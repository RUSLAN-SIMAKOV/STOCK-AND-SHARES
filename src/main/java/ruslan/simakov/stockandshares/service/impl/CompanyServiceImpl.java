package ruslan.simakov.stockandshares.service.impl;

import static ruslan.simakov.stockandshares.service.impl.StockServiceImpl.STOCKS_API_ENDPOINT;
import static ruslan.simakov.stockandshares.service.impl.StockServiceImpl.TOKEN;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ruslan.simakov.stockandshares.model.Company;
import ruslan.simakov.stockandshares.model.Stock;
import ruslan.simakov.stockandshares.service.CompanyService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {


    private ConcurrentLinkedQueue<String> companyNamesSet = new ConcurrentLinkedQueue<>();
    private List<Company> listCompany = new ArrayList<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Company> getInfoAboutCompany(List<Stock> listStock) {
        for (Stock stock : listStock) {
            String companyName = stock.getSymbol();
            companyNamesSet.add(companyName);
            companyName = companyNamesSet.poll();
            if (companyName != null) {
                HttpGet request = new HttpGet(STOCKS_API_ENDPOINT
                        + "stock/" + companyName + "/quote?" + "token=" + TOKEN);
                try (CloseableHttpClient httpClient = HttpClients.createDefault();
                     CloseableHttpResponse response = httpClient.execute(request)) {

                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        listCompany = objectMapper.readValue(EntityUtils.toString(entity),
                                new TypeReference<List<Company>>() {
                                });
                    }
                } catch (IOException e) {
                    log.error("Can't get data from web!");
                    throw new RuntimeException("Can't get data from web!", e);
                }
            }
        }
        return listCompany;
    }
}
