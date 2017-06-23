package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Credential_Validation
{
	private Credential_Validation()
	{
		
	}
	
	public static boolean isUser(String username, String password)
	{
		boolean isValid = false;
		try 
		{
			ResultSet rs = Database_Command.execute("select username, password from members where username = " + "'" + username + "'"
					+ "and password = " + "'" + password +"'");
			
			if(rs.next())
				isValid = true;
			
		}
			
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return isValid;
	}
}
