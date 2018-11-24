package offlineBr;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SimpleDownloader implements Downloader {
    public InputStream download(String url) throws IOException {
        URL turl = new URL(url);
        return turl.openStream();
    }
}

