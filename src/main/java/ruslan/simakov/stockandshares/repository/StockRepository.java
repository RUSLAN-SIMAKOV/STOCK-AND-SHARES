package ruslan.simakov.stockandshares.repository;

import ruslan.simakov.stockandshares.model.Stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
