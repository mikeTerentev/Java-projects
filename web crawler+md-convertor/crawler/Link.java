package crawler;

import java.net.MalformedURLException;
import java.net.URL;

public class Link {
    private URL URL;

    public Link(String htmlLink) {
        try {
            htmlLink = deleteStaffElements(htmlLink);
            URL = new URL(htmlLink);
        } catch (MalformedURLException e) {
            System.err.print("link_Build_Falled");
        }
    }

    private String deleteStaffElements(String htmlLink) {
        htmlLink = Syntax.changeStaffFormat(htmlLink);//#del
        return htmlLink.contains("#") ? htmlLink.substring(0, htmlLink.indexOf("#")) : htmlLink;
    }

    private Link(URL URL) {
        this.URL = URL;
    }

    String getURL() {
        return URL.toString();
    }

    Link getLink(String htmlLink) {
        Link builtLink = null;
        try {
            htmlLink = deleteStaffElements(htmlLink);
            if (htmlLink.contains("://")) return new Link(htmlLink);
            builtLink = new Link(new URL(URL, htmlLink));
        } catch (MalformedURLException e) {
            System.err.print("link_Build_Failed");
        }
        return builtLink;
    }

}
