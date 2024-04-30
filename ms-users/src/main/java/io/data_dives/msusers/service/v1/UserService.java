package io.data_dives.msusers.service.v1;

import io.data_dives.msusers.dto.user.CreateUserDto;
import io.data_dives.msusers.model.User;
import io.data_dives.msusers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;

@Service
public class UserService {
    private UserRepository repository;
    private Clock clock;

    @Autowired
    public UserService(UserRepository repository, Clock clock){
        this.repository = repository;
        this.clock = clock;
    }
    public void createUser(CreateUserDto dto){
        //checks if user exist
        User user = repository.findByCpf(dto.getCpf()).orElse(null);
        if(user == null){
            return;
        }

        //check valid cpf

        //add dto atributes to user
        user = new User(dto);

        //Add created time and modified time
        user.setCreated(ZonedDateTime.now(clock));
        user.setModified(ZonedDateTime.now(clock));

        repository.save(user);
    }
}
