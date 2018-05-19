import java.util.Date;

public class DataCollector {
	private int contentEmotion;
	private Date date = new Date();
	private String StockName;
	private String content;
	private String POIcontent;
	private double OnDatePrice;
	private double SecondDayPrice;
	private double SevenDayPrice;
	
	//Static factory function VS constructor
	public static DataCollector getCollector(int type) {

		DataCollector collector = new DataCollector();
		
		if (type == 1) {
			collector.SecondDayPrice = -1;
			collector.SevenDayPrice = -1;
			return collector;
		} else if (type == 2) {
			collector.SevenDayPrice = -1;
			return collector;
		} else {
			return collector;
		}
		
		// TODO Add time judge condition in response to the three child classes
	}
	
	public void setDate(Date d) {
		date = d;
	}
	
	void setOnDatePrice(double p) {
		OnDatePrice = p;
	}
	
	public void setSecondDayPrice(double p) {
		SecondDayPrice = p;
	}
	
	public void setSevenDayPrice(double p) {
		SevenDayPrice = p;
	}
	
	public void setContent(String c) {
		content = c;
	}
	
	public void setPOIcontent(String poi) {
		POIcontent = poi;
	}
	
	public void setStockName(String stock) {
		StockName = stock;
	}
	
	public Date getDate() {
		return date;
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
	
	public String getStockName() {
		return StockName;
	}
	
	public String getPOIcontent() {
		return POIcontent;
	}
	
	private static class RecentNews extends DataCollector {
		
	} 

	private static class PastNews extends DataCollector {
		
		
	} 

}
