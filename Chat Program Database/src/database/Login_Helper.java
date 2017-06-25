package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import date.TimeStamp;

public class Login_Helper
{
	
	Database_Command  database;
	TimeStamp timestamp;
	
	public Login_Helper()
	{
		database = new Database_Command();
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
			database.update("update members set Time = " + "'" + time + "'" +" where username = " + "'" + username + "'");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
