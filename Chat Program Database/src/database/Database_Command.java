package database;

import java.sql.*;

public class Database_Command 
{
	Connection connection;
	Statement statement;
	
	public Database_Command(String IPAddress)throws SQLException
	{
		//Create a statement for the query
		
		//Connect to the database
		connection = DriverManager.getConnection("jdbc:mysql://"+ IPAddress +":25568/server_clients?autoReconnect=true&useSSL=false", "Java Program", "Agentfrost1");
		statement = connection.createStatement();
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
