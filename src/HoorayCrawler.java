import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVWriter;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/*================================================*/
/*			Author Hongrui Li @2018				 */
/*			ALL rights reserved					 */
/*			Email: leehorray@outlook.com			 */
/*			API from quandl.com 					 */
/*			API key: xbd_goeN1NDxqBwX_BSx		 */
/*================================================*/

public class HoorayCrawler extends WebCrawler{
//		 private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js"
//		 + "|xml|gz))$");
		
		private static File POIFile = new File("../data/crawl/POI.csv");  
		
		private static Map<String, Integer> StockCheck = new HashMap<>();
		private static Map<String, String> StockName = new HashMap<>();
		
		private String[] Company = {"apple", "amazon", "boeing", "microsoft", "cisco"};
		
		public static void HoorayCrawler() {
			
		}
		//private final static Pattern FILTERS = Pattern.compile(".*(\\.(html|pdf|png|jpeg|jpg|gif))$");

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
			 boolean PDownload = false;
			 String ContentType = referringPage.getContentType();
			 
			 boolean FILTER = false;
			 FILTER = validate(ContentType);
			 PDownload = FILTER;
				
			 return PDownload;
		}
		 
		 /**
		  * This function is called when a page is fetched and ready
		  * to be processed by your program.
		  */
		  @Override
		  public void visit(Page page) {
			  	StockName.put("apple","AAPL");
				StockName.put("amazon","AMZN");
				StockName.put("boeing","BA");
				StockName.put("microsoft","MSFT");
				StockName.put("cisco","CSCO");
				
				StockCheck.put("apple",0);
				StockCheck.put("amazon",0);
				StockCheck.put("boeing",0);
				StockCheck.put("microsoft",0);
				StockCheck.put("cisco",0);
				
			  String ContentType = page.getContentType();
			  ParseData parseData = page.getParseData();
			  String url = page.getWebURL().getURL();
			  int StatusCode = page.getStatusCode();
			  Date date = Date.valueOf("2018-3-4");
			  //TODO Get the web content issue date.
			        	
			  if (validate(ContentType)) {
				  
				  String content = parseData.toString();
				  String POIcontent = new String();
				  int flag = 0;
				  
				  for (int i = 0; i < Company.length; i++) {
					  if (content.toString().toLowerCase().contains(Company[i])) {
						  flag = content.indexOf(Company[i]);
						  POIcontent = content.substring(flag - 40, flag + 40);
						  
						  if (StockCheck.get(Company[i]) == 0) {
							  
							  try {
									StockPriceAccess.stockPriceAccessAgent(StockName.get(Company[i]), date);
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							  
							  StockCheck.put(Company[i], 1);
						  }
					  }
				  }
				  
				  @Deprecated
//				  if (content.toString().toLowerCase().contains("amazon")) {
//						 POIcontent = content.substring(content.indexOf("amazon") - 40, content.indexOf("amazon") + 40);
//						 
//						 try {
//							StockPriceAccess.stockPriceAccessAgent("AMZN", date);
//						} catch (UnsupportedEncodingException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						 
//					 } else if (content.toString().toLowerCase().contains("apple")) {
//						 POIcontent = content.substring(content.indexOf("Apple") - 40, content.indexOf("Apple") + 40);
//						 try {
//								StockPriceAccess.stockPriceAccessAgent("AAPL", date);
//							} catch (UnsupportedEncodingException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//					 }
				  
				  	String [] POI = {url, POIcontent, ContentType};
					 if (POIcontent.length() != 0) {
						 
						 DataCollector DC = DataCollector.getCollector(page);	 
						 DC.setPOIcontent(POIcontent);
						 
						 try {
							 Writer writer = new FileWriter(POIFile, true);
				  			  
							 CSVWriter csvWriter = new CSVWriter(writer);  
							 csvWriter.writeNext(POI);  
					         csvWriter.close();  
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }

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
