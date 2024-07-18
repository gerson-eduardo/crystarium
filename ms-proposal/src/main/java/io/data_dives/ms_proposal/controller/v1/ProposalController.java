package io.data_dives.ms_proposal.controller.v1;

import io.data_dives.ms_proposal.dto.CreateProposalDto;
import io.data_dives.ms_proposal.ex.InvalidUserException;
import io.data_dives.ms_proposal.service.IProposalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/v1")
@RestController
@Tag(name = "proposal-controller")
public class ProposalController {

    @Autowired
    private IProposalService service;

    @PostMapping("/proposal")
    @Operation(summary = "Creates a proposal in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proposal created sucessfully"),
            @ApiResponse(responseCode = "400", description = "The user is invalid"),
    })
    public ResponseEntity<String> createProposal(@RequestBody CreateProposalDto dto){
        try {
            service.createProposal(dto);
            return ResponseEntity.status(201).body("Proposal Created Sucessfully");
        }catch(InvalidUserException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }catch(ResponseStatusException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
}
