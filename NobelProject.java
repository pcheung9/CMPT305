/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;
import javax.Json;


/**
 *
 * @author User
 */
public class NobelProject extends Application {
    
    /*
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    */
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        //start with the base URL
        String url ="http://api.nobelprize.org/v1/prize.json";
        
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
        url="http://api.nobelprize.org/v1/prize.json?category=phyics&year=2000&yearto=2001";
       
        //create a URL object to identify the webaddress
        URL nobelAPI = null;
        try {
            nobelAPI = new URL (url);
        } catch (MalformedURLException excep){
            System.out.println("MalformedURLException");
        }

        //Creat URLConnection to access the actual content information of the URL
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
        
        //create a JsonReader object which reads a JSON Object or an array
        //structure from an input source
        JsonReader parser = Json.createReader (nobelDataStream);
        
    }
}
    
