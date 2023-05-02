package nl.han.ica.veiligveilen.server.dao;

import java.util.Optional;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Auction;
import nl.han.ica.veiligveilen.server.model.dataclasses.IPDiscovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIPDiscoveryRepository extends JpaRepository<IPDiscovery, UUID> {

	Optional<IPDiscovery> findByAuction(Auction auction);

	void deleteByAuction(Auction auction);
}
