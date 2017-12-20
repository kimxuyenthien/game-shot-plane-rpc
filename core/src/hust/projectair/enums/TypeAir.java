package hust.projectair.enums;

/**
 * Created by hieutrinh on 10/1/15.
 */
public enum TypeAir {

    NONE(-1), ALLIED(0), ENEMY(1);

    private int value;

    private TypeAir(int mValue)
    {
        value = mValue;
    }

    public int getValue()
    {
        return value;
    }

    public static TypeAir parseTypeAir(int value)
    {
        for(TypeAir type : TypeAir.values())
        {
            if(type.getValue() == value)
            {
                return type;
            }
        }

        return TypeAir.NONE;
    }

}
