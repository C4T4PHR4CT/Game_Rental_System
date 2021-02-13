package DAO;

import model.*;
import java.sql.*;
import java.util.ArrayList;

/**responsible for managing DAO classes and keeping the Dependency Inversion principle*/
public class PostgreManager implements PersistenceInterface
{
    /**GameDAO instance*/
    private final GameDAO gameDAO;
    /**UserDAO instance*/
    private final UserDAO userDAO;
    /**RentDAO instance*/
    private final RentDAO rentDAO;
    /**CommentDAO instance*/
    private final CommentDAO commentDAO;
    /**RateDAO instance*/
    private final RateDAO rateDAO;

    /**takes postgre server credentials and initializes all DAO instances*/
    public PostgreManager(String url, String user, String password)
    {
        try
        {
            DriverManager.registerDriver(new org.postgresql.Driver());
        }
        catch (SQLException e)
        {
            throw new RuntimeException("error registering driver", e);
        }

        gameDAO = new GameDAO(url, user, password);
        userDAO = new UserDAO(url, user, password);
        rentDAO = new RentDAO(url, user, password);
        commentDAO = new CommentDAO(url, user, password);
        rateDAO = new RateDAO(url, user, password);
    }

    /**uses gameDAO to create a new game, returns success code (using http status convention)*/
    @Override
    public int postGame(Game game) {
        try {
            gameDAO.postGame(game);
            return 201;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 400;
        }
    }

    /**uses gameDAO to return a list of games based on the query attributes (povUser is used to determine the Game's user rating attribute as it depends on the user whom the request is coming from [can be null])*/
    @Override
    public ArrayList<Game> getGamesByQuery(Query query, String povUser) {
        try {
            return gameDAO.getGamesByQuery(query, povUser);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**uses gameDAO to return a list of games based on the owner of the games (povUser is used to determine the Game's user rating attribute as it depends on the user whom the request is coming from [can be null])*/
    @Override
    public ArrayList<Game> getGamesByOwner(String user, String povUser) {
        try {
            return gameDAO.getGamesByOwner(user, povUser);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**uses gameDAO to return a list of games based on if the user has rented that game (povUser is used to determine the Game's user rating attribute as it depends on the user whom the request is coming from [can be null])*/
    @Override
    public ArrayList<Game> getGamesByRent(String user, String povUser) {
        try {
            return gameDAO.getGamesByRent(user, povUser);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**uses gameDAO to return a  game based on its id (povUser is used to determine the Game's user rating attribute as it depends on the user whom the request is coming from [can be null])*/
    @Override
    public Game getGameById(int gameId, String povUser) {
        try {
            return gameDAO.getGameById(gameId, povUser);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**uses gameDAO to delete a game, returns success code (using http status convention)*/
    @Override
    public int deleteGame(int gameId) {
        try {
            gameDAO.deleteGame(gameId);
            return 200;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 400;
        }
    }

    /**uses gameDAO to update a game, returns success code (using http status convention)*/
    @Override
    public int putGame(Game game) {
        try {
            gameDAO.putGame(game);
            return 200;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 400;
        }
    }

    /**uses commentDAO to create a new comment, returns success code (using http status convention)*/
    @Override
    public int postComment(Comment comment) {
        try {
            commentDAO.postComment(comment);
            return 201;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 400;
        }
    }

    /**uses commentDAO to return a list of comments based on game which the comments are related to*/
    @Override
    public ArrayList<Comment> getComments(int gameId) {
        try {
            return commentDAO.getComments(gameId);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**uses rateDAO to create a new rating, returns success code (using http status convention)*/
    @Override
    public int postRating(Rate rate) {
        try {
            rateDAO.postRating(rate);
            return 201;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 400;
        }
    }

    /**uses userDAO to return a user-password based on the username*/
    @Override
    public UserPw getUserPw(String user) {
        try {
            return userDAO.getUserPw(user);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**uses userDAO to create a new user-password, returns success code (using http status convention)*/
    @Override
    public int postUserPw(UserPw user) {
        try {
            userDAO.postUserPw(user);
            return 201;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 400;
        }
    }

    /**uses userDAO to return a user based on the username*/
    @Override
    public User getUser(String name) {
        try {
            return userDAO.getUser(name);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**uses userDAO to delete a user, returns success code (using http status convention)*/
    @Override
    public int deleteUser(String user) {
        try {
            userDAO.deleteUser(user);
            return 200;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 404;
        }
    }

    /**uses userDAO to update a user-password, returns success code (using http status convention)*/
    @Override
    public int patchUserPassword(UserPw userPw) {
        try {
            userDAO.patchUserPassword(userPw);
            return 200;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 400;
        }
    }

    /**uses userDAO to update a user, returns success code (using http status convention)*/
    @Override
    public int putUser(User user, String oldName) {
        try {
            userDAO.putUser(user, oldName);
            return 200;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 400;
        }
    }

    /**uses rentDAO to create a new rent, returns success code (using http status convention)*/
    @Override
    public int postRent(Rent rent) {
        try {
            rentDAO.postRent(rent);
            return 201;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 400;
        }
    }

    /**uses rentDAO to return a list of rents based on user which the rents are related to*/
    @Override
    public ArrayList<Rent> getRentsByUser(String user) {
        try {
            return rentDAO.getRentsByUser(user);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**uses rentDAO to return a list of rents based on games which the rents are related to*/
    @Override
    public ArrayList<Rent> getRentsByGame(int gameId) {
        try {
            return rentDAO.getRentsByGame(gameId);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}