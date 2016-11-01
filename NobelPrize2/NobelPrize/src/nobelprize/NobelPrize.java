/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URLConnection;

/**
 *
 * @author Peter
 */
public class NobelPrize extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    private static String readAll(Reader readObj) throws IOException {
        StringBuilder stringbuilder = new StringBuilder();
        int charVal;
        //append a single character at a time to the stringbuilder
        while ((charVal = readObj.read()) != -1) {
            stringbuilder.append((char) charVal);
        }
        return stringbuilder.toString();
    }
    
    
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        //create a URL object to identify the webaddress
        URL nobelAPI = null;
        try {
            nobelAPI = new URL (url);
        } catch (MalformedURLException excep){
            System.out.println("MalformedURLException");
        }

        //Create URLConnection to access the actual content information of the URL
        URLConnection nobelContent= null;
        try {
            nobelContent= nobelAPI.openConnection();
        } catch (IOException excep){
            System.out.println("IOException");
        }
        
        //Return the InputStream linked to the API to obtain content
        InputStream nobelDataStream= null;
        try {
            nobelDataStream= nobelContent.getInputStream();
        } catch (IOException excep){
            System.out.println("IOException");
        }
        
        //Create a BufferedReader obj to efficiently read text from a character stream
        //and use InputStreamReader to decode bytes into characters
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(nobelDataStream));
            
            //call readAll to put all the text into a string
            String jsonText = readAll(reader);
            JSONObject json = new JSONObject(jsonText);
        return json;
        } finally {
            nobelDataStream.close();
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        JSONObject json = new JSONObject();
        json = readJsonFromUrl("http://api.nobelprize.org/v1/prize.json?");
        System.out.println(json.toString(4));
        System.out.println(json.get("prizes"));
    }    
}