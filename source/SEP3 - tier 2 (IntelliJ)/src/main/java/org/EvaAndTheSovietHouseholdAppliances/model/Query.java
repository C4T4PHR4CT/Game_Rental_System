package org.EvaAndTheSovietHouseholdAppliances.model;

/**this class serves as a Data Object for Query among games*/
public class Query
{

    /**types of games*/
    public String Type;
    /**order by of games [can be: Name; Price; User; Genre; Rating] (nullable, default is Name)*/
    public String OrderBy;
    /**is descending of games*/
    public boolean IsDescending;

    /**initialises all instance variables*/
    public Query(String type, String orderBy, boolean isDescending)
    {
        Type = type;
        OrderBy = orderBy;
        IsDescending = isDescending;
    }

    public String toString()
    {
        return Type + " " + OrderBy + " " + IsDescending;
    }
}
