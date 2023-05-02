package nl.han.ica.veiligveilen.server.dao;

import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.AuctionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuctionTypeRepository extends JpaRepository<AuctionType, UUID> {
}
