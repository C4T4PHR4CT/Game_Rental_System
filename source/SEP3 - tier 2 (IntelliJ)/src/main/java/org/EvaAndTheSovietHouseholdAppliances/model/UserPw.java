package org.EvaAndTheSovietHouseholdAppliances.model;

import java.util.Objects;

/**this class serves as a Data Object for User-Password*/
public class UserPw
{

    /**name of the user*/
    public String Name;
    /**password of the user*/
    public String Password;

    /**initialises all instance variables*/
    public UserPw(String name, String password)
    {
        this.Name = name;
        this.Password = password;
    }

    public String toString()
    {
        return Name + " " + Password;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof UserPw))
            return false;
        return (Objects.equals(((UserPw) obj).Name, Name) && Objects.equals(((UserPw) obj).Password, Password));
    }
}