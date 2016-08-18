package runner;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.TabularResult;

import property_reader.PropertyUtils;

public class Sandbox {
  
  public static void main(String[] args) {    
    
    PropertyUtils propertyUtils = new PropertyUtils();
    
    String quandlApi = propertyUtils.getQuandlApi();    

    
    QuandlSession session = QuandlSession.create(quandlApi);
    TabularResult tabularResult = session.getDataSet(
    DataSetRequest.Builder.of("ZILL/N01663_A").build());
    System.out.println(tabularResult.toPrettyPrintedString());
    
  }
  
}
