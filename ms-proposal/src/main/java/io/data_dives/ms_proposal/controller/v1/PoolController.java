package io.data_dives.ms_proposal.controller.v1;

import io.data_dives.ms_proposal.ex.PoolAlreadyStartedException;
import io.data_dives.ms_proposal.ex.PoolNotEndedException;
import io.data_dives.ms_proposal.ex.PoolNotFoundException;
import io.data_dives.ms_proposal.ex.ProposalNotFoundException;
import io.data_dives.ms_proposal.service.IPoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "pool-controller")
public class PoolController {

    @Autowired
    private IPoolService service;

    @PostMapping("/pool/start/{id}")
    @Operation(summary = "Start a votation pool in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pool is created sucessfully"),
            @ApiResponse(responseCode = "404", description = "Propossal was not found"),
            @ApiResponse(responseCode = "409", description = "A pool for this proposal was already initiated"),
    }
    )
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
    @Operation(summary = "Ends a votation pool in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pool is sucessfully ended"),
            @ApiResponse(responseCode = "404", description = "Pool was not found"),
            @ApiResponse(responseCode = "409", description = "The pool end time is not finished"),
    }
    )
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
