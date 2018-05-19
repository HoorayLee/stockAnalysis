import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
	
	
	private static File StockPrice = new File("../data/crawl/Price.csv");  
	
	private static double OnDatePriceA = 0;
	private static double SecondDayPriceA = 0;
	private static double SevenDayPriceA = 0;
	
	public static StockPriceAccess stockPriceAccessAgent(String stockName, Date date, long diffMili) throws UnsupportedEncodingException, IOException {
		StockPriceAccess SPA = new StockPriceAccess();
		//Initialize the request
		//TODO Implement the data filter based on the selected date. Implement the price collecting function.
		//https://www.quandl.com/api/v3/datasets/EOD/AAPL.json?api_key=xbd_goeN1NDxqBwX_BSx&start_date=2018-01-01
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String onDate = sdf.format(date);
		//TODO Extend the range to 7 days before the certain date.
		long oneDay = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
		int daysNum = (int) (diffMili/oneDay);
		int range = 1;
		
		if (1 < daysNum &&  daysNum < 7) {
			range = daysNum;
		} else if (daysNum > 6) {
			range = 7;
		}
		
		date.setTime(date.getTime() + oneDay * range);
		String endDate = sdf.format(date);
		int DataLength = 0;
		
		String Request = "https://www.quandl.com/api/v3/datasets/EOD/" + stockName + 
				".json?api_key=xbd_goeN1NDxqBwX_BSx&&start_date=" + onDate + "&end_date=" + endDate;
		
		URL url = new URL(Request);
		
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8")); 
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
		    responseStrBuilder.append(inputStr);
		
		JSONObject obj = new JSONObject(responseStrBuilder.toString());
		JSONArray arr = obj.getJSONObject("dataset").getJSONArray("data");
		DataLength = arr.length();
		
		JSONArray OnDatePrice = arr.getJSONArray(DataLength - 1);
		//JSONArray SecondDayPrice = new JSONArray();
		//JSONArray SevenDayPrice = new JSONArray();
		
		OnDatePriceA = (double)OnDatePrice.get(1);
		if (DataLength < 6) {
			JSONArray SecondDayPrice = arr.getJSONArray(DataLength - 2);
			SecondDayPriceA = (double)SecondDayPrice.get(1);
		} else {
			JSONArray SecondDayPrice = arr.getJSONArray(DataLength - 2);
			JSONArray SevenDayPrice = arr.getJSONArray(DataLength - 5);
			SecondDayPriceA = (double)SecondDayPrice.get(1);
			SevenDayPriceA = (double)SevenDayPrice.get(1);
		}
		
		String[] Prices = {stockName, OnDatePrice.get(1).toString(), String.valueOf(SecondDayPriceA), String.valueOf(SevenDayPriceA)};
		
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
	
	
	public double getOnDatePrice() {
		return OnDatePriceA;
	}
	
	public double getSecondDayPrice() {
		return SecondDayPriceA;
	}
	
	public double getSevenDayPrice() {
		return SevenDayPriceA;
	}
	
	
}
