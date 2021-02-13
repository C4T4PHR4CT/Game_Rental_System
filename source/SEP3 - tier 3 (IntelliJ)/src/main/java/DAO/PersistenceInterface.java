package DAO;

import model.*;

import java.util.ArrayList;

/**responsible for keeping the Dependency Inversion principle*/
public interface PersistenceInterface {
    /**creates a new game, returns success code (using http status convention)*/
    int postGame(Game game);
    /**returns a list of games based on the query attributes (povUser is used to determine the Game's user rating attribute as it depends on the user whom the request is coming from [can be null])*/
    ArrayList<Game> getGamesByQuery(Query query, String povUser);
    /**returns a list of games based on the owner of the games (povUser is used to determine the Game's user rating attribute as it depends on the user whom the request is coming from [can be null])*/
    ArrayList<Game> getGamesByOwner(String user, String povUser);
    /**returns a list of games based on if the user has rented that game (povUser is used to determine the Game's user rating attribute as it depends on the user whom the request is coming from [can be null])*/
    ArrayList<Game> getGamesByRent(String user, String povUser);
    /**returns a  game based on its id (povUser is used to determine the Game's user rating attribute as it depends on the user whom the request is coming from [can be null])*/
    Game getGameById(int gameId, String povUser);
    /**deletes a game, returns success code (using http status convention)*/
    int deleteGame(int gameId);
    /**updates a game, returns success code (using http status convention)*/
    int putGame(Game game);
    /**creates a new comment, returns success code (using http status convention)*/
    int postComment(Comment comment);
    /**returns a list of comments based on game which the comments are related to*/
    ArrayList<Comment> getComments(int gameId);
    /**creates a new rating, returns success code (using http status convention)*/
    int postRating(Rate rate);
    /**returns a user-password based on the username*/
    UserPw getUserPw(String user);
    /**creates a new user-password, returns success code (using http status convention)*/
    int postUserPw(UserPw user);
    /**returns a user based on the username*/
    User getUser(String name);
    /**deletes a user, returns success code (using http status convention)*/
    int deleteUser(String user);
    /**updates a user-password, returns success code (using http status convention)*/
    int patchUserPassword(UserPw userPw);
    /**updates a user, returns success code (using http status convention)*/
    int putUser(User user, String oldName);
    /**creates a new rent, returns success code (using http status convention)*/
    int postRent(Rent rent);
    /**returns a list of rents based on user which the rents are related to*/
    ArrayList<Rent> getRentsByUser(String user);
    /**returns a list of rents based on games which the rents are related to*/
    ArrayList<Rent> getRentsByGame(int gameId);
}