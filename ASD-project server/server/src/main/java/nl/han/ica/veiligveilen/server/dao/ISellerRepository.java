package nl.han.ica.veiligveilen.server.dao;

import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISellerRepository extends JpaRepository<Seller, UUID> {
}
