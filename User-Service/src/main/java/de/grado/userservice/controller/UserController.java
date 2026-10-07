package de.grado.userservice.controller;

import de.grado.userservice.dto.LoginResponse;
import de.grado.userservice.dto.UserRequest;
import de.grado.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserRequest request)
    {
        return ResponseEntity.ok(userService.login(request));
    }
}
