package hust.projectair.enums;

public enum Orientation {

	
	NONE(0), NORTH(1), NORTHEAST(2), EAST(3), SOUTHEAST(4), SOUTH(5), SOUTHWEST(6), WEST(7), NORTHWEST(8);
	
	private int value;
	
	private Orientation(int mValue)
	{
		value = mValue;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public static Orientation parseValueOrientation(int value)
	{
		Orientation orientation = Orientation.NONE;
		
		
		for(Orientation temp : Orientation.values())
		{
			if(temp.getValue() == value)
			{
				return temp;
			}
		}
		return orientation;
	}
	
}
