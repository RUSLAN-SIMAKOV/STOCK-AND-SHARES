package ruslan.simakov.stockandshares.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ruslan.simakov.stockandshares.model.Stock;
import ruslan.simakov.stockandshares.model.dto.StockDto;
import ruslan.simakov.stockandshares.repository.StockRepository;
import ruslan.simakov.stockandshares.service.StockService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static final String STOCKS_API_ENDPOINT = "https://sandbox.iexapis.com/stable/";


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<StockDto> getStocksInfoFromWeb() {

        HttpGet request = new HttpGet(STOCKS_API_ENDPOINT
                + "ref-data/symbols?token=Tpk_ee567917a6b640bb8602834c9d30e571");
        List<StockDto> listStockDto = new ArrayList<>();

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                listStockDto.addAll(objectMapper.readValue(EntityUtils.toString(entity),
                        List.class));
            }
        } catch (IOException e) {
            log.error("Can't get data from web!");
            throw new RuntimeException("Can't get data from web!", e);
        }
        return listStockDto;
    }

    @Override
    public void addStocksInfoToDataBase(List<StockDto> listStockDto) {
        List<Stock> listStocks = transferStocksDtoToStocks(listStockDto);
        stockRepository.saveAll(listStocks);
    }

    private List<Stock> transferStocksDtoToStocks(List<StockDto> listStockDto) {
        List<Stock> listStocks = new ArrayList<>();
        Stock stock = new Stock();
        for (StockDto stockDto : listStockDto) {

            stock.setSymbol(stockDto.getSymbol());
            stock.setExchange(stockDto.getExchange());
            stock.setName(stockDto.getName());
            stock.setDate(stockDto.getDate());
            stock.setType(stockDto.getType());
            stock.setIexId(stockDto.getIexId());
            stock.setRegion(stockDto.getRegion());
            stock.setCurrency(stockDto.getCurrency());
            stock.setIsEnabled(stockDto.getIsEnabled());
            stock.setFigi(stockDto.getFigi());
            stock.setCik(stockDto.getCik());
            listStocks.add(stock);
        }
        return listStocks;
    }
}
