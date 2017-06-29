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
						
						if(message.indexOf("/") == 0)
						{
							if(message.contains("online"))
								v.get(i).sendMSG(ClientCommands.getUsersOnline(v));
							
							else if(message.contains("help"))
								v.get(i).sendMSG(ClientCommands.getHelp());
							
							else
								v.get(i).sendMSG("Unknown Command. Type /help for commmand information");
						}
						
						else if(ClientCommands.atSymbol(message))
						{
							for(int k = 0; k < v.size(); k++)
							{
								if(message.contains("@" + v.get(k).getUser()))
								{
									v.get(i).sendMSG(message);
									v.get(k).sendMSG(message);
									serverText.appendText(message + "\n");
								}
								
								else
								{
									v.get(i).sendMSG("This user is not online");
								}
							}
						}
						
						else
						{
							serverText.appendText(message + "\n");
						
							for(int j = 0; j < v.size(); j++)
							v.get(j).sendMSG(message);
						}
						
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
