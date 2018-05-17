import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import org.json.*;

public class StockPriceAccess {
	
	/*================================================*/
	/*			Author Hongrui Li @2018				 */
	/*			ALL rights reserved					 */
	/*			Email: leehorray@outlook.com			 */
	/*			API from quandl.com 					 */
	/*			API key: xbd_goeN1NDxqBwX_BSx		 */
	/*================================================*/
	
	private double OnDatePrice;
	private double SecondDayPrice;
	private double SevenDayPrice;
	
	private static StockPriceAccess SPA;
	
	public static StockPriceAccess stockPriceAccessAgent(String stockName, Date date) throws UnsupportedEncodingException, IOException {
		
		//Initialize the request
		//TODO Implement the data filter based on the selected date. Implement the price collecting function.
		
		URL url;
		url = new URL("https://www.quandl.com/api/v3/datasets/WIKI/" + stockName + "/data.json?api_key=xbd_goeN1NDxqBwX_BSx");
		
		JSONObject obj = new JSONObject(new InputStreamReader(url.openStream(), "UTF-8"));
		System.out.println(obj);
		
//		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
//		    for (String line; (line = reader.readLine()) != null;) {
//		        System.out.println(line);
//		    }
//		}
		
		return SPA;
	}
	
	
}
