package org.EvaAndTheSovietHouseholdAppliances.controllers;

import org.EvaAndTheSovietHouseholdAppliances.SEP3_tier2;
import org.EvaAndTheSovietHouseholdAppliances.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

/**responsible for exposing comment related RESTful services*/
@RestController
@RequestMapping("/game_rental")
public class CommentController
{
    /**RESTful service for posting a comment*/
    @RequestMapping(method=RequestMethod.POST, value="/postComment")
    public ResponseEntity<?> postComment(@RequestBody Comment comment)
    {
        int temp = SEP3_tier2.Persistence.postComment(comment);
        return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp));
    }

    /**RESTful service for getting a comment*/
    @RequestMapping(method=RequestMethod.GET, value="/getComments/{gameId}")
    public ResponseEntity<?> getComments(@PathVariable int gameId)
    {
        ArrayList<Comment> temp = SEP3_tier2.Persistence.getComments(gameId);
        if (temp == null)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(400));
        else if (temp.size() == 0)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(404));
        else
            return new ResponseEntity<>(temp, SEP3_tier2.getHttpStatus(200));
    }
}
