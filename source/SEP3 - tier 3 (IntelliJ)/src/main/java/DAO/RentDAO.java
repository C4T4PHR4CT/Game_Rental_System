package DAO;

import model.*;
import java.sql.*;
import java.util.ArrayList;

/**responsible for data manipulation in _rent table*/
public class RentDAO {

    /**postgre server url*/
    private final String Url;
    /**postgre server user*/
    private final String User;
    /**postgre server password*/
    private final String Password;

    /**takes postgre server credentials and stores them in the instance variables*/
    RentDAO(String url, String user, String password)
    {
        this.Url = url;
        this.User = user;
        this.Password = password;
    }

    /**opens a new connection to the db, inserts a new record in the _rent table, and closes the connection to the db*/
    void postRent(Rent rent) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO _rent(rent_game, rent_user, rent_from, rent_to) VALUES (?, ?, ?, ?)");
            statement.setInt(1, rent.GameId);
            statement.setString(2, rent.User);
            statement.setTimestamp(3, new Timestamp(rent.TimeFrom));
            statement.setTimestamp(4, new Timestamp(rent.TimeUntil));
            statement.executeUpdate();
        }
    }

    /**opens a new connection to the db, queries the _rent table, and closes the connection to the db*/
    ArrayList<Rent> getRentsByUser(String user) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM _rent WHERE rent_user = ?");
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();
            ArrayList<Rent> temp = new ArrayList<>();
            while (result.next())
                temp.add(new Rent(result.getInt("rent_game"), result.getString("rent_user"), result.getTimestamp("rent_from").getTime(), result.getTimestamp("rent_to").getTime()));
            return temp;
        }
    }

    /**opens a new connection to the db, queries the _rent table, and closes the connection to the db*/
    ArrayList<Rent> getRentsByGame(int gameId) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM _rent WHERE rent_game = ?");
            statement.setInt(1, gameId);
            ResultSet result = statement.executeQuery();
            ArrayList<Rent> temp = new ArrayList<>();
            while (result.next())
                temp.add(new Rent(result.getInt("rent_game"), result.getString("rent_user"), result.getTimestamp("rent_from").getTime(), result.getTimestamp("rent_to").getTime()));
            return temp;
        }
    }
}
