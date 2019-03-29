package crawler;

public enum Names {
    imageName("src"), linkName("href");


    String htmlcode;

    Names(String code) {
        htmlcode = code;
    }

    public String getHtmlcode() {
        return htmlcode;
    }
}