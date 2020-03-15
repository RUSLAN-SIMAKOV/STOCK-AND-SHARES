package ruslan.simakov.stockandshares;

import lombok.extern.slf4j.Slf4j;
import ruslan.simakov.stockandshares.model.Stock;
import ruslan.simakov.stockandshares.service.CompanyService;
import ruslan.simakov.stockandshares.service.StockService;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class StockandsharesApplication {

    private static CompanyService companyService;
    private static StockService stockService;

    public StockandsharesApplication(StockService stockService, CompanyService companyService) {
        this.stockService = stockService;
        this.companyService = companyService;
    }

    public static void main(String[] args) {

        SpringApplication.run(StockandsharesApplication.class, args);
        log.info("Spring application started successful");
        List<Stock> listStock = stockService.getStocksInfoFromWeb();
        stockService.addStocksInfoToDataBase(listStock);
        log.info("Data symbols/names/state for each trading company added to DB");
        while (true != false) {

            companyService.getInfoAboutCompany(listStock);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error("Thread has been interrupted", e);
            }
            System.out.println(stockService.getStockById(1L));
        }
    }
}
