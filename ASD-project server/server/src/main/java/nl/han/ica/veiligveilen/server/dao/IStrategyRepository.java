package nl.han.ica.veiligveilen.server.dao;

import java.util.Optional;
import nl.han.ica.veiligveilen.server.model.dataclasses.Strategy;
import nl.han.ica.veiligveilen.server.model.keys.StrategyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStrategyRepository extends JpaRepository<Strategy, StrategyKey> {

	Optional<Strategy> findById(StrategyKey strategyKey);
}
