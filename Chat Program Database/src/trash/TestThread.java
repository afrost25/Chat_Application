package trash;

import database.UserStatus;

@Deprecated
public class TestThread implements Runnable
{
	UserStatus us;
	Driver dr;
	
	public TestThread(UserStatus us, Driver dr)
	{
		this.dr = dr;
		this.us = us;
	}
	
	@Override
	public void run()
	{
	}
}
