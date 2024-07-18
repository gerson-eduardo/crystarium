package io.data_dives.ms_proposal.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.data_dives.ms_proposal.ex.InvalidUserException;
import io.data_dives.ms_proposal.ex.PoolAlreadyEndedException;
import io.data_dives.ms_proposal.service.v1.ProposalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static io.data_dives.ms_proposal.props.ProposalProps.*;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(ProposalController.class)
class ProposalControllerTest {

    @MockBean
    private ProposalService service;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void createProposal_valid_proposal() throws Exception {
        doNothing().when(service).createProposal(CREATE_PROPOSAL_DTO1);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/proposal")
                .content(mapper.writeValueAsString(CREATE_PROPOSAL_DTO1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createProposal_invalid_user() throws Exception {
        doThrow(InvalidUserException.class).when(service).createProposal(CREATE_PROPOSAL_DTO1);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/proposal")
                        .content(mapper.writeValueAsString(CREATE_PROPOSAL_DTO1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}