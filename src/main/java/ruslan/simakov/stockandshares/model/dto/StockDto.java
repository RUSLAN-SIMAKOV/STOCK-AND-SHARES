package ruslan.simakov.stockandshares.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StockDto {
    private String symbol;
    private String exchange;
    private String name;
    private LocalDate date;
    private String type;
    private String iexId;
    private String region;
    private String currency;
    private Boolean isEnabled;
    private String figi;
    private Long cik;
}
