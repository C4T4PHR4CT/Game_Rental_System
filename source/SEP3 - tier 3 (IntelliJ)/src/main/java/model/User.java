package model;

/**this class serves as a Data Object for User*/
public class User
{
    /**name of the user*/
    public String Name;
    /**level of the user*/
    public int Level;
    /**phone number of the user (nullable)*/
    public Long Phone;
    /**city (address) of the user*/
    public String City;
    /**zip code (address) of the user (nullable)*/
    public Integer ZipCode;
    /**street (address) of the user*/
    public String Street;
    /**number (address) of the user*/
    public String Number;

    /**initialises all instance variables*/
    public User(String name, int level, Long phone, String city, Integer zipCode, String street, String number)
    {
        Name = name;
        Level = level;
        Phone = phone;
        City = city;
        ZipCode = zipCode;
        Street = street;
        Number = number;
    }

    public String toString()
    {
        return Name + " " + Level + " " + Phone + " " + City + " " + ZipCode + " " + Street + " " + Number;
    }
}