package ruslan.simakov.stockandshares.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Stock {

    @Id
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
