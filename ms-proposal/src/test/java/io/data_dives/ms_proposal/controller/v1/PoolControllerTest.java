package io.data_dives.ms_proposal.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.data_dives.ms_proposal.ex.PoolAlreadyStartedException;
import io.data_dives.ms_proposal.ex.ProposalNotFoundException;
import io.data_dives.ms_proposal.service.v1.PoolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(PoolController.class)
class PoolControllerTest {
    @MockBean
    private PoolService service;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void startPool_valid_input() throws Exception {
        doNothing().when(service).createPool(1L);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pool/start/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void startPool_proposal_not_found() throws Exception {
        doThrow(ProposalNotFoundException.class).when(service).createPool(1L);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pool/start/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void startPool_pool_already_started() throws Exception {
        doThrow(PoolAlreadyStartedException.class).when(service).createPool(1L);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pool/start/1"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void endPool() {
    }
}