package hust.projectair.enums;

public enum StatusClient {

	NONE(0), PLAYING(1), WAITING(2),  DESTROY(3), ISSHOOTING(4), DISCONNECT(5);
	
	private int value;
	
	private StatusClient(int mValue)
	{
		value = mValue;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public static StatusClient parseStatusClient(int value)
	{
		for(StatusClient temp : StatusClient.values())
		{
			if(temp.getValue() == value)
			{
				return temp;
			}
		}
		
		return NONE;
	}
}
