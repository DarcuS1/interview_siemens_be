package site.samijosan.siemenshotels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.samijosan.siemenshotels.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findUserByEmail(String email);

}