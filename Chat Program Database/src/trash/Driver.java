package trash;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Deprecated
public class Driver
{
	public static void main(String[] args) throws UnknownHostException
	{
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}
}
