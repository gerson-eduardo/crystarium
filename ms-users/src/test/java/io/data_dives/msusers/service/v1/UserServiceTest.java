package io.data_dives.msusers.service.v1;

import io.data_dives.msusers.dto.user.SelectUserDto;
import io.data_dives.msusers.ex.user.EmailConflictException;
import io.data_dives.msusers.ex.user.UserNotFoundException;
import io.data_dives.msusers.model.User;
import io.data_dives.msusers.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
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

    private static final ZonedDateTime NOW = ZonedDateTime.of(
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

    @Test
    void createUser_user_dont_exist() {
        User user = USER1;
        user.setId(null);

        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(repository.findByCpf(C_USER_DTO1.getCpf())).thenReturn(Optional.empty());
        when(repository.save(user)).thenReturn(USER1);

        assertDoesNotThrow(() -> service.createUser(C_USER_DTO1));

        verify(repository, atLeast(1)).findByCpf(C_USER_DTO1.getCpf());
        verify(repository, atLeast(1)).save(USER1);
    }

    @Test
    void selectByCpf_user_exist() {
        when(repository.findByCpf(USER1.getCpf())).thenReturn(Optional.of(USER1));

        SelectUserDto dto = service.selectByCpf(USER1.getCpf());

        verify(repository, atLeast(1)).findByCpf(USER1.getCpf());
        assertEquals(USER1.getEmail(), dto.getEmail());
    }

    @Test
    void selectByCpf_user_dont_exist() {
        when(repository.findByCpf("00000000000")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.selectByCpf("00000000000"));

        verify(repository, atLeast(1)).findByCpf("00000000000");
    }

    @Test
    void selectAll() {
        when(repository.findAll()).thenReturn(USER_LIST);

        List<SelectUserDto> S_DTO_L = service.selectAll();

        verify(repository, atLeast(1)).findAll();
        assertEquals(USER_LIST.getFirst().getEmail(), S_DTO_L.getFirst().getEmail());
    }

    @Test
    void updateEmail_email_dont_exist_and_user_exist() {
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
        when(repository.findByCpf(USER1.getCpf())).thenReturn(Optional.of(USER1));
        when(repository.findByEmail("new@mail.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> service.updateEmail(USER1.getCpf(), "new@mail.com"));

        verify(repository, atLeast(1)).findByCpf(USER1.getCpf());
        verify(repository, atLeast(1)).findByEmail("new@mail.com");
    }

    @Test
    void updateEmail_email_already_in_use() {
        when(repository.findByEmail("used@mail.com")).thenReturn(Optional.of(USER1));

        assertThrows(EmailConflictException.class, () -> service.updateEmail(USER1.getCpf(), "used@mail.com"));

        verify(repository, atLeast(1)).findByEmail("used@mail.com");
    }

    @Test
    void updateEmail_user_not_found() {
        when(repository.findByEmail("new@mail.com")).thenReturn(Optional.empty());
        when(repository.findByCpf("00011100011")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.updateEmail("00011100011", "new@mail.com"));

        verify(repository, atLeast(1)).findByEmail("new@mail.com");
        verify(repository, atLeast(1)).findByCpf("00011100011");
    }
}