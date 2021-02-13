package DAO;

import model.*;
import java.sql.*;

/**responsible for data manipulation in _user table*/
public class UserDAO {

    /**postgre server url*/
    private final String Url;
    /**postgre server user*/
    private final String User;
    /**postgre server password*/
    private final String Password;

    /**takes postgre server credentials and stores them in the instance variables*/
    UserDAO(String url, String user, String password)
    {
        this.Url = url;
        this.User = user;
        this.Password = password;
    }

    /**opens a new connection to the db, queries the _user table, and closes the connection to the db*/
    UserPw getUserPw(String user) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT user_password FROM _user WHERE user_name = ?");
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();
            result.next();
            return new UserPw(user, result.getString("user_password"));
        }
    }

    /**opens a new connection to the db, inserts a new record in the _user table, and closes the connection to the db*/
    void postUserPw(UserPw user) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO _user(user_name, user_password, user_level) VALUES (?, ?, ?)");
            statement.setString(1, user.Name);
            statement.setString(2, user.Password);
            statement.setInt(3, 1);
            statement.executeUpdate();
        }
    }

    /**opens a new connection to the db, queries the _user table, and closes the connection to the db*/
    User getUser(String name) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT user_name, user_level, user_phone, user_address_city, user_address_zip, user_address_street, user_address_number FROM _user WHERE user_name = ?");
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            result.next();
            User temp = new User(result.getString("user_name"), result.getInt("user_level"), result.getLong("user_phone"), result.getString("user_address_city"), result.getInt("user_address_zip"), result.getString("user_address_street"), result.getString("user_address_number"));
            result.getLong("user_phone");
            if (result.wasNull())
                temp.Phone = null;
            result.getInt("user_address_zip");
            if (result.wasNull())
                temp.ZipCode = null;
            return temp;
        }
    }

    /**opens a new connection to the db, deletes a record from _user table, and closes the connection to the db*/
    void deleteUser(String user) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT user_name FROM _user WHERE user_name = ?");
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();
            result.next();
            result.getString("user_name");
            statement = connection.prepareStatement("DELETE FROM _user WHERE user_name = ?");
            statement.setString(1, user);
            statement.executeUpdate();
        }
    }

    /**opens a new connection to the db, updates a record in _user table, and closes the connection to the db*/
    void patchUserPassword(UserPw userPw) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT user_name FROM _user WHERE user_name = ?");
            statement.setString(1, userPw.Name);
            ResultSet result = statement.executeQuery();
            result.next();
            result.getString("user_name");
            statement = connection.prepareStatement("UPDATE _user SET user_password = ? WHERE user_name = ?");
            statement.setString(1, userPw.Password);
            statement.setString(2, userPw.Name);
            statement.executeUpdate();
        }
    }

    /**opens a new connection to the db, updates a record in _user table, and closes the connection to the db*/
    void putUser(User user, String oldName) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT user_name FROM _user WHERE user_name = ?");
            statement.setString(1, oldName);
            ResultSet result = statement.executeQuery();
            result.next();
            result.getString("user_name");
            statement = connection.prepareStatement("UPDATE _user SET user_name = ?, user_phone = ?, user_address_city = ?, user_address_zip = ?, user_address_street = ?, user_address_number = ? WHERE user_name = ?");
            statement.setString(1, user.Name);
            if (user.Phone == null)
                statement.setNull(2, 0);
            else
                statement.setLong(2, user.Phone);
            statement.setString(3, user.City);
            if (user.ZipCode == null)
                statement.setNull(4, 0);
            else
                statement.setInt(4, user.ZipCode);
            statement.setString(5, user.Street);
            statement.setString(6, user.Number);
            statement.setString(7, oldName);
            statement.executeUpdate();
        }
    }
}