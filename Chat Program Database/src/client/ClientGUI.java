package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import database.UserStatus;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
//import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientGUI extends Application
{
	volatile boolean isRunning = true;
	
	//Username for client
	String username;
	String message;
	
	//String Property
	StringProperty connectionStatus = new SimpleStringProperty(this, "connectionStatus", "Online");
	
	//Fields for the GUI interface
	ScrollPane sp;
	TextField messageText;
	TextFlow chatText;
	Button sendBtn;
	Label users;
	Label online;
	Label status;
	Label info;
	Label connectionStatusLbl;
	
	//Task
	Task<Void> updater;
	ClientHeartbeat heartbeat;
	
	//UserStatus
	UserStatus us;
	
	PrintWriter clientInput;
	
	//Field for server connection
	Socket socket;
	
	//Constructor
	public ClientGUI(Socket s, String username)throws IOException
	{
		
		//Starts thread to receive messages from server
		new Thread(new ReceiverThread()).start();
		
		//Initializes socket for server connection
		socket = s;
		
		//Online status
		us = new UserStatus(s.getInetAddress().getHostAddress());	
		
		//Lets user print messages to server's stream
		clientInput = new PrintWriter(s.getOutputStream(), true);
		
		//Initializes client's Username
		this.username = username;
		
		us.onlineStatus(username, true);
		
		//Updates label
		updater = new LabelUpdater();
		new Thread(updater).start();
		
		//Connection label
		heartbeat = new ClientHeartbeat();
		new Thread(heartbeat).start();
	}
	
	@Override
	public void init()
	{
		//Initializes GUI elements
		chatText = new TextFlow();
		sp = new ScrollPane(chatText);
		messageText = new TextField();
		sendBtn = new Button("Send");
		users = new Label("Online:");
		online = new Label();
		status = new Label("Status:");
		info = new Label();
		
		chatText.setPadding(new Insets(10));
		
		connectionStatusLbl = new Label();
		
		connectionStatusLbl.textProperty().bind(heartbeat.messageProperty());
		
		messageText.setOnKeyPressed(new key_EventHandler());
		
		sp.setStyle("-fx-background: gray");
		
		sendBtn.setMaxWidth(100);
		//chatText.setEditable(false);
		//chatText.setWrapText(true);
		sendBtn.setOnAction(new click_EventHandler());
		
		info.setText("Server IP: " + socket.getInetAddress().getHostAddress() +"\n"
				+ "Server Port: " + socket.getPort());
		
		online.textProperty().bind(updater.messageProperty());
	}
	
	//Sets up GUI interface
	@Override
	public void start(Stage primaryStage)
	{	
		init();
		
		VBox onlinePanel = new VBox(users, online);
		VBox.setMargin(users, new Insets(10, 30, 0, 30));
		VBox.setMargin(online, new Insets(10, 30, 30, 30));
		onlinePanel.setPadding(new Insets(0, 0, 150, 0));
		onlinePanel.setStyle("-fx-border-style: solid inside;"
				+ "-fx-border-width: 0.5px;"
				+ "-fx-border-color: gray;"
				+ "-fx-border-insets: 20 10 10 10;");
		
		VBox statusPanel = new VBox(status, info, connectionStatusLbl);
		VBox.setMargin(status, new Insets(10, 30, 10, 30));
		VBox.setMargin(info, new Insets(10, 30, 0, 30));
		VBox.setMargin(connectionStatusLbl, new Insets(0, 30, 10, 30));
		statusPanel.setPadding(new Insets(0, 0, 50, 0));
		statusPanel.setStyle("-fx-border-style: solid inside;"
				+ "-fx-border-width: 0.5px;"
				+ "-fx-border-color: gray;"
				+ "-fx-border-insets: 20 10 10 10;");
		
		VBox rootPanel = new VBox(onlinePanel, statusPanel);
		
		VBox messenger = new VBox(messageText, sendBtn);
		messenger.setAlignment(Pos.CENTER);
		messenger.setPadding(new Insets(10,10,10,210));
		VBox.setMargin(messageText, new Insets(10));
		VBox.setMargin(sendBtn, new Insets(10));	
		
		BorderPane pane = new BorderPane();
		pane.setCenter(sp);
		pane.setBottom(messenger);
		pane.setLeft(rootPanel);
		
		BorderPane.setMargin(sp, new Insets(20,20,20,10));
		
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("style.css");
		
		//primaryStage.getIcons().add(new Image(""));
		primaryStage.setHeight(500);
		primaryStage.setWidth(500);
		primaryStage.setResizable(true);
		primaryStage.setOnCloseRequest(new close_EventHandler());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chat Client");
		primaryStage.show();
		
		//Broadcasts message to clients on server that user has joined
		clientInput.println(username + " has joined the chat");
	}
	
	@Override
	public void stop()
	{
		isRunning = false;
	}
	
	private class key_EventHandler implements EventHandler<KeyEvent>
	{
		@Override
		public void handle(KeyEvent e)
		{
			switch(e.getCode())
			{
				case ENTER: sendBtn.fire(); break;
				default: break;
			}
		}
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
				us.onlineStatus(username, false);
				socket.close();
				stop();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	//Class for Message Receiver
	private class ReceiverThread extends Task<Void>
	{
		@Override
		public Void call()
		{
			
			Platform.runLater(()->{chatText.getChildren().add(new Text("Welcome " + username + "\n"));});
			try
			{
				//Scans for information in the socket stream
				Scanner ServerInput = new Scanner(socket.getInputStream());
				
				while(ServerInput.hasNext())
				{
					//Takes information from socket stream and appends it to text area box on client
					message = ServerInput.nextLine();
					
					if(message.equals("Connected..."))
					{
						heartbeat.setStatus(true);
					}
					
					else
					{
						Text styledMessage = new Text();
						
						if((message.contains("has joined the chat") || message.contains("has disconnected"))&& !message.contains(":"))
						{
							styledMessage.setText(message + "\n");
							styledMessage.setFill(Color.TURQUOISE);
							styledMessage.setStyle("-fx-font-weight: bold");
							
							Platform.runLater(()->{chatText.getChildren().add(styledMessage);});
						}
						
						else
						{
							Text user = new Text(message.substring(0, message.indexOf(":")));
							user.setFill(Color.TURQUOISE);
							user.setStyle("-fx-font-weight: bold");
							
							Platform.runLater(()->{chatText.getChildren().addAll(user, new Text(message.substring(message.indexOf(":")) + "\n"));});
						}
						
						
					}
				}	
				
				ServerInput.close();
			}
			
			catch(IOException e)
			{
				System.err.println("Error: Cannot Receive Message");
			}
			
			return null;
		}
	}
	
	private class LabelUpdater extends Task<Void>
	{
		@Override
		public Void call()
		{
			while(isRunning)
				updateMessage(us.getUsersOnline());
			
			return null;
		}
	}
	
	private class ClientHeartbeat extends Task<Void>
	{
		private boolean isConnected;
		
		@Override
		public Void call()
		{
			while(isRunning)
			{
				try 
				{
					isConnected = false;
					clientInput.println(username);
					updateMessage("Testing  ");
					
					Thread.sleep(10_000);
					
					if(!isConnected)
					{
						updateMessage("Disconnected");
					}
					
					else
					{
						updateMessage("Connected");
					}
					
					Thread.sleep(10_000);
				} 
				catch (InterruptedException e) 
				{
					System.err.println("Fatal Error: Skipped_Beat");
				}
				
				
			}
			
			return null;
		}
		
		public void setStatus(boolean connection)
		{
			isConnected = connection;
		}
	}
}


