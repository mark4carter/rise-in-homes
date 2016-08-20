package utils;

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
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(API_BASE_URL_V3);
    target = target.queryParam(AUTH_TOKEN_PARAM_NAME, getAuthToken());
    target = target.path(dataSet + JSON_SUFFIX);
    Builder requestBuilder = target.request();
    //System.out.println(target.getUri());
    Response response = requestBuilder.buildGet().invoke();
    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
      InputStream inputStream = response.readEntity(InputStream.class);
      
        //java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        //System.out.println( s.hasNext() ? s.next() : "");
      // should we be buffering this?
      
      JSONTokener tokeniser = new JSONTokener(new InputStreamReader(inputStream));
      try {
        JSONObject object = new JSONObject(tokeniser);
        //System.out.println(object.toString(4));
        System.out.println(((JSONArray)object.getJSONObject("dataset").get("data")));
      } catch (JSONException jsone) {
        System.out.println("Problem parsing JSON reply" + jsone);
      }
    } else {
      System.out.println("Response code to " + target.getUri() + " was " + response.getStatusInfo());
    }  
    
    
  }
  
  public String getAuthToken() {
    PropertyUtils propertyUtils = new PropertyUtils();    
    return propertyUtils.getQuandlApi();   
  }

}
