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
        Parent root = FXMLLoader.load(getClass().getResource("GUIScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    //This function reads data from the Nobel prize API. It takes the 
    //API URL as an argument and returns a JSON object, which holds
    //the name/value pairs of the Nobel prize JSON data.
    public static JSONObject readUrl(String url) {
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
        
        //Create a BufferedReader obj to efficiently hold text from a character stream
        //and use InputStreamReader to decode bytes into characters
        JSONObject jsonObj = null;
        try {
            Reader reader = new BufferedReader(new InputStreamReader(nobelDataStream));
            //call readData to put all the text into a string and put in JSONOBject
            String jsonText = readData(reader);
            jsonObj = new JSONObject(jsonText);
        } catch (JSONException ex){
            System.out.println("IOException");
        }
        
        try {
            nobelDataStream.close();
        }
        catch (IOException ex){
            System.out.println("IOException");
        }
        return jsonObj;
    }

    //this function reads data from the Reader object which reads the character
    //stream of API data. The data is read and returned as a string.
    private static String readData (Reader readObj) {
        //create a StringBuilder to hold data
        StringBuilder stringData = new StringBuilder();
        int charVal;
        try {
            //read chars and append to the stringbuilder until end of stream reached
            while ((charVal=readObj.read())!= -1){
            stringData.append((char)charVal);
            }
        } catch (IOException excep){};
        
        return stringData.toString();
    }
    
    public static void main(String[] args) {
        //create json object to hold the nobel prize data
        JSONObject json = readUrl("http://api.nobelprize.org/v1/prize.json?");
        System.out.println(json.toString(4));
        //print all the data based on the name (key) of "prizes"
        System.out.println(json.get("prizes"));
        //launch JavaFX application
        launch(args);
    }    
}