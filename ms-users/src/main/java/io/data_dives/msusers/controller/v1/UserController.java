package io.data_dives.msusers.controller.v1;

import io.data_dives.msusers.dto.user.CreateUserDto;
import io.data_dives.msusers.dto.user.SelectUserDto;
import io.data_dives.msusers.ex.user.UserConflictException;
import io.data_dives.msusers.ex.user.UserNotFoundException;
import io.data_dives.msusers.service.v1.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/cpf/{cpf}")
    @Operation(summary = "Select user by cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found sucessfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<SelectUserDto> selectUserByCpf(@PathVariable String cpf){
        try {
            return ResponseEntity.ok(service.selectByCpf(cpf));
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }
    }
}
