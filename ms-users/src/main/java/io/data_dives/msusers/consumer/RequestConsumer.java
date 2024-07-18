package io.data_dives.msusers.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.data_dives.msusers.dto.BooleanMessageResponse;
import io.data_dives.msusers.model.User;
import io.data_dives.msusers.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestConsumer {

    private RabbitTemplate template;
    private UserRepository repository;
    private ObjectMapper mapper;

    @Autowired
    public RequestConsumer(RabbitTemplate template, UserRepository repository, ObjectMapper mapper) {
        this.template = template;
        this.repository = repository;
        this.mapper = mapper;
    }


    @RabbitListener(queues = "validate_user_queue")
    public String validateUser(String cpf) throws JsonProcessingException {
        User user = repository.findByCpf(cpf).orElse(null);
        if(user == null){
            return mapper.writeValueAsString(new BooleanMessageResponse("User not found", false));
        }
        return mapper.writeValueAsString(new BooleanMessageResponse("User is valid", true));
    }

}
