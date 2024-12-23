package com.example.blogsecurty.Controller;

import com.example.blogsecurty.DTO.MyUserDTO;
import com.example.blogsecurty.Model.MyUser;
import com.example.blogsecurty.Service.MyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class MyUserController {

    private final MyUserService myUserService;

    // All
    @PostMapping("/login")
    public ResponseEntity login() {
        return ResponseEntity.status(200).body("Logged in successfully");
    }

    // Admin
    @GetMapping("/all-users")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(myUserService.getAllUsers());
    }

    // Admin
    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(myUserService.getUser(id));
    }

    // All
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid MyUserDTO myUserDTO) {
        myUserService.registerNewUser(myUserDTO);
        return ResponseEntity.status(201).body("User created successfully");
    }

    // All
    @GetMapping("/my-user")
    public ResponseEntity getMyUser(@AuthenticationPrincipal MyUser auth) {
        return ResponseEntity.status(200).body(myUserService.getUser(auth.getId()));
    }

    // User
    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody @Valid MyUserDTO myUserDTO,@AuthenticationPrincipal MyUser auth) {
        myUserService.updateUser(myUserDTO, auth.getId());
        return ResponseEntity.status(200).body("User updated successfully");
    }

    // Admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        myUserService.deleteUser(id);
        return ResponseEntity.status(200).body("User deleted successfully");
    }
}
