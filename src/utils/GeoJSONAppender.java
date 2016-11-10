package utils;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeoJSONAppender {

  
  public static void appendToJSON(HashMap<String, Integer> neigborhoodValue) {
    JSONObject geova = JSONUtils.returnJSONFromFile("geova.json");
    
    try {
    
      int count = 0;
      JSONArray featuresArray = geova.getJSONArray("features");
          
      int arrayLength = featuresArray.length();
      
      for (int i = 0; i < arrayLength; i++) {
        JSONObject propertiesObject = featuresArray.getJSONObject(i).getJSONObject("properties");
        String neighborhoodName = propertiesObject.get("NAME").toString();
        if (neigborhoodValue.containsKey(neighborhoodName)) {
          System.out.println(neighborhoodName + " === " + neigborhoodValue.containsKey(neighborhoodName));
          count++;
          propertiesObject.put("Value", neigborhoodValue.get(neighborhoodName));
        }
      }
      
      System.out.println(count);

      /*File file = new File("Append1029" + "_JSON.json");

      //file.getParentFile().mkdirs();
      file.createNewFile();
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(geova.toString());
      fileWriter.flush();
      fileWriter.close();
      */
      
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}
