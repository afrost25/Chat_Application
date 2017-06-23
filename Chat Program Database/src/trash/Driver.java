package trash;

import java.sql.SQLException;

import database.Database_Command;
//import database.Credential_Validation;
import date.TimeStamp;

//import java.sql.*;
@Deprecated
public class Driver {

	public static void main(String[] args) 
	{
		String username = "AceinEights";
		TimeStamp ts = new TimeStamp();
		
		//Need to create class that can manipulate data in the server
		
		try {
			Database_Command.execute("update members set Time = " + "'" + ts.toString() + "'" 
					+ " where username = " + "'" + username + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*if(!Username_Validation.isMember("afro8hg"))
		{
			System.out.println("Doge");
		}*/
		
		/*try 
		{
			String username = "afrost25";
			ResultSet rs = Database_Command.execute("select username, password from members where username = " + "'" + username + "'");
			while(rs.next())
			{
				System.out.println(rs.getString("Username") + ", " + rs.getString("Password"));
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

}
