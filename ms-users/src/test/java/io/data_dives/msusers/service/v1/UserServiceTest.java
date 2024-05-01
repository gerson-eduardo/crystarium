package io.data_dives.msusers.service.v1;

import io.data_dives.msusers.model.User;
import io.data_dives.msusers.repository.UserRepository;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static io.data_dives.msusers.test_objects.UserTestObj.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private Clock clock;

    private static ZonedDateTime NOW = ZonedDateTime.of(
            2024,
            4,
            30,
            16,
            51,
            30,
            0,
            ZoneId.of("GMT-3")
    );

    @InjectMocks
    private UserService service;

    @BeforeEach()
    void setup(){
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
    }

    @Test
    void createUser() {
        User user = USER1;
        user.setId(null);
        when(repository.findByCpf(USER_DTO1.getCpf())).thenReturn(Optional.empty());
        when(repository.save(user)).thenReturn(USER1);

        assertDoesNotThrow(() -> service.createUser(USER_DTO1));

        verify(repository, atLeast(1)).findByCpf(USER_DTO1.getCpf());
        verify(repository, atLeast(1)).save(USER1);
    }
}