package io.data_dives.msusers.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "api")
public class Api {

    @GetMapping("")
    @Operation(summary = "Check if the system is responding", method = "GET")
    @ApiResponse(responseCode = "200", description = "Service is online")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }
}
