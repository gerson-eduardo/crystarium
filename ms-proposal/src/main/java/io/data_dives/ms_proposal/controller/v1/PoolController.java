package io.data_dives.ms_proposal.controller.v1;

import io.data_dives.ms_proposal.service.IPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PoolController {

    @Autowired
    private IPoolService service;

    @PostMapping("/pool/start/{id}")
    public ResponseEntity<String> startPool(@PathVariable Long id){
        try {
            service.createPool(id);
            return ResponseEntity.ok("Pool started");
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
}
