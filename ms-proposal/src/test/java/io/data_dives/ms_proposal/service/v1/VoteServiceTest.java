package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.ex.ProposalNotFoundException;
import io.data_dives.ms_proposal.ex.VoteConflictException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private ProposalRepository proposalRepository;
    @Mock
    private Clock clock;
    @InjectMocks
    private VoteService service;

    @Test
    void createVote_success() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(proposalRepository.findById(VOTE1.getProposal().getId())).thenReturn(Optional.of(VOTE1.getProposal()));
        when(voteRepository.findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> service.createVote(CREATE_VOTE_DTO1));

        verify(proposalRepository, atLeast(1)).findById(CREATE_VOTE_DTO1.getId());
        verify(voteRepository, atLeast(1)).findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId());
        verify(voteRepository, atLeast(1)).save(VOTE1_NO_ID);
    }

    @Test
    void createVote_proposal_not_found() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(proposalRepository.findById(VOTE1.getProposal().getId())).thenReturn(Optional.empty());

        assertThrows(ProposalNotFoundException.class, () -> service.createVote(CREATE_VOTE_DTO1));

        verify(proposalRepository, atLeast(1)).findById(CREATE_VOTE_DTO1.getId());
        verify(voteRepository, never()).findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId());
        verify(voteRepository, never()).save(VOTE1_NO_ID);
    }

    @Test
    void createVote_vote_already_created() {
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(proposalRepository.findById(VOTE1.getProposal().getId())).thenReturn(Optional.of(VOTE1.getProposal()));
        when(voteRepository.findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId())).thenReturn(Optional.of(VOTE1));
        assertThrows(VoteConflictException.class, () -> service.createVote(CREATE_VOTE_DTO1));

        verify(proposalRepository, atLeast(1)).findById(CREATE_VOTE_DTO1.getId());
        verify(voteRepository, atLeast(1)).findByCpfAndProposal_Id(CREATE_VOTE_DTO1.getCpf(), CREATE_VOTE_DTO1.getId());
        verify(voteRepository, never()).save(VOTE1_NO_ID);
    }

}