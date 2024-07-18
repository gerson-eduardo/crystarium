package io.data_dives.ms_proposal.service.v1;

import io.data_dives.ms_proposal.ex.InvalidUserException;
import io.data_dives.ms_proposal.model.Proposal;
import io.data_dives.ms_proposal.producer.RequestProducer;
import io.data_dives.ms_proposal.repository.ProposalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static io.data_dives.ms_proposal.props.ProposalProps.*;
import static io.data_dives.ms_proposal.props.MessageProps.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProposalServiceTest {

    @Mock
    private ProposalRepository repository;
    @Mock
    private Clock clock;
    @Mock
    private RequestProducer producer;
    @InjectMocks
    private ProposalService service;

    @Test
    public void createProposal_valid_proposal(){
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(producer.validateUser(PROPOSAL1.getCpf())).thenReturn(BOOLEAN_MSG_RESP1);
        when(repository.save(PROPOSAL1_NO_ID)).thenReturn(PROPOSAL1);

        assertDoesNotThrow(() -> service.createProposal(CREATE_PROPOSAL_DTO1));

        verify(producer, atLeast(1)).validateUser(PROPOSAL1.getCpf());
        verify(repository, atLeast(1)).save(PROPOSAL1_NO_ID);
    }

    @Test
    public void createProposal_invalid_user(){
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(producer.validateUser(PROPOSAL1.getCpf())).thenReturn(BOOLEAN_MSG_RESP2);

        assertThrows(InvalidUserException.class ,() -> service.createProposal(CREATE_PROPOSAL_DTO1));

        verify(producer, atLeast(1)).validateUser(PROPOSAL1.getCpf());
        verify(repository, never()).save(PROPOSAL1_NO_ID);
    }
}