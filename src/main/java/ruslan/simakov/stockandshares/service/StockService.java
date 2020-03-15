package ruslan.simakov.stockandshares.service;

import ruslan.simakov.stockandshares.model.Company;
import ruslan.simakov.stockandshares.model.Stock;
import java.util.List;

public interface StockService {

    List<Stock> getStocksInfoFromWeb();

    void addStocksInfoToDataBase(List<Stock> listStock);

    Stock getStockById(Long id);

    List<Company> getTopFiveHighestValueStocks();

    List<Company> getTopFiveCompaniesWithGreatestChangePercentInStockValue();
}
