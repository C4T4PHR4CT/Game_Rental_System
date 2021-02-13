package org.EvaAndTheSovietHouseholdAppliances.model;

/**this class serves as a Data Object for Search among games*/
public class Search
{

    /**types of games*/
    public String Type;
    /**order by of games [can be: Name; Price; User; Genre; Rating] (nullable, default is Name)*/
    public String OrderBy;
    /**is descending of games*/
    public boolean IsDescending;
    /**is page size of games*/
    public int PageSize;
    /**page number of games*/
    public int Page;
    /**genre of games*/
    public String Genre;
    /**name of games*/
    public String Name;

    /**initialises all instance variables*/
    public Search(String type, String orderBy, boolean isDescending, int pageSize, int page, String genre, String name)
    {
        Type = type;
        OrderBy = orderBy;
        IsDescending = isDescending;
        PageSize = pageSize;
        Page = page;
        Genre = genre;
        Name = name;
    }

    public String toString()
    {
        return Type + " " + OrderBy + " " + IsDescending + " " + PageSize + " " + Page + " " + Genre + " " + Name;
    }
}
