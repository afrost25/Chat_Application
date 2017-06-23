package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientGUI extends Application
{
	//Username for client
	String username;
	
	//Fields for the GUI interface
	TextField messageText;
	TextArea chatText;
	Button sendBtn;
	PrintWriter clientInput;
	
	//Field for server connection
	Socket socket;
	
	//Constructor
	public ClientGUI(Socket s, String username)throws IOException
	{
		//Initializes socket for server connection
		socket = s;
		
		//Initializes GUI elements
		messageText = new TextField();
		chatText = new TextArea();
		sendBtn = new Button("Send");
		
		//Lets user print messages to server's stream
		clientInput = new PrintWriter(s.getOutputStream(), true);
		
		//Initializes client's Username
		this.username = username;
		
		//Starts thread to receive messages from server
		new Thread(new ReceiveThread(s, chatText)).start();
		
	}
	
	//Sets up GUI interface
	@Override
	public void start(Stage primaryStage)
	{	
		sendBtn.setMaxWidth(100);
		chatText.setEditable(false);
		sendBtn.setOnAction(new click_EventHandler());
		
		VBox vbox = new VBox(messageText, sendBtn);
		vbox.setAlignment(Pos.CENTER);
		VBox.setMargin(messageText, new Insets(10));
		VBox.setMargin(sendBtn, new Insets(10));
		
		BorderPane pane = new BorderPane();
		pane.setCenter(chatText);
		pane.setBottom(vbox);
		BorderPane.setMargin(chatText, new Insets(10));
		
		Scene scene = new Scene(pane, 400, 400);
				
		primaryStage.setOnCloseRequest(new close_EventHandler());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chat Client");
		primaryStage.show();
		
		//Broadcasts message to clients on server that user has joined
		clientInput.println(username + " has joined the chat");
	}
	
	
	private class click_EventHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			if(e.getSource() == sendBtn)
			{
				
				clientInput.println(username + ": " + messageText.getText());
				
				//Clears textbox
				messageText.setText("");
			}
		}
	}
	
	//Disconnects the client when closed down with the red "X"
	private class close_EventHandler implements EventHandler<WindowEvent>
	{
		@Override
		public void handle(WindowEvent event)
		{
			try 
			{
				clientInput.println(username + " disconnected");
				socket.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
