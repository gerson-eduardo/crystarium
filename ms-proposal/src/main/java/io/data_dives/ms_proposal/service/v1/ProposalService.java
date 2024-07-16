package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.dto.CreateProposalDto;
import io.data_dives.ms_proposal.model.Proposal;
import io.data_dives.ms_proposal.repository.ProposalRepository;
import io.data_dives.ms_proposal.service.IProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Clock;
import java.time.ZonedDateTime;

@Service
public class ProposalService implements IProposalService {
    
    private ProposalRepository repository;
    private Clock clock;

    @Autowired
    public ProposalService(ProposalRepository repository, Clock clock){
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public void createProposal(CreateProposalDto dto){
        ZonedDateTime now = ZonedDateTime.now(clock);
        Proposal proposal = new Proposal(dto);

        proposal.setCreatedAt(now);
        proposal.setModifiedAt(now);

        try {
            repository.save(proposal);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
