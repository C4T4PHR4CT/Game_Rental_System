package model;

/**this class serves as a Data Object for Game*/
public class Game
{

    /**id of the game (nullable)*/
    public Integer Id;
    /**name of the game*/
    public String Name;
    /**price per day of the game*/
    public double Price;
    /**details of the game*/
    public String Details;
    /**owner of the game*/
    public String User;
    /**type of the game*/
    public String Type;
    /**genre of the game*/
    public String Genre;
    /**average rating of the game (nullable)*/
    public Double Rating;
    /**rating made by the user [depends on pov] (nullable)*/
    public Integer UserRating;

    /**initialises all instance variables*/
    public Game(Integer id, String name, double price, String details, String user, String type, String genre, Double rating, Integer userRating)
    {
        Id = id;
        Name = name;
        Price = price;
        Details = details;
        User = user;
        Type = type;
        Genre = genre;
        Rating = rating;
        UserRating = userRating;
    }

    public String toString()
    {
        return Id + " " + Name + " " + Price + " " + Details + " " + User + " " + Type + " " + Genre + " " + Rating + " " + UserRating;
    }
}