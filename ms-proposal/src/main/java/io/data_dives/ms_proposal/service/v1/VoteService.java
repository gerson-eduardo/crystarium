package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.dto.BooleanMessageResponse;
import io.data_dives.ms_proposal.dto.CreateVoteDto;
import io.data_dives.ms_proposal.ex.*;
import io.data_dives.ms_proposal.model.Pool;
import io.data_dives.ms_proposal.model.Proposal;
import io.data_dives.ms_proposal.model.Vote;
import io.data_dives.ms_proposal.producer.RequestProducer;
import io.data_dives.ms_proposal.repository.PoolRepository;
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
    private PoolRepository poolRepository;
    private Clock clock;
    private RequestProducer producer;

    @Autowired
    public VoteService(ProposalRepository proposalRepository, VoteRepository voteRepository, PoolRepository poolRepository, Clock clock, RequestProducer producer) {
        this.proposalRepository = proposalRepository;
        this.voteRepository = voteRepository;
        this.poolRepository = poolRepository;
        this.clock = clock;
        this.producer = producer;
    }

    @Override
    @Transactional
    public void createVote(CreateVoteDto dto) {
        ZonedDateTime now = ZonedDateTime.now(clock);

        BooleanMessageResponse response = producer.validateUser(dto.getCpf());
        if(!response.isResult()){
            throw new InvalidUserException(response.getMessage());
        }

        Pool pool = poolRepository.findByProposal_Id(dto.getId()).orElse(null);
        if(pool == null){
            throw new PoolNotFoundException("Pool with proposal_id " + dto.getId() + "not found !");
        }

        if(now.isAfter(pool.getEnd())){
            throw new PoolAlreadyEndedException("Pool with proposal_id " + dto.getId() + "already ended");
        }

        Vote vote = voteRepository.findByCpfAndProposal_Id(dto.getCpf(), dto.getId()).orElse(null);
        if(vote != null){
            throw new VoteConflictException("Vote alredy exists with this cpf and proposal id");
        }

        vote = new Vote(dto);
        vote.setCreatedAt(now);
        vote.setModifiedAt(now);
        vote.setProposal(pool.getProposal());

        voteRepository.save(vote);
    }
}
