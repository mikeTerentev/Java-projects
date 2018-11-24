package offlineBr;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface WebCrawler {
    Page crawl(String url, int depth);
}