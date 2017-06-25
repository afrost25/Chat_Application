package database;

import java.sql.*;

public class Database_Command 
{
	Connection connection;
	Statement statement;
	
	public Database_Command()
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
	public ResultSet execute(String args)throws SQLException
	{	
		//Returns ResultSet
		return statement.executeQuery(args);
		
	}
	
	public void update(String args)throws SQLException
	{
		statement.executeUpdate(args);
	}
	
}
