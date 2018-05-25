import java.util.Date;

public class DataCollector {
	
	private int contentEmotion = 0;
	private Date date = new Date();
	private String StockName;		//Required filed
	private String content;
	private String POIcontent;
	private double OnDatePrice = 0;
	private double SecondDayPrice = 0;
	private double SevenDayPrice = 0;
	
	
	public static class Builder{
		private int contentEmotion = 0;
		private Date date = new Date();
		private String StockName;		//Required filed
		private String content = "";
		private String POIcontent = "";
		private double OnDatePrice = 0;
		private double SecondDayPrice = 0;
		private double SevenDayPrice = 0;
		
		
		public Builder(String s) {
			this.StockName = s;
		}
		
		public Builder Content(String c) {
			this.content = c;
			return this;
		}
		
		public Builder POIContent(String p) {
			this.POIcontent = p;
			return this;
		}
		
		public Builder OnDatePrice(double a) {
			this.OnDatePrice = a;
			return this;
		}
		
		public Builder SecondDayPrice(double p) {
			this.SecondDayPrice = p;
			return this;
		}
		
		public Builder SevenDayPrice(double p) {
			this.SevenDayPrice = p;
			return this;
		}
		
		public Builder contentEmotion(int p) {
			this.contentEmotion = p;
			return this;
		}
		
		public Builder setDate(Date d) {
			this.date = d;
			return this;
		}
		
		public DataCollector build() {
			DataCollector collector = new DataCollector(this);
			
			return collector;
		}
		
		
	}
	
	private DataCollector(Builder b) {
		this.content = b.content;
		this.POIcontent = b.POIcontent;
		this.OnDatePrice = b.OnDatePrice;
		this.SecondDayPrice = b.SecondDayPrice;
		this.SevenDayPrice = b.SevenDayPrice;
		this.contentEmotion = b.contentEmotion;
		this.date = b.date;
		this.StockName = b.StockName;
		
	}
	
	private DataCollector() {
		this.content = "";
		this.POIcontent = "";
		this.OnDatePrice = 0;
		this.SecondDayPrice = 0;
		this.SevenDayPrice = 0;
		this.contentEmotion = 0;
		this.StockName = "";
		
	}
	
	//Static factory function VS constructor
	@Deprecated
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
