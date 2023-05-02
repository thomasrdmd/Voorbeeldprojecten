package nl.han.ica.veiligveilen.server.dao;

import java.time.LocalDateTime;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * De repository voor de logins tabel van de Database.
 * NIET OM IN TE LOGGEN. Daarvoor is de IUserRepository benodigd.
 */
@Repository
public interface ILoginsRepository extends JpaRepository<Login, UUID> {

	@Query(
		value = "insert into logins (login_id, user_id, client_id, ip_address, timestamp) values (?1, ?2, ?3, ?4, ?5)",
		nativeQuery = true
	)
	void insertLogin(String loginId, String userId, String clientId, String ip, LocalDateTime loginTime);
}
