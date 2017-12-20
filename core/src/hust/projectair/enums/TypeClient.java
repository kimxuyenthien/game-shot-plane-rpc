package hust.projectair.enums;


public enum TypeClient {

	
	NONE(-1), A(0), B(1);
	
	private int value;
	private TypeClient(int mValue)
	{
		value = mValue;
	}
	public int getValue()
	{
		return value;
	}
	public static TypeClient parseType(int value)
	{
		TypeClient type = TypeClient.NONE;
		
		for(TypeClient temp : TypeClient.values())
		{
			if(temp.getValue() == value)
			{
				return temp;
			}
		}
		return type;
	}
}
