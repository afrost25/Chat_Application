package trash;

import java.net.Socket;

@Deprecated
//Not used in the program
//May be subject to deletion?
public class ClientHelper
{
	private ClientHelper() 
	{
		
	}
	
	public static void startChatService(Socket s, String username)
	{
		//Thread send = new Thread(new SendThread(s, username));
		//send.start();
		
		//Thread receive = new Thread(new ReceiveThread(s));
		//receive.start();
		
		try 
		{
			Thread.sleep(100);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		//System.out.println("Type to talk");
	}
}
