package com.example.budgettracker.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import com.example.budgettracker.dto.request.RegistrationRequest;
import com.example.budgettracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.core.Authentication;

import java.util.Map;

@Tag(name = "User", description = "Operations related to user accounts")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with a unique username and email."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or duplicate user")
    })
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    @Operation(
            summary = "Get current user info",
            description = "Returns the username of the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Current user info returned successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(
            @Parameter(hidden = true) Authentication authentication
    ) {
        return ResponseEntity.ok(Map.of("username", authentication.getName()));
    }
}
