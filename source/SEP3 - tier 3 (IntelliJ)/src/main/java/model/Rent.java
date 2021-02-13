package model;

/**this class serves as a Data Object for Rent*/
public class Rent
{

    /**id of the game which this renting is associated with*/
    public int GameId;
    /**name of the user which this renting is associated with*/
    public String User;
    /**datetime when the game is rented from*/
    public long TimeFrom;
    /**datetime when the game is rented until*/
    public long TimeUntil;

    /**initialises all instance variables*/
    public Rent(int gameId, String user, long timeFrom, long timeUntil)
    {
        GameId = gameId;
        User = user;
        TimeFrom = timeFrom;
        TimeUntil = timeUntil;
    }

    public String toString()
    {
        return GameId + " " + User + " " + TimeFrom + " " + TimeUntil;
    }
}
