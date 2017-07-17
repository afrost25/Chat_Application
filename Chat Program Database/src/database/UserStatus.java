package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStatus 
{
	Database_Command dc;
	
	public UserStatus(String IPAddress)
	{
		try
		{
			dc = new Database_Command(IPAddress);
		}
		catch(SQLException e)
		{
			
		}
	}
	
	public void onlineStatus(String username, boolean status)
	{
		try
		{
		if(status)
			dc.update("update members set Status = 'Online' where username = " + "'" + username + "'");
		
		else
			dc.update("update members set Status = 'Offline' where username = " + "'" + username + "'");
		}
		catch(SQLException e)
		{
			
		}
	}
	
	public String getUsersOnline()
	{
		String users = "";
		try
		{
			ResultSet rs = dc.execute("select username from members where status = 'Online'");
			
			while(rs.next())
			{
				users += rs.getString("Username");
				users += "\n";
			}
		}
		catch(SQLException e)
		{
			
		}
		
		return users;
	}
}
