package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.dto.CreateVoteDto;
import io.data_dives.ms_proposal.ex.ProposalNotFoundException;
import io.data_dives.ms_proposal.ex.VoteConflictException;
import io.data_dives.ms_proposal.model.Proposal;
import io.data_dives.ms_proposal.model.Vote;
import io.data_dives.ms_proposal.repository.ProposalRepository;
import io.data_dives.ms_proposal.repository.VoteRepository;
import io.data_dives.ms_proposal.service.IVoteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;

@Service
public class VoteService implements IVoteService {

    private ProposalRepository proposalRepository;
    private VoteRepository voteRepository;
    private Clock clock;

    @Autowired
    public VoteService(ProposalRepository proposalRepository, VoteRepository voteRepository, Clock clock) {
        this.proposalRepository = proposalRepository;
        this.voteRepository = voteRepository;
        this.clock = clock;
    }

    @Override
    @Transactional
    public void createVote(CreateVoteDto dto) {
        ZonedDateTime now = ZonedDateTime.now(clock);

        Proposal proposal = proposalRepository.findById(dto.getId()).orElse(null);
        if(proposal == null){
            throw new ProposalNotFoundException("Proposal with id " + dto.getId() + "not found !");
        }

        Vote vote = voteRepository.findByCpfAndProposal_Id(dto.getCpf(), dto.getId()).orElse(null);
        if(vote != null){
            throw new VoteConflictException("Vote alredy exists with this cpf and proposal id");
        }

        vote = new Vote(dto);
        vote.setCreatedAt(now);
        vote.setModifiedAt(now);
        vote.setProposal(proposal);

        voteRepository.save(vote);
    }
}
