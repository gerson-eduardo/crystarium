package io.data_dives.ms_proposal.controller.v1;

import io.data_dives.ms_proposal.dto.CreateVoteDto;
import io.data_dives.ms_proposal.ex.ProposalNotFoundException;
import io.data_dives.ms_proposal.ex.VoteConflictException;
import io.data_dives.ms_proposal.service.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VoteController {

    @Autowired
    private IVoteService service;

    @PostMapping("/vote")
    public ResponseEntity<String> createVote(@RequestBody CreateVoteDto dto){
        try {
            service.createVote(dto);
            return ResponseEntity.status(201).body("Proposal created sucessfully");
        }catch (ProposalNotFoundException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }catch (VoteConflictException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }
    }
}
