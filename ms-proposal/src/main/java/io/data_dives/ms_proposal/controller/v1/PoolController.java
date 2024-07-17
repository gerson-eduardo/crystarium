package io.data_dives.ms_proposal.controller.v1;

import io.data_dives.ms_proposal.ex.PoolAlreadyStartedException;
import io.data_dives.ms_proposal.ex.PoolNotEndedException;
import io.data_dives.ms_proposal.ex.PoolNotFoundException;
import io.data_dives.ms_proposal.ex.ProposalNotFoundException;
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
        }catch (ProposalNotFoundException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }catch (PoolAlreadyStartedException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping("/pool/end/{id}")
    public ResponseEntity<String> endPool(@PathVariable Long id){
        try {
            service.endPool(id);
            return ResponseEntity.ok("Pool ended sucessfully");
        }catch (PoolNotFoundException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }catch (PoolNotEndedException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }
    }
}
