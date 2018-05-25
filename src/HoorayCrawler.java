import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
//import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;

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
		
		private static Map<String, String> StockName = new HashMap<>();
		boolean cssFollowing = false;
		Date parsedDate = new Date();
		
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
			 String ContentType = referringPage.getContentType();
			
			 return validate(ContentType);
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
				
				//TODO Threads must share the visited information with each other
				
			  String ContentType = page.getContentType();
			  ParseData parseData = page.getParseData();
			  Header[] a = page.getFetchResponseHeaders();
			  Date onDate = new Date();
			  
			  
			  long diff = -1;
			  long diffInMillies = 0; 
			  
			  SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss");
			  String timeStamp = dateFormat.format(Calendar.getInstance().getTime());
			  dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			  try {
				
				if (a[2].getName().equals("Last-Modified")) {
					  onDate = dateFormat.parse(timeStamp);
					  String lastM = a[2].getValue();
					  parsedDate = dateFormat.parse(lastM);

					  //Thu, 03 May 2018 21:38:01 GMT
					  
				  }
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			  diffInMillies = Math.abs(onDate.getTime() - parsedDate.getTime());
			  diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

			  String url = page.getWebURL().getURL();
			  
			  //Using the builder pattern to build the object
			  DataCollector DC = new DataCollector.Builder("").build();
//			 
//			  if (0 < diff && diff < 7) {
//				  
//				  DC = DataCollector.getCollector(2);	
//				  
//			  } else {
//				  
//				  DC = DataCollector.getCollector(3);	
//				  
//			  }
			  
			  if (validate(ContentType) && diffInMillies > 0) {
				  
				  String content = parseData.toString();
				  String POIcontent = new String();
				  int flag = 0;
				  
				  for (int i = 0; i < Company.length; i++) {
					  if (content.toString().toLowerCase().contains(Company[i])) {
						  flag = content.indexOf(Company[i]);
						  POIcontent = content.substring(flag - 40, flag + 40);
						  //TODO Add more features to filter the real POI content

							  try {
								  StockPriceAccess SPA = StockPriceAccess.stockPriceAccessAgent(StockName.get(Company[i]), parsedDate, diffInMillies);
								  
								// Use Builder to build the object
								  DC = new DataCollector.Builder(StockName.get(Company[i]))
										  .Content(content)
										  .POIContent(POIcontent)
										  .OnDatePrice(SPA.getOnDatePrice())
										  .SecondDayPrice(SPA.getSecondDayPrice())
										  .SevenDayPrice(SPA.getSevenDayPrice())
										  .setDate(parsedDate)
										  .build();
								  
								  //Deprecated. Build the object with constructor
//								  DC.setPOIcontent(POIcontent);
//								  DC.setContent(content);
//								  DC.setOnDatePrice(SPA.getOnDatePrice());
//								  DC.setSecondDayPrice(SPA.getSecondDayPrice());
//								  DC.setSevenDayPrice(SPA.getSevenDayPrice());
//								  DC.setDate(parsedDate);
//								  DC.setStockName(StockName.get(Company[i]));
								 
								  //TODO Use NLP to analyze document emotion
								} catch (UnsupportedEncodingException e) {
						
									e.printStackTrace();
								} catch (IOException e) {
									
									e.printStackTrace();
								}
							  
					  }
				  }
				  
				  	
					 if (POIcontent.length() != 0) {
						 String [] POI = {url, POIcontent, ContentType, DC.getStockName(), DC.getDate().toString(),
						  			String.valueOf(DC.getOnDatePrice()), String.valueOf(DC.getSecondDayPrice()), String.valueOf(DC.getSevenDayPrice())};
						 try {
							 Writer writer = new FileWriter(POIFile, true);
				  			  
							 CSVWriter csvWriter = new CSVWriter(writer);  
							 csvWriter.writeNext(POI);  
					         csvWriter.close();  
							
						} catch (IOException e) {
							
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
				 } else if (ContentType.contains("text/css")) {
					 cssFollowing = true;
				 } else {
					 cssFollowing = false;
				 }
			 }
			 
			 return FILTER;
		 }
		 
}
