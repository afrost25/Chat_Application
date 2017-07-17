package client;

import database.UserStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UsernameThread
{
	UserStatus us;
	volatile StringProperty users;
	
	public UsernameThread(UserStatus us)
	{
		this.us = us;
		this.users = new SimpleStringProperty(this, "users", us.getUsersOnline());
	}
	
	public StringProperty usersProperty()
	{
		return users;
	}
	
	public void refresh()
	{
		users.set(us.getUsersOnline());
	}
}