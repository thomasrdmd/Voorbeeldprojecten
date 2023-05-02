package nl.han.ica.veiligveilen.server.dao;

import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, UUID> {

}
