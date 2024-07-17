package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.ex.PoolAlreadyStartedException;
import io.data_dives.ms_proposal.ex.PoolNotEndedException;
import io.data_dives.ms_proposal.ex.PoolNotFoundException;
import io.data_dives.ms_proposal.ex.ProposalNotFoundException;
import io.data_dives.ms_proposal.model.Pool;
import io.data_dives.ms_proposal.repository.PoolRepository;
import io.data_dives.ms_proposal.repository.ProposalRepository;
import io.data_dives.ms_proposal.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static io.data_dives.ms_proposal.props.PoolProps.*;
import static io.data_dives.ms_proposal.props.VoteProps.VOTES;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoolServiceTest {
    @Mock
    private PoolRepository poolRepository;
    @Mock
    private ProposalRepository proposalRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private Clock clock;
    @InjectMocks
    private PoolService service;

    @Test
    void createPool_pool_is_valid() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(poolRepository.findByProposal_Id(1L)).thenReturn(Optional.empty());
        when(proposalRepository.findById(1L)).thenReturn(Optional.of(POOL1.getProposal()));

        assertDoesNotThrow(() -> service.createPool(1L));

        verify(poolRepository, atLeast(1)).findByProposal_Id(1L);
        verify(proposalRepository, atLeast(1)).findById(1L);
        verify(poolRepository, atLeast(1)).save(any(Pool.class));
    }

    @Test
    void createPool_pool_already_started() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(poolRepository.findByProposal_Id(1L)).thenReturn(Optional.of(POOL1));

        assertThrows(PoolAlreadyStartedException.class, () -> service.createPool(1L));

        verify(poolRepository, atLeast(1)).findByProposal_Id(1L);
        verify(proposalRepository, never()).findById(1L);
        verify(poolRepository, never()).save(any(Pool.class));
    }

    @Test
    void createPool_proposal_not_found() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(poolRepository.findByProposal_Id(1L)).thenReturn(Optional.empty());
        when(proposalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProposalNotFoundException.class, () -> service.createPool(1L));

        verify(poolRepository, atLeast(1)).findByProposal_Id(1L);
        verify(proposalRepository, atLeast(1)).findById(1L);
        verify(poolRepository, never()).save(any(Pool.class));
    }

    @Test
    void endPool() {
        when(clock.instant()).thenReturn(NOW.plusMinutes(2).toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(poolRepository.findByProposal_Id(1L)).thenReturn(Optional.of(POOL1));
        when(voteRepository.findAllByProposal_Id(1L)).thenReturn(VOTES);

        assertDoesNotThrow(() -> service.endPool(1L));

        verify(poolRepository, atLeast(1)).findByProposal_Id(1L);
        verify(voteRepository, atLeast(1)).findAllByProposal_Id(1L);
        verify(poolRepository, atLeast(1)).save(any(Pool.class));
    }

    @Test
    void endPool_pool_not_found() {
        when(clock.instant()).thenReturn(NOW.plusMinutes(2).toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(poolRepository.findByProposal_Id(1L)).thenReturn(Optional.empty());

        assertThrows(PoolNotFoundException.class, () -> service.endPool(1L));

        verify(poolRepository, atLeast(1)).findByProposal_Id(1L);
        verify(voteRepository, never()).findAllByProposal_Id(1L);
        verify(poolRepository, never()).save(any(Pool.class));
    }

    @Test
    void endPool_pool_time_not_finished() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(poolRepository.findByProposal_Id(1L)).thenReturn(Optional.of(POOL1));

        assertThrows(PoolNotEndedException.class, () -> service.endPool(1L));

        verify(poolRepository, atLeast(1)).findByProposal_Id(1L);
        verify(voteRepository, never()).findAllByProposal_Id(1L);
        verify(poolRepository, never()).save(any(Pool.class));
    }
}