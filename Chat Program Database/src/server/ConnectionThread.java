package server;

import java.io.IOException;
import java.net.ServerSocket;
//import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import javafx.scene.control.TextArea;

public class ConnectionThread implements Runnable{

	Vector<StreamConnector> v;
	ServerSocket ss;
	TextArea serverText;
	volatile boolean serviceRun = true;
	
	public ConnectionThread(Vector<StreamConnector> v, ServerSocket ss)
	{
		this.v = v;
		this.ss = ss;
	}
	
	@Override
	public void run()
	{	
		while(serviceRun)
		{
			try 
			{
				v.add(new StreamConnector(ss.accept()));	
			}
			catch(SocketException e)
			{
				serviceRun = false;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
