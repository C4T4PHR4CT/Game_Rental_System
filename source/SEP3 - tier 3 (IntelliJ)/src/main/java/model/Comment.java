package model;

import java.util.Date;

/**this class serves as a Data Object for Comment*/
public class Comment
{

    /**id of the game which this comment is associated with*/
    public int GameId;
    /**name of the user which this comment is associated with*/
    public String User;
    /**content of the comment*/
    public String Text;
    /**datetime when the comment was posted (nullable)*/
    public Long Time;

    /**initialises all instance variables*/
    public Comment(int gameId, String user, String text, Long time)
    {
        GameId = gameId;
        User = user;
        Text = text;
        Time = time;
    }

    public String toString()
    {
        return GameId + " " + User + " " + Text + " " + new Date(Math.round(Time));
    }
}