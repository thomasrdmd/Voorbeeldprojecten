package nl.han.ica.veiligveilen.server.dao;

import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Auction;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILotRepository extends JpaRepository<Lot, UUID> {

	List<Lot> findAllByAuction(Auction auction);

}
