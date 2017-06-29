package server;

import java.util.Vector;

public class ClientCommands 
{
	private ClientCommands() {}
	
	public static String getUsersOnline(Vector<StreamConnector> v)
	{
		String list = "Users Online: \n";
		
		for(int i = 0; i < v.size(); i++)
		{
			list += v.get(i).getUser();
			
			if(i != v.size() - 1)
				list += "\n";
		}
		
		return list;
	}
	
	public static String getHelp()
	{
		
		String message = "List of commands: \n"
				+ "/online";
		return message;
	}

	public static boolean atSymbol(String message)
	{
		boolean isValid = false;
		
		if(message.indexOf("@") == 0 || (message.charAt(message.indexOf("@") + 1) != ' ' 
				&& message.charAt(message.indexOf("@") - 1) == ' '))
			isValid = true;
		
		return isValid;
	}
}
