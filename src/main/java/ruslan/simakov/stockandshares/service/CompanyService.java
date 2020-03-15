package ruslan.simakov.stockandshares.service;

import ruslan.simakov.stockandshares.model.Company;
import ruslan.simakov.stockandshares.model.Stock;
import java.util.List;

public interface CompanyService {
    List<Company> getInfoAboutCompany(List<Stock> listStock);
}
