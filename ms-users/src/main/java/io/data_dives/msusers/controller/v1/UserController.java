package io.data_dives.msusers.controller.v1;

import io.data_dives.msusers.dto.user.CreateUserDto;
import io.data_dives.msusers.ex.UserConflictException;
import io.data_dives.msusers.model.User;
import io.data_dives.msusers.service.v1.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "user-controller")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/user")
    @Operation(summary = "Creates a user in the database", method = "POST")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "User created sucessfully"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<String > createUser(@RequestBody CreateUserDto dto){
        try {
            service.createUser(dto);
            return ResponseEntity.status(201).body("User created sucessfully!");
        }catch (UserConflictException e){
            return ResponseEntity.status(409).body("User already exists");
        }
    }
}
