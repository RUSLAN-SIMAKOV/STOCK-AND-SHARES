package ruslan.simakov.stockandshares;

import lombok.extern.slf4j.Slf4j;
import ruslan.simakov.stockandshares.model.dto.StockDto;
import ruslan.simakov.stockandshares.service.StockService;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class StockandsharesApplication {

    private static StockService stockService;

    public StockandsharesApplication(StockService stockService) {
        this.stockService = stockService;
    }

    public static void main(String[] args) {

        SpringApplication.run(StockandsharesApplication.class, args);
        log.info("Spring application started successful");
        List<StockDto> listStockDto = stockService.getStocksInfoFromWeb();
        stockService.addStocksInfoToDataBase(listStockDto);
        while (true != false) {
        }
    }
}
