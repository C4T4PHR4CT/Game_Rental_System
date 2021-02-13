package org.EvaAndTheSovietHouseholdAppliances.controllers;

import org.EvaAndTheSovietHouseholdAppliances.SEP3_tier2;
import org.EvaAndTheSovietHouseholdAppliances.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**responsible for exposing rate related RESTful services*/
@RestController
@RequestMapping("/game_rental")
public class RateController
{
    /**RESTful service for posting a rating*/
    @RequestMapping(method=RequestMethod.POST, value="/postRating")
    public ResponseEntity<?> postRating(@RequestBody Rate rate)
    {
        int temp = SEP3_tier2.Persistence.postRating(rate);
        return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp));
    }
}
