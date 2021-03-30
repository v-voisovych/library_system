package com.voisovych.library.controller;

import com.voisovych.library.model.User;
import com.voisovych.library.myexception.MyException;
import com.voisovych.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public List<User> allUsers() throws MyException {
        return userService.allUsers();
    }

    @GetMapping("/findUser")
    public User findUser(@RequestParam String userNumber) throws MyException {
        return userService.findUser(userNumber);
    }

    @PutMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user) throws MyException {
        userService.addUser(user);
        return ResponseEntity.ok("User has created Successfully!!!");
    }

    @PostMapping("/editUser")
    public ResponseEntity<String> editUser(@RequestBody User user) throws MyException {
        userService.editUser(user);
        return ResponseEntity.ok("User has edited Successfully!!!");
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String userNumber) throws MyException {
        userService.deleteUser(userNumber);
        return ResponseEntity.ok("User has deleted Successfully!!!");
    }


}
