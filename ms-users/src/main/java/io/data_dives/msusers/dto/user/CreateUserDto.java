package io.data_dives.msusers.dto.user;

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
}

