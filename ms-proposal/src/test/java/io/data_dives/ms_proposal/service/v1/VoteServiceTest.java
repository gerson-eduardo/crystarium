package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.ex.InvalidUserException;
import io.data_dives.ms_proposal.ex.PoolNotFoundException;
import io.data_dives.ms_proposal.ex.ProposalNotFoundException;
import io.data_dives.ms_proposal.ex.VoteConflictException;
import io.data_dives.ms_proposal.producer.RequestProducer;
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

import static io.data_dives.ms_proposal.props.VoteProps.*;
import static io.data_dives.ms_proposal.props.PoolProps.POOL1;
import static io.data_dives.ms_proposal.props.MessageProps.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private PoolRepository poolRepository;
    @Mock
    private ProposalRepository proposalRepository;
    @Mock
    private Clock clock;
    @Mock
    private RequestProducer producer;
    @InjectMocks
    private VoteService service;

    @Test
    void createVote_success() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(producer.validateUser(VOTE1.getCpf())).thenReturn(BOOLEAN_MSG_RESP1);
        when(poolRepository.findByProposal_Id(VOTE1.getId())).thenReturn(Optional.of(POOL1));
        when(voteRepository.findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> service.createVote(CREATE_VOTE_DTO1));

        verify(producer, atLeast(1)).validateUser(VOTE1.getCpf());
        verify(poolRepository, atLeast(1)).findByProposal_Id(VOTE1.getId());
        verify(voteRepository, atLeast(1)).findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId());
        verify(voteRepository, atLeast(1)).save(VOTE1_NO_ID);
    }

    @Test
    void createVote_invalid_user() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(producer.validateUser(VOTE1.getCpf())).thenReturn(BOOLEAN_MSG_RESP2);

        assertThrows(InvalidUserException.class, () -> service.createVote(CREATE_VOTE_DTO1));

        verify(producer, atLeast(1)).validateUser(VOTE1.getCpf());
        verify(poolRepository, never()).findByProposal_Id(VOTE1.getId());
        verify(voteRepository, never()).findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId());
        verify(voteRepository, never()).save(VOTE1_NO_ID);
    }

    @Test
    void createVote_pool_not_found() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(producer.validateUser(VOTE1.getCpf())).thenReturn(BOOLEAN_MSG_RESP1);
        when(poolRepository.findByProposal_Id(VOTE1.getId())).thenReturn(Optional.empty());

        assertThrows(PoolNotFoundException.class, () -> service.createVote(CREATE_VOTE_DTO1));

        verify(producer, atLeast(1)).validateUser(VOTE1.getCpf());
        verify(poolRepository, atLeast(1)).findByProposal_Id(VOTE1.getProposal().getId());
        verify(voteRepository, never()).findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId());
        verify(voteRepository, never()).save(VOTE1_NO_ID);
    }

    @Test
    void createVote_vote_already_created() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(producer.validateUser(VOTE1.getCpf())).thenReturn(BOOLEAN_MSG_RESP1);
        when(poolRepository.findByProposal_Id(VOTE1.getId())).thenReturn(Optional.of(POOL1));
        when(voteRepository.findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId())).thenReturn(Optional.of(VOTE1));

        assertThrows(VoteConflictException.class, () -> service.createVote(CREATE_VOTE_DTO1));

        verify(producer, atLeast(1)).validateUser(VOTE1.getCpf());
        verify(poolRepository, atLeast(1)).findByProposal_Id(VOTE1.getProposal().getId());
        verify(voteRepository, atLeast(1)).findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId());
        verify(voteRepository, never()).save(VOTE1_NO_ID);
    }

}