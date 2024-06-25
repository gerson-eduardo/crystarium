package io.data_dives.msusers.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.data_dives.msusers.ex.user.UserConflictException;
import io.data_dives.msusers.ex.user.UserNotFoundException;
import io.data_dives.msusers.service.v1.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static io.data_dives.msusers.test_objects.UserTestObj.*;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService service;
    private ObjectMapper mapper = new ObjectMapper();

    private String objectToJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
    @Test
    void createUser_valid_input() throws Exception {
        doNothing().when(service).createUser(C_USER_DTO1);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                .content(objectToJson(C_USER_DTO1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createUser_user_already_exist() throws Exception {
        doThrow(UserConflictException.class).when(service).createUser(C_USER_DTO2);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                .content(objectToJson(C_USER_DTO2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void selectUserByCpf_user_exist() throws Exception {
        when(service.selectByCpf(USER1.getCpf())).thenReturn(S_USER_DTO1);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/user/cpf/" + USER1.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Estinien"));
    }

    @Test
    void selectUserByCpf_user_dont_exist() throws Exception {
        doThrow(UserNotFoundException.class).when(service).selectByCpf(USER1.getCpf());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/user/cpf/" + USER1.getCpf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void selectAll() throws Exception {
        when(service.selectAll()).thenReturn(S_DTO_LIST);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateEmail() {
    }
}