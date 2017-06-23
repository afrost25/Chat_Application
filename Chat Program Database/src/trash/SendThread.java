package trash;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Deprecated
//Not used in the program
//May be subject to deletion?
public class SendThread implements Runnable
{
	Socket socket;
	String username;
	
	public SendThread(Socket s, String username)
	{
		socket = s;
		this.username = username;
	}
	
	@Override
	public void run()
	{
		try
		{
			Scanner scan  = new Scanner(System.in);
			PrintWriter ClientOutput = new PrintWriter(socket.getOutputStream(), true);
			
			String message = "";
			
			while(!message.equalsIgnoreCase("/quit"))
			{
				message = scan.nextLine();
				ClientOutput.println(username + ": " + message);
			}
			
			try 
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			ClientOutput.println(username + " disconnected");
			
			try 
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			scan.close();
			ClientOutput.close();
		}
		
		catch(IOException e)
		{
			e.getStackTrace();
		}
	}
}
