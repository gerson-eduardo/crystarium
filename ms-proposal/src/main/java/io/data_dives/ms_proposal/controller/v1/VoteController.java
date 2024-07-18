package io.data_dives.ms_proposal.controller.v1;

import io.data_dives.ms_proposal.dto.CreateVoteDto;
import io.data_dives.ms_proposal.ex.*;
import io.data_dives.ms_proposal.service.IVoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "vote-controller")
public class VoteController {

    @Autowired
    private IVoteService service;

    @PostMapping("/vote")
    @Operation(summary = "Creates a vote in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vote created sucessfully"),
            @ApiResponse(responseCode = "404", description = "Pool was not found"),
            @ApiResponse(responseCode = "400", description = "Pool already ended or invalid user"),
            @ApiResponse(responseCode = "409", description = "Vote for this proposal with this cpf already created")
    })
    public ResponseEntity<String> createVote(@RequestBody CreateVoteDto dto){
        try {
            service.createVote(dto);
            return ResponseEntity.status(201).body("Proposal created sucessfully");
        }catch (PoolNotFoundException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }catch (PoolAlreadyEndedException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }catch (VoteConflictException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }catch(InvalidUserException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }
}
