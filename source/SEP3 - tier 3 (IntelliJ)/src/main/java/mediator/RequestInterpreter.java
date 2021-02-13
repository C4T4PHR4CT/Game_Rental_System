package mediator;

import DAO.PersistenceInterface;
import com.google.gson.Gson;
import model.*;

/**responsible recognising incoming requests and formatting the correct replies accordingly*/
public class RequestInterpreter
{

    /**PersistenceServer instance*/
    private PersistenceServer Server;
    /**PersistenceInterface instance*/
    private PersistenceInterface Persistence;
    /**Gson instance, for serialising and deserializing*/
    private Gson gson;

    /**initialises all instance variables*/
    public RequestInterpreter(PersistenceInterface persistence)
    {
        Persistence = persistence;
        Server = new PersistenceServer(this);
        gson = new Gson();
    }

    /**responsible recognising incoming requests and formatting the correct replies accordingly*/
    void ReceiveContent(String text)
    {
        System.out.println( "[RECEIVED] " + text);
        String temp[] = text.split("˜");
        String reply;
        switch (temp[1])
        {
            case "postGame":
                reply = String.valueOf(Persistence.postGame(gson.fromJson(temp[2], Game.class)));
                break;
            case "getGamesByQuery":
                reply = gson.toJson(Persistence.getGamesByQuery(gson.fromJson(temp[2], Query.class), temp[3]));
                break;
            case "getGamesByOwner":
                reply = gson.toJson(Persistence.getGamesByOwner(temp[2], temp[3]));
                break;
            case "getGamesByRent":
                reply = gson.toJson(Persistence.getGamesByRent(temp[2], temp[3]));
                break;
            case "getGameById":
                reply = gson.toJson(Persistence.getGameById(Integer.parseInt(temp[2]), temp[3]));
                break;
            case "deleteGame":
                reply = String.valueOf(Persistence.deleteGame(Integer.parseInt(temp[2])));
                break;
            case "putGame":
                reply = String.valueOf(Persistence.putGame(gson.fromJson(temp[2], Game.class)));
                break;
            case "postComment":
                reply = String.valueOf(Persistence.postComment(gson.fromJson(temp[2], Comment.class)));
                break;
            case "getComments":
                reply = gson.toJson(Persistence.getComments(Integer.parseInt(temp[2])));
                break;
            case "postRating":
                reply = String.valueOf(Persistence.postRating(gson.fromJson(temp[2], Rate.class)));
                break;
            case "getUserPw":
                reply = gson.toJson(Persistence.getUserPw(temp[2]));
                break;
            case "postUserPw":
                reply = String.valueOf(Persistence.postUserPw(gson.fromJson(temp[2], UserPw.class)));
                break;
            case "getUser":
                reply = gson.toJson(Persistence.getUser(temp[2]));
                break;
            case "deleteUser":
                reply = String.valueOf(Persistence.deleteUser(temp[2]));
                break;
            case "patchUserPassword":
                reply = String.valueOf(Persistence.patchUserPassword(gson.fromJson(temp[2], UserPw.class)));
                break;
            case "putUser":
                reply = String.valueOf(Persistence.putUser(gson.fromJson(temp[2], User.class),temp[3]));
                break;
            case "postRent":
                reply = String.valueOf(Persistence.postRent(gson.fromJson(temp[2], Rent.class)));
                break;
            case "getRentsByUser":
                reply = gson.toJson(Persistence.getRentsByUser(temp[2]));
                break;
            case "getRentsByGame":
                reply = gson.toJson(Persistence.getRentsByGame(Integer.parseInt(temp[2])));
                break;
            default:
                reply = "500 Internal Server Error";
                break;
        }
        Server.SendContent(temp[0] + "˜" + reply);
    }
}
