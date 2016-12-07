package runner;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.MetaDataRequest;
import com.jimmoores.quandl.MetaDataResult;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.TabularResult;

import example_sets.ExampleZillowCodes;
import property_reader.PropertyUtils;
import utils.ZillowJSONParser;
import utils.GeoJSONAppender;
import utils.JSONCreator;
import utils.Requestor;

public class Sandbox {
  
  /*
  public static void main(String[] args) throws JSONException, IOException {
    //getApiInformation();
    //getApiInformationFromFile("json_files/state_all_homes.json");
    //initialTest();
    //HashMap<String, Integer> neighborhoodValue = printNames();
    //createNewJSON(neighborhoodValue);
    HashMap<String, Integer> stateValue = printNames("state/ZILL");
    JSONObject stateJSON = JSONCreator.createJSONFromHashMap(stateValue);
    JSONCreator.writeJSONTo(stateJSON, "state/ZILL/state-ds.json");
    
  }*/

  
  public static ArrayList<JSONObject> getApiInformation() {    
    Requestor requestor = new Requestor();
    
    ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(ExampleZillowCodes.fullDataSetArray));      
    return requestor.getApiInformationFromArrayList(wordList);    
  }
  
  public static HashMap<String, Integer> printNames() {
    return ZillowJSONParser.readAllHomeValueByNeighborhood("ZILL");
  }
  
  public static HashMap<String, Integer> printNames(String folderName) {
    return ZillowJSONParser.readStatesAllBack(folderName);
  }
  
  public static void createNewJSON(HashMap<String, Integer> neigborhoodValue) {
    GeoJSONAppender.appendToJSON(neigborhoodValue);
  }
  
  public static void initialTest() {    
    
    PropertyUtils propertyUtils = new PropertyUtils();
    
    String quandlApi = propertyUtils.getQuandlApi();    

    
    for ( int i = 0; i < ExampleZillowCodes.dataSetArray.length; i++) {
      String set = ExampleZillowCodes.dataSetArray2[i];
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
      Double result = (tabularResult.get(0).getDouble("Value") / tabularResult.get(tabSize - 120).getDouble("Value")) * 100;
      LocalDate end = tabularResult.get(0).getLocalDate("Date");
      LocalDate start = tabularResult.get(tabSize - 120).getLocalDate("Date");
      
      LocalDate bottom = tabularResult.get(0).getLocalDate("Date");
      Double bottomDouble = tabularResult.get(0).getDouble("Value");
      LocalDate top = tabularResult.get(tabSize - 120).getLocalDate("Date");
      Double topDouble = tabularResult.get(tabSize - 120).getDouble("Value");
      for ( int j = 0; j < tabSize - 120; j++) {
        if ( tabularResult.get(j).getDouble("Value") < bottomDouble ) {
          bottomDouble = tabularResult.get(j).getDouble("Value");
          bottom  = tabularResult.get(j).getLocalDate("Date");
        }
        if ( tabularResult.get(j).getDouble("Value") > topDouble ) {
          topDouble = tabularResult.get(j).getDouble("Value");
          top  = tabularResult.get(j).getLocalDate("Date");
        }
      }
      
      DecimalFormat df = new DecimalFormat("#.00"); 
      
      System.out.println(df.format(result) + "% change since " + start + " to " + end);
      System.out.println(df.format(bottomDouble) + " " + bottom);
      System.out.println(df.format(topDouble) + " " + top);
    }    
  }
  
}
