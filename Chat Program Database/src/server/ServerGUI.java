package server;

import java.io.IOException;
import java.util.Vector;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class ServerGUI extends Application
{
	//Text box on the server log
	TextArea serverText = new TextArea();
	
	int portNum;
	
	public ServerGUI(int pn)
	{
		portNum = pn;
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		//Appends the text "Chat Server" to server log
		serverText.appendText("Chat Server" + "\n");
		
		//Creates a vector stream for the clients that are connecting to the server
		//As of right now, the server is limited to ten clients, but will eventually change to adjustable amount
		Vector<StreamConnector> v = new Vector<StreamConnector>(10);
		
		//Starts the server
		ServerHelper.startServer(v, portNum, serverText);
		
		serverText.setEditable(false);
		
		Scene scene = new Scene(serverText, 400, 400);
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
				ServerHelper.stopServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
