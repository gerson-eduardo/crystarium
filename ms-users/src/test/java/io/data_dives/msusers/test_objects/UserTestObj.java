package io.data_dives.msusers.test_objects;

import io.data_dives.msusers.dto.user.CreateUserDto;
import io.data_dives.msusers.model.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class UserTestObj {
    private static ZonedDateTime time1 = ZonedDateTime.of(2024, 4, 30, 16, 51, 30, 0, ZoneId.of("GMT-3"));
    private static ZonedDateTime time2 = ZonedDateTime.of(2024, 12, 21, 13, 42, 25, 0, ZoneId.of("GMT-3"));
    public static User USER1 = new User(1L, "Estininen", "11144477735", "mail@estinien.web", "drag00n.Password", time1, time1);
    public static User USER2 = new User(2L, "Nami", "11144477736", "free@gnomemail.sea", "orange.p1rate", time2,  time2);
    public static List<User> USER_LIST = Arrays.asList(USER1, USER2);

    public static CreateUserDto USER_DTO1 = new CreateUserDto(USER1);
    public static CreateUserDto USER_DTO2 = new CreateUserDto(USER2);
}
