package pe.edu.upc.banking.accounts.contracts.events;

import lombok.Value;
import java.math.BigDecimal;
import java.time.Instant;

@Value
public class AccountCredited {
    private String accountId;
    private String transactionId;
    private BigDecimal amount;
    private Instant occurredOn;
}