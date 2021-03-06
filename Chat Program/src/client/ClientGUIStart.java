package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import exceptions.InvalidUsernameException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * Client Application starting point!
 */

public class ClientGUIStart extends Application
{
	//Main method
	public static void main(String args[])
	{
		launch(args);
	}
	
	//Elements for GUI
	Label nameLbl, IPLbl, portLbl;
	TextField nameText, IPText, portText;
	Button connectBtn;
	Stage stage;
	
	//Elements for initializing GUI
	@Override
	public void start(Stage primaryStage)
	{
		stage = primaryStage;
		
		nameLbl = new Label("Name:");
		IPLbl = new Label("Host:");
		portLbl = new Label("Port:");
		
		nameText = new TextField();
		IPText = new TextField();
		portText = new TextField();
		
		connectBtn = new Button("Connect");
		connectBtn.setOnAction(e->connectButton_EventHandler());
		
		VBox vboxLabels = new VBox();
		vboxLabels.getChildren().addAll(nameLbl, IPLbl, portLbl);
		vboxLabels.setAlignment(Pos.CENTER);
		VBox.setMargin(nameLbl, new Insets(10));
		VBox.setMargin(IPLbl, new Insets(10));
		VBox.setMargin(portLbl, new Insets(10));
		
		VBox vboxText = new VBox();
		vboxText.getChildren().addAll(nameText, IPText, portText);
		vboxText.setAlignment(Pos.CENTER);
		VBox.setMargin(nameText, new Insets(5, 10, 5, 0));
		VBox.setMargin(IPText, new Insets(5, 10, 5, 0));
		VBox.setMargin(portText, new Insets(5, 10, 5, 0));
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(vboxLabels, vboxText);
		hbox.setAlignment(Pos.CENTER);
		
		VBox vboxButton = new VBox();
		vboxButton.getChildren().addAll(hbox, connectBtn);
		vboxButton.setAlignment(Pos.CENTER);
		
		BorderPane border = new BorderPane();
		border.setCenter(vboxButton);
		
		Scene scene = new Scene(border, 250, 150);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Welcome!");
		primaryStage.show();
	}
	
	//Button for GUI that makes server connection
	private void connectButton_EventHandler()
	{
		try
		{	
			if(nameText.getText().isEmpty())
				throw new InvalidUsernameException();

			//Creates a socket that attempts to connect to the server with the given IP address and port number
			Socket s = new Socket(InetAddress.getByName(IPText.getText()), Integer.parseInt(portText.getText()));
			
			//Creates an object that writes messages to the server
			PrintWriter ClientOutput = new PrintWriter(s.getOutputStream(), true);
			
			ClientOutput.println(s.getInetAddress().getHostAddress());
			ClientOutput.println(nameText.getText());
			
			//Launches ClientGUI after successful connection to server. 
			//Passes the socket and username as parameters for the class
			new ClientGUI(s, nameText.getText()).start(stage);
		}
		
		//Error handling for program.
		catch(UnknownHostException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Connection");
			alert.setHeaderText("Host Connection Failed");
			alert.setContentText("Unable to establish connection with the server");
			
			alert.showAndWait();
		}
		
		catch(SocketException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Connection");
			alert.setHeaderText("Port Connection Failed");
			alert.setContentText("Unable to establish connection with the server");
			
			alert.showAndWait();
		}
		
		catch(NumberFormatException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Connection");
			alert.setHeaderText("Invalid Port Number");
			alert.setContentText("Enter numerical values only");
			
			alert.showAndWait();
		}
		
		catch(InvalidUsernameException e)
		{
			e.message();
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
