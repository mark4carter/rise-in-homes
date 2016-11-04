package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

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
}
