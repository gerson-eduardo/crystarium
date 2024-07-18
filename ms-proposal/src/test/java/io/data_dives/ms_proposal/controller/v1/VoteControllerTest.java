package io.data_dives.ms_proposal.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.data_dives.ms_proposal.ex.*;
import io.data_dives.ms_proposal.service.v1.VoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static io.data_dives.ms_proposal.props.VoteProps.*;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(VoteController.class)
class VoteControllerTest {

    @MockBean
    private VoteService service;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;


    @Test
    void createVote_sucess() throws Exception {
        doNothing().when(service).createVote(CREATE_VOTE_DTO1);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(CREATE_VOTE_DTO1)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createVote_pool_not_found() throws Exception{
        doThrow(PoolNotFoundException.class).when(service).createVote(CREATE_VOTE_DTO1);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CREATE_VOTE_DTO1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createVote_pool_already_ended() throws Exception{
        doThrow(PoolAlreadyEndedException.class).when(service).createVote(CREATE_VOTE_DTO1);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CREATE_VOTE_DTO1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createVote_vote_already_exist() throws Exception {
        doThrow(VoteConflictException.class).when(service).createVote(CREATE_VOTE_DTO1);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CREATE_VOTE_DTO1)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void createVote_invalid_user() throws Exception {
        doThrow(InvalidUserException.class).when(service).createVote(CREATE_VOTE_DTO1);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CREATE_VOTE_DTO1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}