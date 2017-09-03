package trash;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

@Deprecated
public class Driver
{
	public static void main(String[] args) throws UnknownHostException, SQLException
	{
		try {
			Socket s = new Socket("73.67.251.8", 25565);
			
			if(s.isConnected())
			{
				System.out.println("Hi");
			}
			else
			{
				System.out.println("Hello");
			}
			
			s.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
