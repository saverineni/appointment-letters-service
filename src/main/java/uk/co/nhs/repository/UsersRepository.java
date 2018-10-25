package uk.co.nhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.nhs.model.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}