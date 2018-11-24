package offlineBr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Image implements Serializable {
    private final String url, file;
    private final List<Page> pages = new ArrayList<>();

    public Image( String url, String file) {
        this.url = url;
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public String getFile() {
        return file;
    }

    public List<Page> getPages() {
        return pages;
    }
}