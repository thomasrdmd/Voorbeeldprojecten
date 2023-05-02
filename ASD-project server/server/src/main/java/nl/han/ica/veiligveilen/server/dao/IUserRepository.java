package nl.han.ica.veiligveilen.server.dao;

import java.util.Optional;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByUsername(String username);
}
