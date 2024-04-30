package io.data_dives.msusers.model;

import io.data_dives.msusers.dto.user.CreateUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private ZonedDateTime created;
    private ZonedDateTime modified;

    public User(CreateUserDto dto){
        this.name = dto.getName();
        this.cpf = dto.getCpf();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
    }
}
