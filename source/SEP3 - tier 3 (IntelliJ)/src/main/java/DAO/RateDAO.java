package DAO;

import model.*;
import java.sql.*;

/**responsible for data manipulation in _game_rate table*/
public class RateDAO {

    /**postgre server url*/
    private final String Url;
    /**postgre server user*/
    private final String User;
    /**postgre server password*/
    private final String Password;

    /**takes postgre server credentials and stores them in the instance variables*/
    RateDAO(String url, String user, String password)
    {
        this.Url = url;
        this.User = user;
        this.Password = password;
    }

    /**opens a new connection to the db, inserts a new record in the _game_rate table, and closes the connection to the db*/
    void postRating(Rate rate) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO _game_rate(grate_game, grate_user, grate) VALUES (?, ?, ?)");
            statement.setInt(1, rate.GameId);
            statement.setString(2, rate.User);
            statement.setInt(3, rate.Rating);
            statement.executeUpdate();
        }
    }
}
