package Transaction.project.controller;

import Transaction.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Transaction.project.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.status(201).body("User Created");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable String id){
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.status(404).body("User not Found");
        }
        return ResponseEntity.ok(user);
    }

}
