package io.data_dives.msusers.repository;

import io.data_dives.msusers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
