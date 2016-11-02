package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Decoder {
  
  public static HashMap<String, Integer> readAllBack(String folderName) {
    
    ArrayList<String> fileList = new ArrayList<String>();
    HashMap<String, Integer> neighborhoodValue = new HashMap<String, Integer>();
    
    final File folder = new File(folderName);
    fileList = listFilesForFolder(folder);    
    
    for (int i = 0; i < fileList.size(); i++ ) {

      JSONObject readFromFileObj = null;
      try {
        JSONTokener tokener = new JSONTokener(new FileReader(folderName + "/" + fileList.get(i)));
        readFromFileObj = new JSONObject(tokener);
        
        String fullString = (String)readFromFileObj.getJSONObject("dataset").get("name");
        System.out.println(fullString);
        fullString = fullString.split("Zillow Home Value Index \\(Neighborhoods\\): All Homes - ")[1].split(", Richmond")[0];
        System.out.println(fullString);
        String valueString = readFromFileObj.getJSONObject("dataset").getJSONArray("data").getJSONArray(0).getString(1);
        System.out.println(valueString);
        neighborhoodValue.put(fullString, Integer.parseInt(valueString));
      } catch (FileNotFoundException ex) {
        // TODO Auto-generated catch block
        ex.printStackTrace();
      } catch (Exception ex) {
        // TODO Auto-generated catch block
        ex.printStackTrace();
      }
      
    }
    System.out.println(neighborhoodValue.size());
    return neighborhoodValue;    
  }
  
  public static ArrayList<String> listFilesForFolder(final File folder) {
    
    ArrayList<String> fileList = new ArrayList<String>();
    
    
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            fileList.add(fileEntry.getName());
        }
    }
    
    return fileList;
  }
  
  public static JSONObject returnJSONFromFile(String fileName) {
    JSONObject readFromFileObj = null;
    try {
      JSONTokener tokener = new JSONTokener(new FileReader(fileName));
      readFromFileObj = new JSONObject(tokener);
    } catch (FileNotFoundException ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    } catch (Exception ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }
    
    return readFromFileObj;
  }
  
  public static ArrayList<String> getArrayListOfDataSetCodes(JSONObject json) {
    
    ArrayList<String> arrayOfDataSetCodes = new ArrayList<String>();

    try {
      
      JSONArray datasetsArray = json.getJSONArray("datasets");
      
      for ( int i = 0; i < datasetsArray.length(); i++ ) {
        String datasetString;
          datasetString = datasetsArray.getJSONObject(i).getString("database_code") + "/";
          
          datasetString += datasetsArray.getJSONObject(i).getString("dataset_code");
          arrayOfDataSetCodes.add(datasetString);
          System.out.println(datasetString);
      }
    
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return arrayOfDataSetCodes;
  }

}
