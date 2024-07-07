package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.model.Proposal;
import io.data_dives.ms_proposal.repository.ProposalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static io.data_dives.ms_proposal.props.ProposalProps.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProposalServiceTest {

    @Mock
    private ProposalRepository repository;
    @Mock
    private Clock clock;
    @InjectMocks
    private ProposalService service;

    @Test
    public void createProposal_valid_proposal(){
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(repository.save(PROPOSAL1_NO_ID)).thenReturn(PROPOSAL1);

        assertDoesNotThrow(() -> service.createProposal(CREATE_PROPOSAL_DTO1));

        verify(repository, atLeast(1)).save(PROPOSAL1_NO_ID);
    }
}