import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
	 public static void main(String[] args) throws Exception {
		 String crawlStorageFolder = "../data/crawl";
		 int numberOfCrawlers = 7;
		 CrawlConfig config = new CrawlConfig();
		 config.setCrawlStorageFolder(crawlStorageFolder);
		 config.setMaxDepthOfCrawling(3);
		 config.setMaxPagesToFetch(20000);
		 /*
		 * Instantiate the controller for this crawl.
		 */
		 PageFetcher pageFetcher = new PageFetcher(config);
		 RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		 config.setIncludeBinaryContentInCrawling(true);
		 RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		 
		 CrawlController controller = null;
		 
		 try {
		     controller = new CrawlController(config, pageFetcher, robotstxtServer);
		 } catch (java.lang.Exception e) {
			 System.out.print(e);
		 }
		 
		 /*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		 controller.addSeed("https://www.bloomberg.com/");
		 
		 /*
		  * Start the crawl. This is a blocking operation, meaning that your code
		  * will reach the line after this only when crawling is finished.
		  */
		  controller.start(HoorayCrawler.class, numberOfCrawlers);
		  }

}
