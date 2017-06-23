package database;

import java.sql.*;

public class Database_Command 
{
	static Connection connection;
	static Statement statement;
	
	private Database_Command()
	{
		//Create a statement for the query
		try {
			//Connect to the database
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/server_clients", "root", "Agentfrost1");
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Method gives user capability to send commands to the server
	public static ResultSet execute(String args)throws SQLException
	{	
		//Returns ResultSet
		return statement.executeQuery(args);
		
	}
	
	public static void update(String args)
	{
		
	}
}
