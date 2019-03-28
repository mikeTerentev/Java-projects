package offlineBr;

public class Syntax {
    public static String changeStaffFormat(String s) {
        return s.replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replaceAll("&nbsp;", " ")
                .replace("&mdash;", "—");
    }


}


