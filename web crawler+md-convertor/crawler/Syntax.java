package crawler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Syntax {
    public static String changeStaffFormat(String s) {
        return s.replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replaceAll("&nbsp;", " ")
                .replace("&mdash;", "—");
    }


}


