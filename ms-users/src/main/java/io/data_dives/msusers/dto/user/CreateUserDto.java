package io.data_dives.msusers.dto.user;

import io.data_dives.msusers.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDto {
    private String name;
    private String cpf;
    private String email;
    private String password;

    public CreateUserDto(User user){
        this.name = user.getName();
        this.cpf = user.getCpf();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}

