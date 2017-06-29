package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.scene.control.TextArea;

public class StreamConnector
{
	Scanner ClientInput;
	PrintWriter ServerInput;
	InputStream inputStream;
	String username;
	
	public StreamConnector(Socket socket, TextArea serverText)
	{
		try
		{
			inputStream = socket.getInputStream();
			ClientInput = new Scanner(inputStream);
			ServerInput = new PrintWriter(socket.getOutputStream(), true);
			
			String IP = ClientInput.nextLine();
			username = ClientInput.nextLine();
			
			serverText.appendText(username + " IP: " + IP + "\n");
			ServerInput.println("Welcome " + username);
			
		}
		catch(IOException e)
		{
			e.getStackTrace();
		}
	}
	
	public void sendMSG(String msg)
	{
		ServerInput.println(msg);
	}
	
	public String getMSG()
	{
		return ClientInput.nextLine();
	}
	
	public String getUser()
	{
		return username;
	}
	
	public boolean hasNextLine()
	{	
		boolean hasNext = false;
		
		try
		{
			hasNext = (inputStream.available() != 0);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return hasNext;
	}
}

