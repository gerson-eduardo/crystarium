package io.data_dives.msusers.service.v1;

import io.data_dives.msusers.dto.user.CreateUserDto;
import io.data_dives.msusers.dto.user.SelectUserDto;
import io.data_dives.msusers.ex.user.EmailConflictException;
import io.data_dives.msusers.ex.user.UserConflictException;
import io.data_dives.msusers.ex.user.UserNotFoundException;
import io.data_dives.msusers.model.User;
import io.data_dives.msusers.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class UserService {
    private UserRepository repository;
    private Clock clock;

    @Autowired
    public UserService(UserRepository repository, Clock clock){
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public void createUser(CreateUserDto dto){
        //checks if user exist
        User user = repository.findByCpf(dto.getCpf()).orElse(null);
        if(user != null){
            throw new UserConflictException("User already exist in the database");
        }

        //check valid cpf

        //add dto atributes to user
        user = new User(dto);

        //Add created time and modified time
        user.setCreated(ZonedDateTime.now(clock));
        user.setModified(ZonedDateTime.now(clock));
        repository.save(user);
    }

    public SelectUserDto selectByCpf(String cpf){
        User user = repository.findByCpf(cpf).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found!");
        }

        return new SelectUserDto(user);
    }

    public List<SelectUserDto> selectAll(){
        return repository.findAll().stream()
                .map(SelectUserDto::new)
                .toList();
    }

    @Transactional
    public void updateEmail(String cpf, String email){
        User user;

        user = repository.findByEmail(email).orElse(null);
        if(user != null){
            throw new EmailConflictException("Email already in use!");
        }

        user = repository.findByCpf(cpf).orElse(null);
        if(user == null){
            throw new UserNotFoundException("User not found!");
        }

        user.setEmail(email);
        user.setModified(ZonedDateTime.now(clock));
        repository.save(user);
    }
}
