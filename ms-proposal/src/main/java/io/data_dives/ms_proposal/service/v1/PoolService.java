package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.ex.PoolAlreadyStartedException;
import io.data_dives.ms_proposal.ex.PoolNotEndedException;
import io.data_dives.ms_proposal.ex.PoolNotFoundException;
import io.data_dives.ms_proposal.ex.ProposalNotFoundException;
import io.data_dives.ms_proposal.model.Pool;
import io.data_dives.ms_proposal.model.Proposal;
import io.data_dives.ms_proposal.model.Vote;
import io.data_dives.ms_proposal.repository.PoolRepository;
import io.data_dives.ms_proposal.repository.ProposalRepository;
import io.data_dives.ms_proposal.repository.VoteRepository;
import io.data_dives.ms_proposal.service.IPoolService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PoolService implements IPoolService {

    private PoolRepository poolRepository;
    private ProposalRepository proposalRepository;
    private VoteRepository voteRepository;
    private Clock clock;

    @Autowired
    public PoolService(PoolRepository poolRepository, ProposalRepository proposalRepository, Clock clock, VoteRepository voteRepository) {
        this.poolRepository = poolRepository;
        this.proposalRepository = proposalRepository;
        this.voteRepository = voteRepository;
        this.clock = clock;
    }

    @Override
    @Transactional
    public void createPool(Long id) {
        ZonedDateTime now = ZonedDateTime.now(clock);

        Pool pool = poolRepository.findByProposal_Id(id).orElse(null);
        if(pool != null){
            throw new PoolAlreadyStartedException("Pool already started!");
        }

        Proposal proposal = proposalRepository.findById(id).orElse(null);
        if(proposal == null){
            throw new ProposalNotFoundException("Proposal not found!");
        }



        pool = new Pool();
        pool.setProposal(proposal);
        pool.setStart(now);
        pool.setEnd(now.plusMinutes(1));
        pool.setCreated(now);
        pool.setModified(now);
        pool.setOpen(true);

        poolRepository.save(pool);
    }

    @Override
    public void endPool(Long id) {
        ZonedDateTime now = ZonedDateTime.now(clock);

        Pool pool = poolRepository.findByProposal_Id(id).orElse(null);
        if(pool == null){
            throw new PoolNotFoundException("Pool not found!");
        }

        if(now.isBefore(pool.getEnd())){
            throw new PoolNotEndedException("Pool is still running");
        }

        long result = voteRepository.findAllByProposal_Id(id)
                .stream()
                .mapToInt(vote -> vote.isApproved() ? 1: -1)
                .sum();

        pool.getProposal().setApproved(result > 0);
        pool.getProposal().setModifiedAt(now);
        pool.setModified(now);
        pool.setOpen(false);

        poolRepository.save(pool);
    }
}
