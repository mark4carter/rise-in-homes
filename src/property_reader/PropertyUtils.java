package property_reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
  
  private String quandlapi;
  
  public PropertyUtils() {
  
    Properties prop = new Properties();
    InputStream input = null;
  
    try {
  
      input = new FileInputStream("config.properties");
  
      // load a properties file
      prop.load(input);
  
      // get the property value and print it out
      quandlapi = prop.getProperty("quandlapi");
  
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    
  }
  
  public String getQuandlApi() {
    return quandlapi;
  }

}
