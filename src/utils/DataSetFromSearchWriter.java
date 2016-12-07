package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataSetFromSearchWriter {
  
  JSONObject jsonSearchObject;
  
  public DataSetFromSearchWriter(String fileName) {
    jsonSearchObject = JSONUtils.returnJSONFromFile(fileName);
  }
  
  public JSONObject searchInNameFor(String searchString) throws JSONException {
    JSONArray datasets = jsonSearchObject.getJSONArray("datasets");
    
    JSONArray newDataSets = new JSONArray();
    
    
    for ( int i = 0; i < datasets.length(); i++ ) {
      if (datasets.getJSONObject(i).getString("name").contains(searchString)) {
        newDataSets.put(datasets.getJSONObject(i));
      }
    }
    
    JSONObject jsonSearchResults = new JSONObject();
    jsonSearchResults.put("datasets", newDataSets);
    
    return jsonSearchResults;
  }
  
  public void writeJSONTo(String fileName) throws IOException {
    writeJSONTo(jsonSearchObject, fileName);  
  }
  
  public void writeJSONTo(JSONObject json, String fileName) throws IOException {
    File file = new File(fileName);

    if (file.getParentFile() != null) file.getParentFile().mkdirs();
    file.createNewFile();
    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write(json.toString());
    fileWriter.flush();
    fileWriter.close();    
    
  }
  
  
  /*
  public static void main(String[] args) {
    DataSetFromSearchWriter ds = new DataSetFromSearchWriter("json_files/QuandlStateSearch.json");
    JSONObject searchResults = new JSONObject();
    try {
      searchResults = ds.searchInNameFor("(State): All Homes");
      
      ds.writeJSONTo(searchResults, "json_files/state_all_homes.json");
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    
  }*/
}
