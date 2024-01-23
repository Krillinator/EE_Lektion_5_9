package com.krillinator.lektion_5.controllers;

import com.krillinator.lektion_5.config.AppPasswordConfig;
import com.krillinator.lektion_5.models.user.UserEntity;
import com.krillinator.lektion_5.models.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final AppPasswordConfig appPasswordConfig; // Bcrypt

    @Autowired
    public UserController(UserRepository userRepository, AppPasswordConfig appPasswordConfig) {
        this.userRepository = userRepository;
        this.appPasswordConfig = appPasswordConfig;
    }

    // TODO - Discuss Model model VS UserEntity userEntity
    @GetMapping("/register")
    public String registerUserPage(
       UserEntity userEntity   // Enables Error Messages
    ) {


        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid UserEntity userEntity,   // Enables Error Messages
            BindingResult result,           // Ties the object with result
            Model model                     // Thymeleaf
    ) {

        // TODO - Talk about this
        model.addAttribute("userEntity", userEntity);

        // Check FOR @Valid Errors
        if (result.hasErrors()) {
            return "register";
        }

        // TODO - Optionally: handle duplicate entries (@Unique PREFERABLY within ENTITY)
        userEntity.setPassword(
                appPasswordConfig.bCryptPasswordEncoder().encode(userEntity.getPassword())
        );

        userRepository.save(userEntity);

        return "redirect:/login";
    }

}
