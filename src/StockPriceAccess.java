import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import org.json.*;

import com.opencsv.CSVWriter;

public class StockPriceAccess {
	
	/*================================================*/
	/*			Author Hongrui Li @2018				 */
	/*			ALL rights reserved					 */
	/*			Email: leehorray@outlook.com			 */
	/*			API from quandl.com 					 */
	/*			API key: xbd_goeN1NDxqBwX_BSx		 */
	/*================================================*/
	
	private static StockPriceAccess SPA;
	private static File StockPrice = new File("../data/crawl/Price.csv");  
	
	public static double OnDatePriceA;
	public static double SecondDayPriceA;
	public static double SevenDayPriceA;
	
	public static StockPriceAccess stockPriceAccessAgent(String stockName, Date date) throws UnsupportedEncodingException, IOException {
		
		//Initialize the request
		//TODO Implement the data filter based on the selected date. Implement the price collecting function.
		//https://www.quandl.com/api/v3/datasets/EOD/AAPL.json?api_key=xbd_goeN1NDxqBwX_BSx&start_date=2018-01-01
		String Request = "https://www.quandl.com/api/v3/datasets/EOD/" + stockName + ".json?api_key=xbd_goeN1NDxqBwX_BSx&&start_date=2018-05-01&end_date=2018-05-15";
		
		URL url = new URL(Request);
		
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8")); 
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
		    responseStrBuilder.append(inputStr);
		
		JSONObject obj = new JSONObject(responseStrBuilder.toString());
		JSONArray arr = obj.getJSONObject("dataset").getJSONArray("data");
		JSONArray OnDatePrice = arr.getJSONArray(0);
		JSONArray SecondDayPrice = arr.getJSONArray(1);
		JSONArray SevenDayPrice = arr.getJSONArray(5);
		
		OnDatePriceA = (double)OnDatePrice.get(1);
		SecondDayPriceA = (double)SecondDayPrice.get(1);
		SevenDayPriceA = (double)SevenDayPrice.get(1);
		
		String[] Prices = {stockName, OnDatePrice.get(1).toString(), SecondDayPrice.get(1).toString(), SevenDayPrice.get(1).toString()};
		
			try {
		    		Writer writer = new FileWriter(StockPrice, true);
					  
			    CSVWriter csvWriter = new CSVWriter(writer);  
		    		csvWriter.writeNext(Prices);  
		        csvWriter.close();  
		    } catch (IOException e) {
		    	    System.out.println(e);
		    }
			
		return SPA;
	}
	
	
}
