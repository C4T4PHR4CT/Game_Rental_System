package DAO;

import model.*;
import java.sql.*;
import java.util.ArrayList;

/**responsible for data manipulation in _comment table*/
public class CommentDAO {

    /**postgre server url*/
    private final String Url;
    /**postgre server user*/
    private final String User;
    /**postgre server password*/
    private final String Password;

    /**takes postgre server credentials and stores them in the instance variables*/
    CommentDAO(String url, String user, String password)
    {
        this.Url = url;
        this.User = user;
        this.Password = password;
    }

    /**opens a new connection to the db, inserts a new record in the _comment table, and closes the connection to the db*/
    void postComment(Comment comment) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO _comment(comment_game, comment_user, comment_text) VALUES (?, ?, ?)");
            statement.setInt(1, comment.GameId);
            statement.setString(2, comment.User);
            statement.setString(3, comment.Text);
            statement.executeUpdate();
        }
    }

    /**opens a new connection to the db, queries the _comment table, and closes the connection to the db*/
    ArrayList<Comment> getComments(int gameId) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT comment_game, comment_user, comment_text, comment_date FROM _comment WHERE comment_game = ?");
            statement.setInt(1, gameId);
            ResultSet result = statement.executeQuery();
            ArrayList<Comment> temp = new ArrayList<>();
            while (result.next())
                temp.add(new Comment(result.getInt("comment_game"), result.getString("comment_user"), result.getString("comment_text"), result.getTimestamp("comment_date").getTime()));
            return temp;
        }
    }
}
