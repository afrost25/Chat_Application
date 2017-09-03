package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class StreamConnector
{
	private Scanner ClientInput;
	private PrintWriter ServerInput;
	private InputStream inputStream;
	private String username;
	private String IP;
	boolean isBeating = true;
	
	public StreamConnector(Socket socket)
	{
		try
		{
			inputStream = socket.getInputStream();
			ClientInput = new Scanner(inputStream);
			ServerInput = new PrintWriter(socket.getOutputStream(), true);
			
			IP = ClientInput.nextLine();
			username = ClientInput.nextLine();
			
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
	
	public String getIP()
	{
		return IP;
	}
}

