import java.util.Date;

import edu.uci.ics.crawler4j.crawler.Page;

public class DataCollector {
	Date date = new Date();
	String content;
	String POIcontent;
	double OnDatePrice;
	double SecondDayPrice;
	double SevenDayPrice;
	
	private static DataCollector collector;
	
	//Static factory function VS constructor
	public static DataCollector getCollector(Page page) {
		if (page.getContentType().equals("text")) {
			return collector;
		} else if (page.getLanguage().equals("English")) {
			return new RecentNews();
		} else {
			return new PastNews();
		}
		
		// TODO Add time judge condition in response to the three child classes
	}
	
	public void setOnDatePrice(double p) {
		OnDatePrice = p;
	}
	
	public void setSecondDayPrice(double p) {
		SecondDayPrice = p;
	}
	
	public void setContent(String c) {
		content = c;
	}
	
	public void setPOIcontent(String poi) {
		POIcontent = poi;
	}
	
	public double getOnDatePrice() {
		return OnDatePrice;
	}
	
	public double getSecondDayPrice() {
		return SecondDayPrice;
	}
	
	public double getSevenDayPrice() {
		return SevenDayPrice;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getPOIcontent() {
		return POIcontent;
	}
	
	private static class RecentNews extends DataCollector {
		
	} 

	private static class PastNews extends DataCollector {
		
		
	} 

}
