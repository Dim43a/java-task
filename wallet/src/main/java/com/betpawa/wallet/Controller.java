package com.betpawa.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wallet")
public class Controller {

    @Autowired
    private  UserRepository userRepository;

    @PostMapping("verif_table")
    public String createUser(@RequestParam String name) {
        userRepository.save(new User(name));
        return userRepository.findByEmail(name) + " Saved";
    }

    @GetMapping("verif_table")
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
