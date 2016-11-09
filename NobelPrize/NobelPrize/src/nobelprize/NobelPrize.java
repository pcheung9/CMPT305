/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;


import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.beans.value.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URLConnection;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import static javafx.geometry.Pos.CENTER;
import javafx.scene.layout.FlowPane;
import static javax.swing.text.StyleConstants.Orientation;


/**
 *
 * @author Peter
 */
public class NobelPrize extends Application {
    
    //@FXML
    private Button button1; 
    Label response;
    Label result;
    Label countryList;
    Label countryPick;
    
    //creates the user input screen
    @Override
    public void start(Stage stage) throws Exception {
        
        //set the Stage title
        stage.setTitle("Nobel Prize Quest");

        //Create a Flowpane for the root node 
        FlowPane rootPlane = new FlowPane(10,10);
        //put elements in topleft  of rootPlane
        rootPlane.setAlignment(Pos.TOP_LEFT);
        //Set rootPlane inside the Scene (size of 300 X 100)
        Scene scene = new Scene(rootPlane, 300, 100);

        //Labels for the search screen
        response = new Label("Search");
        result = new Label("");
        countryList= new Label("Select Country");
        countryPick=new Label ("");
        
        //Create a List of countries 
        ObservableList<String> countries= FXCollections.observableArrayList(
        "Canada","USA", "Argentina","Costa Rica","Colombia","Cyprus","Denmark");
        
        //Create the list view and set height and width
        ListView<String> listViewCountries = new ListView<String> (countries);
        listViewCountries.setPrefSize (100,50);
        //set the selection model
        MultipleSelectionModel<String> selectListViewCountries =listViewCountries.getSelectionModel();
        
        //when user selects a country, respond to change of selection
        selectListViewCountries.selectedItemProperty().addListener(new ChangeListener<String>(){
            public void selectionMade  (ObservableValue<? extends String> selectionMade, 
                String defaultVal, String countryPicked){
                //show the country picked
                countryPick.setText("Country selected is: " + countryPicked);
                }   

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            });
        
        //create a new ScrollPane to hold the results
        ScrollPane results = new ScrollPane();
        Scene resultsScene = new Scene(results);
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
        
        //rootPlane.getChildren().addAll(button1, response);
        rootPlane.getChildren().addAll(listViewCountries, countryPick);
        stage.show();

       
    }
    
    
    
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
        
        //Create a Reader obj to hold text from a character stream
        //and use InputStreamReader to decode bytes into characters
        Reader reader;
        reader = new InputStreamReader(nobelDataStream);
    
        //call readData to put all the text into a string and put in JSONOBject
        String jsonText = readData(reader);
        JSONObject jsonObj;
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
        String bornCountry="";
        //Search for laureates by country of birth based on country code
        String bornCountryCode="";
        //Search for laureates by city of birth
        String bornCity="";
        //Search for laureates by country where they died
        String diedCountry="";
        String diedCountryCode="";
        String diedCity="";
        String bornDate="";
        String bornDateTo="";
        String diedDate="";
        String diedDateTo="";
        //Search for laureates based on the motivation of receiving the prize
        String motivation="";
        String gender="";
        String affiliation="";
        
        //add in fields to end of URL eg. ?category=phyics&year=2000&yearto=2001';
        //concatenate string eg. url+..+..;  
     
       
        //launch JavaFX application
        launch();
        
    }    
}