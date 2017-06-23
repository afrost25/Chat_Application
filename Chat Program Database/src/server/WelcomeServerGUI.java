package server;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/*
 * Class to start server
 */
public class WelcomeServerGUI extends Application
{
	Label port;
	TextField portNum;
	Button start;
	Stage stage;
	
	public static void main(String args[])
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		stage = primaryStage;
		
		port = new Label("Port: ");
		portNum = new TextField();
		start = new Button("Start");
		
		start.setOnAction(e->start_EventHandler());
		
		HBox portBox = new HBox(port, portNum, start);
		HBox.setMargin(port, new Insets(10));
		HBox.setMargin(portNum, new Insets(10));
		HBox.setMargin(start, new Insets(10));
		
		Scene scene = new Scene(portBox);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Welcome");
		primaryStage.show();
	}
	
	private void start_EventHandler() throws NumberFormatException
	{
		int portNumber;
			
		try
		{
			//Gets port number for which the server is going to listen for clients
			portNumber = Integer.valueOf(portNum.getText());
			
			//If valid port number, then application starts serverGUI
			new ServerGUI(portNumber).start(stage);
		}
		catch(NumberFormatException e)
		{
			portNum.setText("");
			Alert alert = new Alert(AlertType.ERROR, "Invalid Port Number");
			alert.show();
			start(stage);
		}
	}
}
