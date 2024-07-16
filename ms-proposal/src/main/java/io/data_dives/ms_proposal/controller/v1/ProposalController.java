package io.data_dives.ms_proposal.controller.v1;

import io.data_dives.ms_proposal.dto.CreateProposalDto;
import io.data_dives.ms_proposal.service.IProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/v1")
@RestController
public class ProposalController {

    @Autowired
    private IProposalService service;

    @PostMapping("/proposal")
    public ResponseEntity<String> createProposal(@RequestBody CreateProposalDto dto){
        try {
            service.createProposal(dto);
            return ResponseEntity.status(201).body("Proposal Created Sucessfully");
        }catch (ResponseStatusException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).body("Error while creating proposal!");
        }
    }
}
