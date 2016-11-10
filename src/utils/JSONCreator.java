package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONCreator {
  
  public static JSONObject createJSONFromHashMap(HashMap<String, Integer> hashmapObjects) throws JSONException {
    
    JSONArray newJSONArray = new JSONArray();
    
    for (Entry<String, Integer> entry : hashmapObjects.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      
      JSONObject newJSON = new JSONObject();
      
      newJSON.put(key, value);
      newJSONArray.put(newJSON);     
    }
    
    JSONObject newJSON = new JSONObject();
    newJSON.put("dataset", newJSONArray);
    
    return newJSON;
    
  }
  
  public static void writeJSONTo(JSONObject json, String fileName) throws IOException {
    File file = new File(fileName);

    if (file.getParentFile() != null) file.getParentFile().mkdirs();
    file.createNewFile();
    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write(json.toString());
    fileWriter.flush();
    fileWriter.close();    
    
  }

}
