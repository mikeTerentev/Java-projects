package offlineBr;

import base.Pair;

public class Main {
    public static void main(String[] args) {

        SimpleDownloader dld = new SimpleDownloader();

        SimpleWebCrawler x = new SimpleWebCrawler(dld);
        Page page = x.crawl("https://www.d.umn.edu/~gshute/arch/cache-addressing.xhtml", 3);
        //
    }
}
