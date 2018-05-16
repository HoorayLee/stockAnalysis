import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.opencsv.CSVWriter;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class HoorayCrawler extends WebCrawler{
		 private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js"
		 + "|xml|gz))$");
		private static File Fetched = new File("../data/crawl/fetch_NewsSite.csv");  
		private static File Downloaded = new File("../data/crawl/POI.csv");  
		private static File Discovered = new File("../data/crawl/urls.csv");  
		//private final static Pattern FILTERS = Pattern.compile(".*(\\.(html|pdf|png|jpeg|jpg|gif))$");
		private Writer writer = null;
	
		
		 /**
		 * This method receives two parameters. The first parameter is the page
		 * in which we have discovered this new url and the second parameter is
		 * the new url. You should implement this function to specify whether
		 * the given url should be crawled or not (based on your crawling logic).
		 * In this example, we are instructing the crawler to ignore urls that
		 * have css, js, git, ... extensions and to only accept urls that start
		 * with "http://www.viterbi.usc.edu/". In this case, we didn't need the
		 * referringPage parameter to make the decision.
		 */
		 @Override
		 public boolean shouldVisit(Page referringPage, WebURL url) {
			 String href = url.getURL().toLowerCase();
			 String OK = "";
			 boolean PDownload = false;
			 String ContentType = referringPage.getContentType();
			 ParseData content = referringPage.getParseData();
			 
			 boolean FILTER = false;
			 FILTER = validate(ContentType);
			 
			 String [] Discover = {href, OK};
			 
				try {
	        		Writer writer = new FileWriter(Discovered, true);
	  	
			    CSVWriter csvWriter = new CSVWriter(writer);  
	        		csvWriter.writeNext(Discover);  
	            csvWriter.close();  
				} catch (IOException e) {
					System.out.println(e);
				}
				PDownload = FILTER;
				
			 return PDownload;
			 }
		 
		 /**
		  * This function is called when a page is fetched and ready
		  * to be processed by your program.
		  */
		  @Override
		  public void visit(Page page) {
			  String ContentType = page.getContentType();
			  ParseData parseData = page.getParseData();
			  int OutGoingUrls = parseData.getOutgoingUrls().size();
	  		  int FileSize = page.getContentData().length;
			  String url = page.getWebURL().getURL();
			  int StatusCode = page.getStatusCode();
			  String [] Urls = {url, String.valueOf(StatusCode)};
			  
			        	try {
			        		Writer writer = new FileWriter(Fetched, true);
			  			  
					    CSVWriter csvWriter = new CSVWriter(writer);  
			        		csvWriter.writeNext(Urls);  
			            csvWriter.close();  
			        } catch (IOException e) {
			        	    System.out.println(e);
			        }
			        	
			  if (validate(ContentType)) {
				  
				  String content = parseData.toString();
				  String POIcontent = new String();
				  
				  if (content.toString().contains("AMZN")) {
						 POIcontent = content.substring(content.indexOf("AMZN") - 20, content.indexOf("AMZN") + 10);
						 
					 } else if (content.toString().contains("WB")) {
						 POIcontent = content.substring(content.indexOf("WB") - 20, content.indexOf("WB") + 10);
					 }
				  
				  	String [] POI = {url, POIcontent, ContentType};
					 if (POIcontent.length() != 0) {
						 try {
							 Writer writer = new FileWriter(Downloaded, true);
				  			  
							    CSVWriter csvWriter = new CSVWriter(writer);  
					        		csvWriter.writeNext(POI);  
					            csvWriter.close();  
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }
					 
//				  String [] Download = {url, String.valueOf(FileSize), 
//						  String.valueOf(OutGoingUrls), ContentType};
//				  try {
//		        		Writer writer = new FileWriter(Downloaded, true);
//		  			  
//				    CSVWriter csvWriter = new CSVWriter(writer);  
//		        		csvWriter.writeNext(Download);  
//		            csvWriter.close();  
//		            
//					} catch (IOException e) {
//						System.out.println(e);
//					}
			  }
			        	
			        	
			        
	}
		  
		 private boolean validate (String ContentType) {
			 boolean FILTER = false;
			 
			 if (ContentType != null) {
				 if (ContentType.contains("text/html")) {
					 FILTER = true;
				 } 
			 }
			 
			 return FILTER;
		 }
		 
}
