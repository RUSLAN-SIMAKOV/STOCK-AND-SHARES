package ruslan.simakov.stockandshares.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ruslan.simakov.stockandshares.model.Company;
import ruslan.simakov.stockandshares.model.Stock;
import ruslan.simakov.stockandshares.repository.StockRepository;
import ruslan.simakov.stockandshares.service.StockService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    private List<Stock> listStock = new ArrayList<>();

    public static final String STOCKS_API_ENDPOINT = "https://sandbox.iexapis.com/stable/";
    public static final String TOKEN = "Tpk_ee567917a6b640bb8602834c9d30e571";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Stock> getStocksInfoFromWeb() {

        HttpGet request = new HttpGet(STOCKS_API_ENDPOINT
                + "ref-data/symbols?" + "token=" + TOKEN);


        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                listStock = objectMapper.readValue(EntityUtils.toString(entity),
                        new TypeReference<List<Stock>>() {
                        });
            }
        } catch (IOException e) {
            log.error("Can't get data from web!");
            throw new RuntimeException("Can't get data from web!", e);
        }
        return listStock;
    }

    @Override
    public void addStocksInfoToDataBase(List<Stock> listStock) {
        stockRepository.saveAll(listStock);
    }

    @Override
    public Stock getStockById(Long id) {
        return stockRepository.getOne(id);
    }

    @Override
    public List<Company> getTopFiveHighestValueStocks() {
        return null;
    }

    @Override
    public List<Company> getTopFiveCompaniesWithGreatestChangePercentInStockValue() {
        return null;
    }
}
