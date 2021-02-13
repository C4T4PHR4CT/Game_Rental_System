package org.EvaAndTheSovietHouseholdAppliances.controllers;

import org.EvaAndTheSovietHouseholdAppliances.SEP3_tier2;
import org.EvaAndTheSovietHouseholdAppliances.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

/**responsible for exposing renting related RESTful services*/
@RestController
@RequestMapping("/game_rental")
public class RentController
{
    /**RESTful service for posting a renting*/
    @RequestMapping(method=RequestMethod.POST, value="/postRent")
    public ResponseEntity<?> postRent(@RequestBody Rent rent)
    {
        ArrayList<Rent> temp = SEP3_tier2.Persistence.getRentsByGame(rent.GameId);
        boolean hasConflict = false;
        for (Rent existing : temp)
            if ((existing.TimeFrom <= rent.TimeFrom && existing.TimeUntil >= rent.TimeFrom) || (existing.TimeFrom <= rent.TimeUntil && existing.TimeUntil >= rent.TimeUntil) ||  (rent.TimeFrom <= existing.TimeUntil && rent.TimeUntil >= existing.TimeUntil))
        {
            System.out.println("existing: " + existing);
            hasConflict = true;
        }
        if (hasConflict)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(409));
        int temp1 = SEP3_tier2.Persistence.postRent(rent);
        return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp1));
    }

    /**RESTful service for getting rents*/
    @RequestMapping(method=RequestMethod.GET, value="/getRentsByUser/{user}")
    public ResponseEntity<?> getRentsByUser(@PathVariable String user)
    {
        ArrayList<Rent> temp = SEP3_tier2.Persistence.getRentsByUser(user);
        if (temp == null)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(400));
        else if (temp.size() == 0)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(404));
        else
            return new ResponseEntity<>(temp, SEP3_tier2.getHttpStatus(200));
    }

    /**RESTful service for getting rents*/
    @RequestMapping(method=RequestMethod.GET, value="/getRentsByGame/{gameId}")
    public ResponseEntity<?> getRentsByGame(@PathVariable int gameId)
    {
        ArrayList<Rent> temp = SEP3_tier2.Persistence.getRentsByGame(gameId);
        if (temp == null)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(400));
        else if (temp.size() == 0)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(404));
        else
            return new ResponseEntity<>(temp, SEP3_tier2.getHttpStatus(200));
    }
}
