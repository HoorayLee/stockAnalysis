import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.opencsv.CSVWriter; 

public class CsvWriter {
	private static File file = new File("../data/crawl/fetch_NewsSite.csv");  
	
	public static void UrlsFetched(String[] args) throws IOException {  
        
        Writer writer = new FileWriter(file); 
        CSVWriter csvWriter = new CSVWriter(writer);  
        
        try {
        		csvWriter.writeNext(args);  
            csvWriter.close();  
        } catch (IOException e) {
        	    System.out.println(e);
        }
        
    }  
}
