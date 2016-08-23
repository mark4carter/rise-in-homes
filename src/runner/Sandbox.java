package runner;

import java.text.DecimalFormat;

import org.threeten.bp.LocalDate;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.MetaDataRequest;
import com.jimmoores.quandl.MetaDataResult;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.TabularResult;

import property_reader.PropertyUtils;
import utils.Requestor;

public class Sandbox {

  static String[] dataSetArray = {"ZILL/N03346_A", "ZILL/N01663_A", "ZILL/N03674_A", "ZILL/N04073_A"};
  
  public static void main(String[] args) {
    testTwo();
    
  }

  
  public static void testTwo() {
    Requestor requestor = new Requestor();
    requestor.sendRequest(dataSetArray[0]);
  }
  
  public static void initialTest() {    
    
    PropertyUtils propertyUtils = new PropertyUtils();
    
    String quandlApi = propertyUtils.getQuandlApi();    

    
    for ( int i = 0; i < dataSetArray.length; i++) {
      String set = dataSetArray[i];
      QuandlSession session = QuandlSession.create(quandlApi);
      TabularResult tabularResult = session.getDataSet(
          DataSetRequest.Builder.of(set).build());
      
      /*
      System.out.println("Header definition: " + tabularResult.getHeaderDefinition());
      for ( int i = tabularResult.size() - 1; i > 0; i--) {
        LocalDate date = tabularResult.get(i).getLocalDate("Date");
        String value = tabularResult.get(i).getString("Value");
        System.out.println("Value on date " + date + " was " + value);      
      }*/
      
      int tabSize = tabularResult.size();
      
      MetaDataResult metaDataResult = session.getMetaData(MetaDataRequest.of(set));
      System.out.println(metaDataResult.getString("description").split("This data is")[0].split("neighborhood of ")[1]);
      Double result = (tabularResult.get(0).getDouble("Value") / tabularResult.get(tabSize - 1).getDouble("Value")) * 100;
      LocalDate end = tabularResult.get(0).getLocalDate("Date");
      LocalDate start = tabularResult.get(tabSize - 1).getLocalDate("Date");
      
      DecimalFormat df = new DecimalFormat("#.00"); 
      
      System.out.println(df.format(result) + "% change since " + start + " to " + end);
    }    
  }
  
}
