package server;

import java.util.Vector;

import javafx.scene.control.TextArea;

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
						
						//Command codes						
						String[] arrayMessage = message.split(" ");
						if(arrayMessage[1].contains("/"))
						{
							if(arrayMessage[1].contains("/online"))
							{
								String users = "Users online: \n";
								
								for(int k = 0; k < v.size(); k++)
								{
									users += v.get(k).getUser();
									
									if(k < v.size() - 1)
										users += "\n";
								}
								
								v.get(i).sendMSG(users);
							}
							
							//...
						}
						
						else if(message.contains("@"))
						{
							for(int k = 0; k < v.size(); k++)
							{
								if(message.contains("@" + v.get(k).getUser()))
								{
									System.out.println(v.get(k).getUser());
									v.get(k).sendMSG(message);
								}
							}
							
							v.get(i).sendMSG(message);
							serverText.appendText(message + "\n");
						}
						
						else
						{
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
