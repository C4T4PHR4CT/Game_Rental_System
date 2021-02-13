package org.EvaAndTheSovietHouseholdAppliances.model;

/**this class serves as a Data Object for Rating*/
public class Rate
{

    /**id of the game which this rating is associated with*/
    public int GameId;
    /**name of the user which this rating is associated with*/
    public String User;
    /**rating value*/
    public int Rating;

    /**initialises all instance variables*/
    public Rate(int gameId, String user, int rating)
    {
        GameId = gameId;
        User = user;
        Rating = rating;
    }

    public String toString()
    {
        return GameId + " " + User + " " + Rating;
    }
}
