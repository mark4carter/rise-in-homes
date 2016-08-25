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
  
  static String[] fullDataSetArray = {
      "ZILL/N04154_A",
      "ZILL/N03251_A",
      "ZILL/N03225_A",
      "ZILL/N04140_A",
      "ZILL/N03833_A",
      "ZILL/N03670_A",
      "ZILL/N03930_A",
      "ZILL/N04127_A",
      "ZILL/N07348_A",
      "ZILL/N04444_A",
      "ZILL/N03439_A",
      "ZILL/N03674_A",
      "ZILL/N04236_A",
      "ZILL/N02900_A",
      "ZILL/N03670_A",
      "ZILL/N04395_A",
      "ZILL/N07348_A",
      "ZILL/N04236_A",
      "ZILL/N07488_A",
      "ZILL/N04424_A",
      "ZILL/N02357_A",
      "ZILL/N03439_A",
      "ZILL/N03674_A",
      "ZILL/N03964_A",
      "ZILL/N04303_A",
      "ZILL/N03341_A",
      "ZILL/N04109_A",
      "ZILL/N02254_A",
      "ZILL/N04337_A",
      "ZILL/N03848_A",
      "ZILL/N03626_A",
      "ZILL/N03772_A",
      "ZILL/N00725_A",
      "ZILL/N04348_A",
      "ZILL/N06361_A",
      "ZILL/N04179_A",
      "ZILL/N03919_A",
      "ZILL/N03516_A",
      "ZILL/N03346_A",
      "ZILL/N03983_A",
      "ZILL/N03458_A",
      "ZILL/N02254_A",
      "ZILL/N04337_A",
      "ZILL/N03516_A",
      "ZILL/N03341_A",
      "ZILL/N04109_A",
      "ZILL/N03980_A",
      "ZILL/N04055_A",
      "ZILL/N04051_A",
      "ZILL/N04219_A",
      "ZILL/N03009_A",
      "ZILL/N03211_A",
      "ZILL/N04073_A",
      "ZILL/N04222_A",
      "ZILL/N01066_A",
      "ZILL/N00117_A",
      "ZILL/N00515_A",
      "ZILL/N00550_A",
      "ZILL/N00581_A",
      "ZILL/N00398_A",
      "ZILL/N01663_A",
      "ZILL/N01663_A",
      "ZILL/N03271_A",
      "ZILL/N03066_A",
      "ZILL/N04119_A",
      "ZILL/N03271_A",
      "ZILL/N03808_A"
  };
  
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
