package nl.han.ica.veiligveilen.server.dao;

import nl.han.ica.veiligveilen.server.model.dataclasses.UserClient;
import nl.han.ica.veiligveilen.server.model.keys.UserClientKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserClientsRepository extends JpaRepository<UserClient, UserClientKey> {

}
