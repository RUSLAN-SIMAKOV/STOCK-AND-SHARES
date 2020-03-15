package ruslan.simakov.stockandshares.repository;

import ruslan.simakov.stockandshares.model.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
