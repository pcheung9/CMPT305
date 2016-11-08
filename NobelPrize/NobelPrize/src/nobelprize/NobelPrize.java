/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URLConnection;
import javafx.event.*;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import static javafx.geometry.Pos.CENTER;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import static javax.swing.text.StyleConstants.Orientation;

/**
 *
 * @author Peter
 */
public class NobelPrize extends Application {
    
    //creates the user input screen
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("GUIScreen.fxml"));
        FlowPane rootPlane = new FlowPane(300,100);
        ScrollPane results = new ScrollPane();
        Scene scene = new Scene(rootPlane);
        Scene resultsScene = new Scene(results);
               
        result = new Label("");
        results.setContent(result);
        
        stage.setScene(scene);
        
        button1 = new Button("get");
        
        button1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                response.setText("clicked");
                result.setText(jsonQuery().toString(4));
                stage.setScene(resultsScene);
            }
        });
        
        response = new Label("Push button");
        
        rootPlane.getChildren().addAll(button1, response);
        stage.show();
    }
    
    //@FXML
    private Button button1; 
    Label response;
    Label result;
    
    //This function creates a JSON object in order to read the API URL. It returns
    //the JSON object to be put into the results Scene.
    public JSONObject jsonQuery(){
        JSONObject json = readUrl("http://api.nobelprize.org/v1/prize.json?");
        return json;
    }
        
    //This function reads data from the Nobel prize API. It takes the 
    //API URL as an argument and returns a JSON object, which holds
    //the name/value pairs of the Nobel prize JSON data.
    public static JSONObject readUrl(String url) {
        //create a URL object to identify the webaddress
        URL nobelAPI = null;
        try {
            nobelAPI = new URL (url);
        } catch (MalformedURLException ex){
            System.out.println("Exception" + ex);
        }

        //Create URLConnection to access the actual content information of the URL
        URLConnection nobelContent= null;
        try {
            nobelContent= nobelAPI.openConnection();
        } catch (IOException ex){
            System.out.println("Exception" + ex);
        }
        
        //Return the InputStream linked to the API to obtain content
        InputStream nobelDataStream= null;
        try {
            nobelDataStream= nobelContent.getInputStream();
        } catch (IOException ex){
            System.out.println("Exception" + ex);
        }
        
        //Create a BufferedReader obj to efficiently hold text from a character stream
        //and use InputStreamReader to decode bytes into characters
        JSONObject jsonObj = null;
        Reader reader=null;
    
        reader = new InputStreamReader(nobelDataStream);
    
        //call readData to put all the text into a string and put in JSONOBject
        String jsonText = readData(reader);
        jsonObj = new JSONObject(jsonText);
        
        try {
            nobelDataStream.close();
        }catch (IOException ex){
            System.out.println("Exception" + ex);
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
        } catch (IOException ex){
            System.out.println("IOException" + ex);
        }
        
        return stringData.toString();
    }
    
    
    public static void main(String[] args) {
        
        //add on the category search from user input
        String category="";
        String year="";
        String yearTo="";
        //ID of a unique Nobel Laureate
        String id="";
        String numberOfLaureates="";
        //Search for laureates by country of birth
        String bornCountry="";;
        //Search for laureates by country of birth based on country code
        String bornCountryCode="";;
        //Search for laureates by city of birth
        String bornCity="";;
        //Search for laureates by country where they died
        String diedCountry="";;
        String diedCountryCode="";;
        String diedCity="";;
        String bornDate="";;
        String bornDateTo="";;
        String diedDate="";;
        String diedDateTo="";;
        //Search for laureates based on the motivation of receiving the prize
        String motivation="";;
        String gender="";;
        String affiliation="";
        
        //add in fields to end of URL eg. ?category=phyics&year=2000&yearto=2001';
        //concatenate string eg. url+..+..;  
     
        //launch JavaFX application to get user input
        launch();
        
    }    
}