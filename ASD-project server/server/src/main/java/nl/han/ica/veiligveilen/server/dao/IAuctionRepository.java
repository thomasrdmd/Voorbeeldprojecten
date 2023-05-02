package nl.han.ica.veiligveilen.server.dao;

import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuctionRepository extends JpaRepository<Auction, UUID> {
}
