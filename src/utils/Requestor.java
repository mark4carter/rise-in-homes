package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import property_reader.PropertyUtils;

public class Requestor {
  
  private static final UriBuilder API_BASE_URL_V3 = UriBuilder.fromPath("https://www.quandl.com/api/v3/datasets");
  private static final String AUTH_TOKEN_PARAM_NAME = "auth_token";
  private static final String JSON_SUFFIX = ".json";
  
  public void sendRequest(String dataSet) {
    JSONObject object = null;
    
    //Client is the main entry point to the fluent API used to build and 
    //execute client requests in order to consume responses returned.
    Client client = ClientBuilder.newClient();
    
    
    //The WebTarget interface represents a specific URI you want to invoke on.
    WebTarget target = client.target(API_BASE_URL_V3);
    target = target.queryParam(AUTH_TOKEN_PARAM_NAME, getAuthToken());
    target = target.path(dataSet + JSON_SUFFIX);
    
    //Creates a request, but request itself does not create a call until invoked
    Builder requestBuilder = target.request();
    
    //buildGet() creates a GET request
    Response response = requestBuilder.buildGet().invoke();
    
    
    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
      
      
      InputStream inputStream = response.readEntity(InputStream.class);
      
        //java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        //System.out.println( s.hasNext() ? s.next() : "");
      // should we be buffering this?
      
      
      //The files in this package implement JSON encoders/decoders in Java.
      //Package is org.json
      JSONTokener tokeniser = new JSONTokener(new InputStreamReader(inputStream));
      
      try {
        object = new JSONObject(tokeniser);
        
        
        //System.out.println(object);
        //toString() prints out JSON in regular JSON format
        
        
        //System.out.println(((JSONArray)object.getJSONObject("dataset").get("data")));
      } catch (JSONException jsone) {
        System.out.println("Problem parsing JSON reply" + jsone);
      }
    } else {
      System.out.println("Response code to " + target.getUri() + " was " + response.getStatusInfo());
    }  
    
    if (object != null) {
      try {
        File file = new File(dataSet + "_JSON");

        file.getParentFile().mkdirs();
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(object.toString());
        fileWriter.flush();
        fileWriter.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    
    readBack(dataSet + "_JSON");
    
  }
  
  public String getAuthToken() {
    PropertyUtils propertyUtils = new PropertyUtils();    
    return propertyUtils.getQuandlApi();   
  }
  
  public void readBack(String fileName) {
    JSONObject readFromFileObj = null;
    try {
      JSONTokener tokener = new JSONTokener(new FileReader(fileName));
      readFromFileObj = new JSONObject(tokener);
      System.out.println(readFromFileObj.toString(4));
    } catch (FileNotFoundException ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    } catch (Exception ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }
  }

}
