package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import date.TimeStamp;
import exceptions.OnlineException;

public class Login_Helper
{
	
	Database_Command  database;
	TimeStamp timestamp;
	UserStatus status;
	
	public Login_Helper(String IPAddress)throws SQLException
	{
		status = new UserStatus(IPAddress);
		database = new Database_Command(IPAddress);
		timestamp = new TimeStamp();
	}
	
	public boolean isUser(String username, String password)
	{
		boolean isValid = false;
		try 
		{
			ResultSet rs = database.execute("select username, password from members where username = " + "'" + username + "'"
					+ "and password = " + "'" + password +"'");
			
			if(rs.next())
				isValid = true;
			
		}
			
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		catch(NullPointerException e)
		{
			isValid = false;
		}
		
		return isValid;
	}
	
	public void recordTime(String username)
	{
		String time = timestamp.toString();
		
		try
		{
			database.update("update members set time = " + "'" + time + "'" +" where username = " + "'" + username + "'");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void isOnline(String username) throws OnlineException
	{		
		if(status.getUsersOnline().contains(username))
			throw new OnlineException();
	}
}
