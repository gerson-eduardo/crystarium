package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.dto.BooleanMessageResponse;
import io.data_dives.ms_proposal.dto.CreateProposalDto;
import io.data_dives.ms_proposal.ex.InvalidUserException;
import io.data_dives.ms_proposal.model.Proposal;
import io.data_dives.ms_proposal.producer.RequestProducer;
import io.data_dives.ms_proposal.repository.ProposalRepository;
import io.data_dives.ms_proposal.service.IProposalService;
import jakarta.transaction.Transactional;
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
    private RequestProducer producer;

    @Autowired
    public ProposalService(ProposalRepository repository, Clock clock, RequestProducer producer) {
        this.repository = repository;
        this.clock = clock;
        this.producer = producer;
    }

    @Override
    @Transactional
    public void createProposal(CreateProposalDto dto){
        ZonedDateTime now = ZonedDateTime.now(clock);

        BooleanMessageResponse response = producer.validateUser(dto.getCpf());
        if(!response.isResult()){
            throw new InvalidUserException(response.getMessage());
        }

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
