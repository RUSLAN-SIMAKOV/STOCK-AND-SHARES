package ruslan.simakov.stockandshares.service;

import ruslan.simakov.stockandshares.model.dto.StockDto;
import java.io.IOException;
import java.util.List;

public interface StockService {

    List<StockDto> getStocksInfoFromWeb();

    void addStocksInfoToDataBase(List<StockDto> listStockDto);
}
