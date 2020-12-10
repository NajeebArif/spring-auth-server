package narif.manslp.msoauth2.authserver.repositories;

import narif.manslp.msoauth2.authserver.entities.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientJpaRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByClientId(String clientId);
}
