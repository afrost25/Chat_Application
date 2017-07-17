package trash;

import java.util.Vector;

import javafx.scene.control.TextArea;
import server.StreamConnector;

@Deprecated
public class MessengerThread implements Runnable
{
	Vector<StreamConnector> v;
	TextArea serverText;
	boolean serviceRun = true;
	
	public MessengerThread(Vector<StreamConnector> v, TextArea serverText)
	{
		this.v = v;
		this.serverText = serverText;
	}
	
	@Override
	public void run()
	{
		while(serviceRun)
		{
			while(v.size() != 0 && serviceRun)
			{		
				for(int i = 0; i < v.size(); i++)
				{	
					if(v.get(i).hasNextLine())
					{
						String message = v.get(i).getMSG();		
						serverText.appendText(message + "\n");
					
						for(int j = 0; j < v.size(); j++)
						v.get(j).sendMSG(message);
						
						if((!message.contains(":")) && message.contains("disconnected"))
							v.remove(i);
					
					}
				}
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
}
