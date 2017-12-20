package hust.projectair.enums;

/**
 * Created by hieutrinh on 10/1/15.
 */
public enum StatusAir {

    LEFT(0), RIGHT(1), STAND(2), DESTROY(3);

    private int value;

    private StatusAir(int mValue)
    {
        value = mValue;
    }

    public int getValue()
    {
        return value;
    }
}
