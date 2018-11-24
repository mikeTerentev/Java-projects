package offlineBr;

public enum Tags {
    anchorTag("a"), imageTag("img"), cmmtryTag("!--"), nextLine("br"), paragarf("p");

    String htmlcode;

    Tags(String code) {
        htmlcode = code;
    }

    public String getHtmlcode() {
        return htmlcode;
    }
}
