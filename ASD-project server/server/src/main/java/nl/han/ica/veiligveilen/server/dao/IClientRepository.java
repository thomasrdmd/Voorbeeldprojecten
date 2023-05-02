package nl.han.ica.veiligveilen.server.dao;

import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, UUID> {

}
