package org.EvaAndTheSovietHouseholdAppliances.mediator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.EvaAndTheSovietHouseholdAppliances.model.*;
import java.util.ArrayList;
import java.util.HashMap;

/**responsible for formatting outgoing requests and recognising the replies accordingly*/
public class PersistenceFormatter implements PersistenceInterface
{

    /**PersistenceConnection instance*/
    private PersistenceConnection Connection;
    /**stores the Threads which are created by SpringBoot for each RESTful request*/
    ArrayList<Thread> Requests;
    /**stores the incoming replies and couples them with the Thread name which send the request in the first place*/
    HashMap<String, String> Replies;
    /**Gson instance, for serialising and deserializing*/
    private Gson gson;

    /**instantiates all instance variables*/
    public PersistenceFormatter(String host)
    {
        Connection = new PersistenceConnection(this, host);
        Requests = new ArrayList<>();
        Replies = new HashMap<>();
        gson = new Gson();
    }

    /**stores the incoming reply in the Replies and wakes up the appropriate Thread which send the request in the first place*/
    void ReceiveContent(String text)
    {
        String[] temp = text.split("˜");
        Replies.put(temp[0], temp[1]);
        Thread issued = null;
        for (Thread request : Requests)
            if (request.getName().equals(temp[0]))
            {
                issued = request;
                break;
            }
        if (issued != null)
            synchronized (issued)
            {
                issued.notify();
            }
        else
        {
            new RuntimeException("Request thread not found").printStackTrace();
            Replies.remove(temp[0]);
        }
    }

    /**formats a request to "postGame" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int postGame(Game game)
    {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "postGame" + "˜";
        content += gson.toJson(game);
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "getGamesByQuery" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public ArrayList<Game> getGamesByQuery(Query query, String povUser) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "getGamesByQuery" + "˜";
        content += gson.toJson(query) + "˜";
        content += povUser;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        ArrayList<Game> ret = gson.fromJson(Replies.get(current.getName()), new TypeToken<ArrayList<Game>>(){}.getType());
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "getGamesByOwner" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public ArrayList<Game> getGamesByOwner(String user, String povUser) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "getGamesByOwner" + "˜";
        content += user + "˜";
        content += povUser;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        ArrayList<Game> ret = gson.fromJson(Replies.get(current.getName()), new TypeToken<ArrayList<Game>>(){}.getType());
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "getGamesByRent" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public ArrayList<Game> getGamesByRent(String user, String povUser) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "getGamesByRent" + "˜";
        content += user + "˜";
        content += povUser;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        ArrayList<Game> ret = gson.fromJson(Replies.get(current.getName()), new TypeToken<ArrayList<Game>>(){}.getType());
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "getGameById" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public Game getGameById(int gameId, String povUser) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "getGameById" + "˜";
        content += gameId + "˜";
        content += povUser;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        Game ret = gson.fromJson(Replies.get(current.getName()), Game.class);
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "deleteGame" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int deleteGame(int gameId) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "deleteGame" + "˜";
        content += gameId;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "putGame" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int putGame(Game game) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "putGame" + "˜";
        content += gson.toJson(game);
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "postComment" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int postComment(Comment comment) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "postComment" + "˜";
        content += gson.toJson(comment);
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "getComments" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public ArrayList<Comment> getComments(int gameId) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "getComments" + "˜";
        content += gameId;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        ArrayList<Comment> ret = gson.fromJson(Replies.get(current.getName()), new TypeToken<ArrayList<Comment>>(){}.getType());
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "postRating" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int postRating(Rate rate) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "postRating" + "˜";
        content += gson.toJson(rate);
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "getUserPw" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public UserPw getUserPw(String user) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "getUserPw" + "˜";
        content += user;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        UserPw ret = gson.fromJson(Replies.get(current.getName()), UserPw.class);
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "postUserPw" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int postUserPw(UserPw user) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "postUserPw" + "˜";
        content += gson.toJson(user);
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "getUser" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public User getUser(String name) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "getUser" + "˜";
        content += name;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        User ret = gson.fromJson(Replies.get(current.getName()), User.class);
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "deleteUser" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int deleteUser(String user) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "deleteUser" + "˜";
        content += user;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "patchUserPassword" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int patchUserPassword(UserPw userPw) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "patchUserPassword" + "˜";
        content += gson.toJson(userPw);
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "putUser" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int putUser(User user, String oldName) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "putUser" + "˜";
        content += gson.toJson(user) + "˜";
        content += oldName;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "postRent" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public int postRent(Rent rent) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "postRent" + "˜";
        content += gson.toJson(rent);
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return 500;
        }
        int ret = Integer.parseInt(Replies.get(current.getName()));
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "getRentsByUser" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public ArrayList<Rent> getRentsByUser(String user) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "getRentsByUser" + "˜";
        content += user;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        ArrayList<Rent> ret = gson.fromJson(Replies.get(current.getName()), new TypeToken<ArrayList<Rent>>(){}.getType());
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }

    /**formats a request to "getRentsByGame" then stores the current Thread in the Requests, puts the current Thread on wait, and when notified gets the appropriate reply, removes the current Thread and reply from Requests and Replies, and returns the recognised reply*/
    @Override
    public ArrayList<Rent> getRentsByGame(int gameId) {
        String content = "";
        content += Thread.currentThread().getName() + "˜";
        content += "getRentsByGame" + "˜";
        content += gameId;
        Connection.SendContent(content);
        Thread current = Thread.currentThread();
        Requests.add(current);
        try
        {
            synchronized (current)
            {
                current.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        ArrayList<Rent> ret = gson.fromJson(Replies.get(current.getName()), new TypeToken<ArrayList<Rent>>(){}.getType());
        Replies.remove(current.getName());
        Requests.remove(current);
        return ret;
    }
}
