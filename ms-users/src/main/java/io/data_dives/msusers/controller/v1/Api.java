package io.data_dives.msusers.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Api {

    @GetMapping("")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }
}
