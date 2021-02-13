package DAO;

import model.*;
import java.sql.*;
import java.util.ArrayList;

/**responsible for data manipulation in _game table*/
public class GameDAO {

    /**postgre server url*/
    private final String Url;
    /**postgre server user*/
    private final String User;
    /**postgre server password*/
    private final String Password;

    /**takes postgre server credentials and stores them in the instance variables*/
    GameDAO(String url, String user, String password)
    {
        this.Url = url;
        this.User = user;
        this.Password = password;
    }

    /**opens a new connection to the db, inserts a new record in the _game table, and closes the connection to the db*/
    void postGame(Game game) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password))
        {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO _game(game_name, game_price, game_details, game_user, game_type, game_genre) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, game.Name);
            statement.setDouble(2, game.Price);
            statement.setString(3, game.Details);
            statement.setString(4, game.User);
            statement.setString(5, game.Type);
            statement.setString(6, game.Genre);
            statement.executeUpdate();
        }
    }

    /**opens a new connection to the db, queries the _game table, and closes the connection to the db*/
    ArrayList<Game> getGamesByQuery(Query query, String povUser) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement1, statement2;
            ResultSet result1, result2;
            String tempOrder = query.getOrderBy();
            if (tempOrder.equals("rate"))
                tempOrder = "game_name";
            if (query.IsDescending)
                statement1 = connection.prepareStatement("SELECT game_id, game_name, game_price, game_details, game_user, game_type, game_genre FROM _game WHERE game_type = ? ORDER BY \"" + tempOrder + "\" DESC;");
            else
                statement1 = connection.prepareStatement("SELECT game_id, game_name, game_price, game_details, game_user, game_type, game_genre FROM _game WHERE game_type = ? ORDER BY \"" + tempOrder + "\";");
            statement1.setString(1, query.Type);
            result1 = statement1.executeQuery();
            ArrayList<Game> temp = new ArrayList<>();
            while (result1.next()) {
                Integer userRating = null;
                try {
                    statement2 = connection.prepareStatement("SELECT grate FROM _game_rate WHERE grate_game = ? AND grate_user = ?");
                    statement2.setInt(1, result1.getInt("game_id"));
                    statement2.setString(2, povUser);
                    result2 = statement2.executeQuery();
                    result2.next();
                    userRating = result2.getInt("grate");
                } catch (SQLException ignored) {
                }
                double rating = 0;
                try {
                    statement2 = connection.prepareStatement("SELECT grate FROM _game_rate WHERE grate_game = ?");
                    statement2.setInt(1, result1.getInt("game_id"));
                    result2 = statement2.executeQuery();
                    int sum = 0;
                    int cnt = 0;
                    while (result2.next()) {
                        sum += result2.getInt("grate");
                        cnt++;
                    }
                    if (cnt != 0)
                        rating = (float) sum / (float) cnt;
                } catch (SQLException ignored) {
                }
                temp.add(new Game(result1.getInt("game_id"), result1.getString("game_name"), result1.getDouble("game_price"), result1.getString("game_details"), result1.getString("game_user"), result1.getString("game_type"), result1.getString("game_genre"), rating, userRating));
            }
            boolean switched = true;
            Game util;
            if (query.getOrderBy().equals("rate"))
                while (switched) {
                    switched = false;
                    for (int i = 0; i < temp.size() - 1; i++)
                        if ((temp.get(i).Rating > temp.get(i + 1).Rating && !query.IsDescending) || (temp.get(i).Rating < temp.get(i + 1).Rating && query.IsDescending)) {
                            util = temp.get(i);
                            temp.set(i, temp.get(i + 1));
                            temp.set(i + 1, util);
                            switched = true;
                        }
                }
            return temp;
        }
    }

    /**opens a new connection to the db, queries the _game table, and closes the connection to the db*/
    ArrayList<Game> getGamesByOwner(String user, String povUser) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement1, statement2;
            ResultSet result1, result2;
            statement1 = connection.prepareStatement("SELECT game_id, game_name, game_price, game_details, game_user, game_type, game_genre FROM _game WHERE game_user = ?");
            statement1.setString(1, user);
            result1 = statement1.executeQuery();
            ArrayList<Game> temp = new ArrayList<>();
            while (result1.next()) {
                Integer userRating = null;
                try {
                    statement2 = connection.prepareStatement("SELECT grate FROM _game_rate WHERE grate_game = ? AND grate_user = ?");
                    statement2.setInt(1, result1.getInt("game_id"));
                    statement2.setString(2, povUser);
                    result2 = statement2.executeQuery();
                    result2.next();
                    userRating = result2.getInt("grate");
                } catch (SQLException ignored) {
                }
                double rating = 0;
                try {
                    statement2 = connection.prepareStatement("SELECT grate FROM _game_rate WHERE grate_game = ?");
                    statement2.setInt(1, result1.getInt("game_id"));
                    result2 = statement2.executeQuery();
                    int sum = 0;
                    int cnt = 0;
                    while (result2.next()) {
                        sum += result2.getInt("grate");
                        cnt++;
                    }
                    if (cnt != 0)
                        rating = (float) sum / (float) cnt;
                } catch (SQLException ignored) {
                }
                temp.add(new Game(result1.getInt("game_id"), result1.getString("game_name"), result1.getDouble("game_price"), result1.getString("game_details"), result1.getString("game_user"), result1.getString("game_type"), result1.getString("game_genre"), rating, userRating));
            }
            return temp;
        }
    }

    /**opens a new connection to the db, queries the _game table, and closes the connection to the db*/
    ArrayList<Game> getGamesByRent(String user, String povUser) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement1, statement2;
            ResultSet result1, result2;
            statement1 = connection.prepareStatement("SELECT _game.game_id, _game.game_name, _game.game_price, _game.game_details, _game.game_user, _game.game_type, _game.game_genre FROM _game JOIN _rent ON _game.game.id = _rent.rent_game WHERE _rent.rent_user = ?");
            statement1.setString(1, user);
            result1 = statement1.executeQuery();
            ArrayList<Game> temp = new ArrayList<>();
            while (result1.next()) {
                Integer userRating = null;
                try {
                    statement2 = connection.prepareStatement("SELECT grate FROM _game_rate WHERE grate_game = ? AND grate_user = ?");
                    statement2.setInt(1, result1.getInt("game_id"));
                    statement2.setString(2, povUser);
                    result2 = statement2.executeQuery();
                    result2.next();
                    userRating = result2.getInt("grate");
                } catch (SQLException ignored) {
                }
                double rating = 0;
                try {
                    statement2 = connection.prepareStatement("SELECT grate FROM _game_rate WHERE grate_game = ?");
                    statement2.setInt(1, result1.getInt("game_id"));
                    result2 = statement2.executeQuery();
                    int sum = 0;
                    int cnt = 0;
                    while (result2.next()) {
                        sum += result2.getInt("grate");
                        cnt++;
                    }
                    if (cnt != 0)
                        rating = (float) sum / (float) cnt;
                } catch (SQLException ignored) {
                }
                temp.add(new Game(result1.getInt("game_id"), result1.getString("game_name"), result1.getDouble("game_price"), result1.getString("game_details"), result1.getString("game_user"), result1.getString("game_type"), result1.getString("game_genre"), rating, userRating));
            }
            return temp;
        }
    }

    /**opens a new connection to the db, queries the _game table, and closes the connection to the db*/
    Game getGameById(int gameId, String povUser) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement1, statement2;
            ResultSet result1, result2;
            statement1 = connection.prepareStatement("SELECT game_id, game_name, game_price, game_details, game_user, game_type, game_genre FROM _game WHERE game_id = ?");
            statement1.setInt(1, gameId);
            result1 = statement1.executeQuery();
            result1.next();
            Integer userRating = null;
            try {
                statement2 = connection.prepareStatement("SELECT grate FROM _game_rate WHERE grate_game = ? AND grate_user = ?");
                statement2.setInt(1, result1.getInt("game_id"));
                statement2.setString(2, povUser);
                result2 = statement2.executeQuery();
                result2.next();
                userRating = result2.getInt("grate");
            } catch (SQLException ignored) {
            }
            double rating = 0;
            try {
                statement2 = connection.prepareStatement("SELECT grate FROM _game_rate WHERE grate_game = ?");
                statement2.setInt(1, result1.getInt("game_id"));
                result2 = statement2.executeQuery();
                int sum = 0;
                int cnt = 0;
                while (result2.next()) {
                    sum += result2.getInt("grate");
                    cnt++;
                }
                if (cnt != 0)
                    rating = (float) sum / (float) cnt;
            } catch (SQLException ignored) {
            }
            return new Game(result1.getInt("game_id"), result1.getString("game_name"), result1.getDouble("game_price"), result1.getString("game_details"), result1.getString("game_user"), result1.getString("game_type"), result1.getString("game_genre"), rating, userRating);
        }
    }

    /**opens a new connection to the db, deletes a record from _game table, and closes the connection to the db*/
    void deleteGame(int gameId) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT game_id FROM _game WHERE game_id = ?");
            statement.setInt(1, gameId);
            ResultSet result = statement.executeQuery();
            result.next();
            result.getInt("game_id");
            statement = connection.prepareStatement("DELETE FROM _game WHERE game_id = ?");
            statement.setInt(1, gameId);
            statement.executeUpdate();
        }
    }

    /**opens a new connection to the db, updates a record in _game table, and closes the connection to the db*/
    void putGame(Game game) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT game_id FROM _game WHERE game_id = ?");
            statement.setInt(1, game.Id);
            ResultSet result = statement.executeQuery();
            result.next();
            result.getInt("game_id");
            statement = connection.prepareStatement("UPDATE _game SET game_name = ?, game_price = ?, game_details = ?, game_type = ?, game_genre = ? WHERE game_id = ?");
            statement.setString(1, game.Name);
            statement.setDouble(2, game.Price);
            statement.setString(3, game.Details);
            statement.setString(4, game.Type);
            statement.setString(5, game.Genre);
            statement.setInt(6, game.Id);
            statement.executeUpdate();
        }
    }
}
