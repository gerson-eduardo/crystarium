package io.data_dives.msusers.dto.user;

import io.data_dives.msusers.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectUserDto {
    private String name;
    private String email;
    private ZonedDateTime modified;

    public SelectUserDto(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.modified = user.getModified();
    }
}
