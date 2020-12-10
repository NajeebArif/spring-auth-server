package narif.manslp.msoauth2.authserver.repositories;

import narif.manslp.msoauth2.authserver.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);
}
