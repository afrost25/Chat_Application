package date;

import java.time.LocalDateTime;
import java.time.Month;

public class TimeStamp 
{
	LocalDateTime ldt;
	int year;
	Month Emonth;
	String month;
	int day;
	
	int hour;
	int minute;
	int second;
	
	public TimeStamp()
	{
		LocalDateTime ldt = LocalDateTime.now();
		year = ldt.getYear();
		Emonth = ldt.getMonth();
		day = ldt.getDayOfMonth();
		
		switch(Emonth)
		{
			case JANUARY:
				month = "01";
				break;
				
			case FEBRUARY:
				month = "02";
				break;
				
			case MARCH:
				month = "03";
				break;
				
			case APRIL:
				month = "04";
				break;
				
			case MAY:
				month = "05";
				break;
				
			case JUNE:
				month = "06";
				break;
				
			case JULY:
				month = "07";
				break;
				
			case AUGUST:
				month = "08";
				break;
				
			case SEPTEMBER:
				month = "09";
				break;
				
			case OCTOBER:
				month = "10";
				break;
				
			case NOVEMBER:
				month = "11";
				break;
				
			case DECEMBER:
				month = "12";
				break;
				
		}
		
		hour = ldt.getHour();
		minute = ldt.getMinute();
		second = ldt.getSecond();
	}
	
	@Override
	public String toString()
	{
		String date = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second; 
		return date;
	}
}
