package org.EvaAndTheSovietHouseholdAppliances.controllers;

import org.EvaAndTheSovietHouseholdAppliances.SEP3_tier2;
import org.EvaAndTheSovietHouseholdAppliances.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

/**responsible for exposing game related RESTful services*/
@RestController
@RequestMapping("/game_rental")
public class GameController
{
    /**RESTful service for posting a game*/
    @RequestMapping(method=RequestMethod.POST, value="/postGame")
    public ResponseEntity<?> postGame(@RequestBody Game game)
    {
        int temp = SEP3_tier2.Persistence.postGame(game);
        return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp));
    }

    /**RESTful service for getting games*/
    @RequestMapping(method=RequestMethod.POST, value="/getGamesBySearch/{povUser}")
    public ResponseEntity<?> getGamesBySearch(@RequestBody Search search, @PathVariable String povUser)
    {
        ArrayList<Game> temp = SEP3_tier2.Persistence.getGamesByQuery(new Query(search.Type, search.OrderBy, search.IsDescending), povUser);
        if (temp == null)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(400));
        else
        {
            for (int i = 0; i < temp.size(); i++)
            {
                if (search.Genre != null)
                    if (!search.Genre.equals(""))
                        if (!temp.get(i).Genre.contains(search.Genre))
                        {
                            temp.remove(i);
                            i--;
                        }
                if (search.Name != null)
                    if (!search.Name.equals(""))
                        if (!temp.get(i).Name.contains(search.Name))
                        {
                            temp.remove(i);
                            i--;
                        }
            }
            //Pagination disabled => unit tests adjusted accordingly, attribute from model Search did not got removed
            if (temp.size() == 0)
                return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(404));
            return new ResponseEntity<>(temp, SEP3_tier2.getHttpStatus(200));
        }
    }

    /**RESTful service for getting games*/
    @RequestMapping(method=RequestMethod.GET, value="/getGamesByOwner/{user}/{povUser}")
    public ResponseEntity<?> getGamesByOwner(@PathVariable String user, @PathVariable String povUser)
    {
        ArrayList<Game> temp = SEP3_tier2.Persistence.getGamesByOwner(user, povUser);
        if (temp == null)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(400));
        else if (temp.size() == 0)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(404));
        else
            return new ResponseEntity<>(temp, SEP3_tier2.getHttpStatus(200));
    }

    /**RESTful service for getting games*/
    @RequestMapping(method=RequestMethod.GET, value="/getGamesByRent/{user}/{povUser}")
    public ResponseEntity<?> getGamesByRent(@PathVariable String user, @PathVariable String povUser)
    {
        ArrayList<Game> temp = SEP3_tier2.Persistence.getGamesByRent(user, povUser);
        if (temp == null)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(400));
        else
            return new ResponseEntity<>(temp, SEP3_tier2.getHttpStatus(200));
    }

    /**RESTful service for getting a game*/
    @RequestMapping(method=RequestMethod.GET, value="/getGameById/{gameId}/{povUser}")
    public ResponseEntity<?> getGameById(@PathVariable int gameId, @PathVariable String povUser)
    {
        Game temp = SEP3_tier2.Persistence.getGameById(gameId, povUser);
        if (temp == null)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(400));
        else
            return new ResponseEntity<>(temp, SEP3_tier2.getHttpStatus(200));
    }

    /**RESTful service for deleting a game*/
    @RequestMapping(method=RequestMethod.DELETE, value="/deleteGame/{gameId}")
    public ResponseEntity<?> deleteGame(@PathVariable int gameId)
    {
        int temp = SEP3_tier2.Persistence.deleteGame(gameId);
        return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp));
    }

    /**RESTful service for putting a game*/
    @RequestMapping(method=RequestMethod.PUT, value="/putGame")
    public ResponseEntity<?> putGame(@RequestBody Game game)
    {
        int temp = SEP3_tier2.Persistence.putGame(game);
        return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp));
    }
}