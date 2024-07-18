package io.data_dives.ms_proposal.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.data_dives.ms_proposal.dto.BooleanMessageResponse;
import io.data_dives.ms_proposal.dto.CreateProposalDto;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class RequestProducer {

    private RabbitTemplate template;
    private ObjectMapper mapper;

    @Autowired
    public RequestProducer(RabbitTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public BooleanMessageResponse validateUser(String cpf){
        System.out.println("Sending request");
        try {
            return mapper.readValue((String) template.convertSendAndReceive("request_exchange", "user", cpf), BooleanMessageResponse.class);
        }catch(AmqpException | MessagingException | JsonProcessingException  e){
            System.out.println("Error validating user " + e.getMessage());
            return new BooleanMessageResponse(e.getMessage(), false);
        }
    }
}
