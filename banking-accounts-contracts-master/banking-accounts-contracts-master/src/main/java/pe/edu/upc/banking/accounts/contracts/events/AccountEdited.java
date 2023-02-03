package pe.edu.upc.banking.accounts.contracts.events;

import lombok.Value;
import java.math.BigDecimal;
import java.time.Instant;

@Value
public class AccountEdited {
    private String accountId;
    private BigDecimal overdraftLimit;
    private Instant occurredOn;
}