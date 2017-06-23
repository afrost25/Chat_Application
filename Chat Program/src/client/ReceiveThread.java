package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import javafx.scene.control.TextArea;

//Class that is used to receive messages from the server and clients
public class ReceiveThread implements Runnable
{
	Socket s;
	TextArea ta;
	
	public ReceiveThread(Socket s, TextArea ta)
	{
		this.s = s;
		this.ta = ta;
	}
	
	@Override
	public void run()
	{

		try
		{
			//Scans for information in the socket stream
			Scanner ServerInput = new Scanner(s.getInputStream());
			String message = "";
			
			while(ServerInput.hasNextLine())
			{
				//Takes information from socket stream and appends it to text area box on client
				message = ServerInput.nextLine();
				ta.appendText(message + "\n");
			}
			
			ServerInput.close();
		}
		
		catch(IOException e)
		{
			//System.err.println("Error: Cannot Receive Message");
		}
	}
}
