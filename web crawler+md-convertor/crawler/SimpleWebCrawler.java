package crawler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleWebCrawler implements WebCrawler {

    private Downloader downloader;
    private HashMap<String, Image> images = new HashMap<>();
    private HashMap<String, Page> allPages = new HashMap<>();
    private final int preTitleSize = 7;
    private Queue<PairQ> queue = new LinkedList<>();

    SimpleWebCrawler(Downloader downloader) {
        this.downloader = downloader;
    }

    public Page crawl(String URL, int dpth) {
        Page res = changeFormat(URL, dpth);
        while (!queue.isEmpty()) {
            PairQ tmp = queue.poll();
            Page page = changeFormat(tmp.page.getUrl(), tmp.depth);
        }
        return res;
    }

    private Page changeFormat(String URL, int dpth) {
        Page newPage;
        URL = new Link(URL).getURL();
        String webSite = "", title = "";
        boolean isDownloaded = true;

        try {
            if (dpth == 0) {
                webSite = "";
                title = "";
            } else {
                webSite = readCode(downloader.download(URL));
                title = readTitle(webSite);
            }
        } catch (IOException e) {
            isDownloaded = false;
            System.err.print("downloaderCrash");
            e.printStackTrace();
        }

        allPages.putIfAbsent(URL, new Page(URL, title));
        newPage = allPages.get(URL);

        if (dpth <= 0 || !isDownloaded) {
            return newPage;
        }
        int ptr = 0;
        while (ptr < webSite.length() - 1) {
            int i_scnd = ptr + 1;

            if (webSite.charAt(ptr) != '<') {
                ptr++;
                continue;
            }
            for (Tags curTag : Tags.values()) {
                if (isHere(curTag.getHtmlcode(), webSite, ptr + 1)) {
                    int eOfptr = endOfPart(webSite, ptr);
                    if (eOfptr == -1) break;

                    htmlBlock part = new htmlBlock(webSite.substring(ptr, eOfptr));
                    i_scnd = eOfptr;
                    if (curTag == Tags.anchorTag && !(part.getTagAtr(Names.linkName.getHtmlcode()) == null)) {
                        tagAtr hrefAttribute = part.getTagAtr(Names.linkName.getHtmlcode());
                        Link link = new Link(URL).getLink(hrefAttribute.getVal());

                        if (allPages.containsKey(link.getURL())) {
                            Page page = allPages.get(link.getURL());
                            newPage.addLink(page);
                        } else {
                            String URL2 = link.getURL();
                            String webSite2 = "", title2 = "";
                            int dpth2 = dpth - 1;
                            try {
                                webSite2 = dpth2 == 0 ? "" : readCode(downloader.download(URL2));
                                title2 = dpth2 == 0 ? "" : readTitle(webSite2);
                            } catch (IOException e) {
                                System.err.print("downloaderCrash ");
                                e.printStackTrace();
                            }
                            allPages.putIfAbsent(URL2, new Page(URL2, title2));
                            Page page = allPages.get(URL2);
                            newPage.addLink(page);
                            queue.offer(new PairQ(page, dpth2));
                        }
                    }
                    if (curTag == Tags.imageTag && !(part.getTagAtr(Names.imageName.getHtmlcode()) == null)) {
                        Link link = new Link(URL).getLink(part.getTagAtr(Names.imageName.getHtmlcode()).getVal());
                        Image image;

                        if (images.containsKey(link.getURL())) {
                            image = images.get(link.getURL());
                        } else {
                            String fileName = UUID.randomUUID().toString();
                            try {
                                Files.write(Paths.get(fileName), ImageConvert(downloader.download(link.getURL())));
                            } catch (IOException e) {
                                System.err.print("ImageLoadFail");
                                e.printStackTrace();
                            }
                            image = new Image(link.getURL(), fileName);
                            images.put(link.getURL(), image);
                        }
                        newPage.addImage(image);
                    }
                }
            }
            ptr = i_scnd;
        }
        return newPage;
    }


    private boolean isHere(String tagName, String code, int pos) {
        return pos + tagName.length() <= code.length() &&
                code.substring(pos, pos + tagName.length()).toLowerCase().equals(tagName);
    }

    private int endOfPart(String content, int endPoint) {
        while (endPoint < content.length() - 1) {
            if (content.charAt(endPoint + 1) == '>') return ++endPoint;
            endPoint++;
        }
        return -1;
    }

    private String readCode(InputStream is) {
        StringBuilder code = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                code.append(line);
            }
        } catch (IOException e) {
            System.err.print("getCodeError");
        }
        return code.toString();
    }

    public String readTitle(String content) {
        Matcher matcher = Pattern.compile("<title>(.*?)</title>").matcher(content);

        if (!matcher.find()) return null;
        String title = matcher.group();
        return Syntax.changeStaffFormat(title.substring(preTitleSize, title.lastIndexOf("</")));
    }

    public static byte[] ImageConvert(InputStream input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int len;
        byte[] data = new byte[1000];

        try {
            while ((len = input.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, len);
            }
        } catch (IOException e) {
            System.out.println("ImageConvertError");
        }
        return buffer.toByteArray();
    }
}
