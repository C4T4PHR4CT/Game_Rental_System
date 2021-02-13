package org.EvaAndTheSovietHouseholdAppliances.controllers;

import org.EvaAndTheSovietHouseholdAppliances.SEP3_tier2;
import org.EvaAndTheSovietHouseholdAppliances.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**responsible for exposing user related RESTful services*/
@RestController
@RequestMapping("/game_rental")
public class UserController
{
    /**RESTful service for validating a user-password*/
    @RequestMapping(method=RequestMethod.POST, value="/validateUser")
    public ResponseEntity<?> validateUser(@RequestBody UserPw validate)
    {
        UserPw temp = SEP3_tier2.Persistence.getUserPw(validate.Name);
        if (temp == null)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(404));
        else if (temp.Password.equals(validate.Password))
        {
            User user = SEP3_tier2.Persistence.getUser(validate.Name);
            if (user == null)
                return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(500));
            return new ResponseEntity<>(user, SEP3_tier2.getHttpStatus(200));
        }
        else
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(403));
    }

    /**RESTful service for posting (registering) a user(-password)*/
    @RequestMapping(method=RequestMethod.POST, value="/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserPw register)
    {
        int temp = SEP3_tier2.Persistence.postUserPw(register);
        if (temp == 201)
        {
            User user = SEP3_tier2.Persistence.getUser(register.Name);
            if (user == null)
                return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(500));
            return new ResponseEntity<>(user, SEP3_tier2.getHttpStatus(201));
        }
        else
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp));
    }

    /**RESTful service for getting a user*/
    @RequestMapping(method=RequestMethod.GET, value="/getUser/{user}")
    public ResponseEntity<?> getUser(@PathVariable String user)
    {
        User temp = SEP3_tier2.Persistence.getUser(user);
        if (temp == null)
            return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(404));
        else
            return new ResponseEntity<>(temp, SEP3_tier2.getHttpStatus(200));
    }

    /**RESTful service for deleting a user*/
    @RequestMapping(method=RequestMethod.DELETE, value="/deleteUser/{user}")
    public ResponseEntity<?> deleteUser(@PathVariable String user)
    {
        int temp = SEP3_tier2.Persistence.deleteUser(user);
        return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp));
    }

    /**RESTful service for patching a user-password*/
    @RequestMapping(method=RequestMethod.PATCH, value="/patchUserPassword")
    public ResponseEntity<?> patchUserPassword(@RequestBody UserPw userPw)
    {
        int temp = SEP3_tier2.Persistence.patchUserPassword(userPw);
        return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp));
    }

    /**RESTful service for putting a user*/
    @RequestMapping(method=RequestMethod.PUT, value="/putUser/{oldName}")
    public ResponseEntity<?> putUser(@RequestBody User user, @PathVariable String oldName)
    {
        System.out.println(user);
        int temp = SEP3_tier2.Persistence.putUser(user, oldName);
        return new ResponseEntity<>(null, SEP3_tier2.getHttpStatus(temp));
    }
}