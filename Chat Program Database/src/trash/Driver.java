package trash;

import java.sql.SQLException;

import database.Login_Helper;

//import java.sql.*;
@Deprecated
public class Driver {

	public static void main(String[] args) throws SQLException 
	{
				
		//Need to create class that can manipulate data in the server
		
		
		Login_Helper cv = new Login_Helper();
		cv.recordTime("afrost25");
		
	}

}
