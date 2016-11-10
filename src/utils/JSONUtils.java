package utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONUtils {

  
  public static void writeJSONTo(JSONObject json, String fileName) throws IOException {
    File file = new File(fileName);

    if (file.getParentFile() != null) file.getParentFile().mkdirs();
    file.createNewFile();
    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write(json.toString());
    fileWriter.flush();
    fileWriter.close();    
    
  }
  
  public static void readBack(String fileName) {
    JSONObject readFromFileObj = null;
    try {
      JSONTokener tokener = new JSONTokener(new FileReader(fileName));
      readFromFileObj = new JSONObject(tokener);
      System.out.println(readFromFileObj.toString(4));
    
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public static JSONObject returnJSONFromFile(String fileName) {
    JSONObject readFromFileObj = null;
    try {
      JSONTokener tokener = new JSONTokener(new FileReader(fileName));
      readFromFileObj = new JSONObject(tokener);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    return readFromFileObj;
  }
}
