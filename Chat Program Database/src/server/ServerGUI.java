package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.Vector;

import database.UserStatus;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerGUI extends Application
{
	//Text box on the server log
	static TextArea serverText = new TextArea();
	static boolean serviceRun = true;
	static Vector<StreamConnector> v;
	ServerHelper sh;
	UserStatus us;
	
	Task<Void> heartbeat;
	
	int portNum;
	
	public ServerGUI(int pn)
	{
		portNum = pn;
		sh = new ServerHelper();
		us = new UserStatus("127.0.0.1");
		heartbeat = new ServerHeartbeat();
		new Thread(heartbeat).start();
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		//users.textProperty().bind(updater.messageProperty());
		
		//Appends the text "Chat Server" to server log
		serverText.appendText("Chat Server" + "\n");
		
		//Creates a vector stream for the clients that are connecting to the server
		//As of right now, the server is limited to ten clients, but will eventually change to adjustable amount
		v = new Vector<StreamConnector>(10);
		
		//Starts the server
		sh.startServer(v, portNum, serverText);
		
		serverText.setEditable(false);
		
		BorderPane pane = new BorderPane(serverText);
		BorderPane.setMargin(serverText, new Insets(20));
		
		Scene scene = new Scene(pane, 500, 500);
		scene.getStylesheets().add("style.css");
		
		primaryStage.setOnCloseRequest(new WindowEventHandler());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Server Log");
		primaryStage.show();
	}
	
	//Closes server when window closes
	private class WindowEventHandler implements EventHandler<WindowEvent>
	{
		@Override
		public void handle(WindowEvent event)
		{
			try {
				sh.stopServer();
				
				for(int i = 0; i < v.size(); i++)
				{
					us.onlineStatus(v.get(i).getUser(), false);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class MessengerThread implements Runnable
	{
		@Override
		public void run()
		{
			while(serviceRun)
			{
				while(v.size() != 0 && serviceRun)
				{		
					messengerHelper();
				}
			}
		}
		
		public void stopService()
		{
			serviceRun = false;
			
			for(int i = 0; i < v.size(); i++)
			{
				v.get(i).sendMSG("Disconnected: Server is closed");
			}
		}
		
		public void messengerHelper()
		{
			for(int i = 0; i < v.size(); i++)
			{	
				if(v.get(i).hasNextLine())
				{
					String message = v.get(i).getMSG();		
					
					//@ Codes
					if(message.contains("@"))
					{
						for(int k = 0; k < v.size(); k++)
						{
							if(message.contains("@" + v.get(k).getUser()))
							{
								v.get(k).sendMSG(message);
							}
						}
						v.get(i).sendMSG(message);
						serverText.appendText(message + "\n");
					}
					
					else if(message.equals(v.get(i).getUser()))
					{
						v.get(i).sendMSG("Connected...");
						v.get(i).isBeating = true;
					}
					//Regular messaging service
					else
					{
						for(int j = 0; j < v.size(); j++)
							v.get(j).sendMSG(message);
						
						serverText.appendText(message + "\n");
					}		
					
					if((!message.contains(":")) && message.contains("disconnected"))
						v.remove(i);
				}
			}
		}
	}
	
	private class ServerHelper 
	{
		Scanner scan = new Scanner(System.in);
		MessengerThread messenger;
		ServerSocket ss;
		
		public void startServer(Vector<StreamConnector> v, int port, TextArea serverText)
		{
			try 
			{
				 ss = new ServerSocket(port);
				
				new Thread(new ConnectionThread(v, ss)).start();
				
				serverText.appendText("Success! Listening on port " + port + "\n");
				serverText.appendText("Waiting For Clients..." + "\n");
				
				new Thread(messenger = new MessengerThread()).start();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		public void stopServer() throws IOException
		{
			
			messenger.stopService();
			ss.close();
			scan.close();
		}
	}
	
	private class ServerHeartbeat extends Task<Void>
	{
		@Override
		public Void call()
		{
			while(serviceRun)
			{
				for(int i = 0; i < v.size(); i++)
				{
					v.get(i).isBeating = false;
				}
				
				try
				{
					Thread.sleep(20_000);
				}
				catch(InterruptedException e)
				{
					System.err.println("Fatal Error: Skipped_Beat");
				}
				
				for(int i = 0; i < v.size(); i++)
				{
					if(!v.get(i).isBeating)
					{
						String message = v.get(i).getUser() + " disconnected\n";
						v.remove(i);
						
						serverText.appendText(message);
						
						for(int j = 0; j < v.size(); j++)
							v.get(j).sendMSG(message);
					}
				}
			}

			return null;
		}
	}
}