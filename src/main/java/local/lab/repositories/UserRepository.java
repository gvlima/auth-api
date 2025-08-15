package local.lab.repositories;

import local.lab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User u set u.refreshToken = :refreshToken where u.id = :id")
    void updateRefreshToken(@Param(value = "id") String id, @Param(value = "refreshToken") String refreshToken);
}
