package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.Vector;

import javafx.scene.control.TextArea;

public class ServerHelper 
{
	static Scanner scan = new Scanner(System.in);
	static ConnectionThread connection;
	static MessengerThread messenger;
	static ServerSocket ss;
	private ServerHelper()
	{
		
	}
	
	public static void startServer(Vector<StreamConnector> v, int port, TextArea serverText)
	{
		try 
		{
			 ss = new ServerSocket(port);
			
			new Thread(connection = new ConnectionThread(v, ss, serverText)).start();
			
			serverText.appendText("Success! Listening on port " + port + "\n");
			serverText.appendText("Waiting For Clients..." + "\n");
			
			new Thread(messenger = new MessengerThread(v, serverText)).start();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void stopServer() throws IOException
	{
		
		messenger.stopService();
		ss.close();
		scan.close();
	}
}
