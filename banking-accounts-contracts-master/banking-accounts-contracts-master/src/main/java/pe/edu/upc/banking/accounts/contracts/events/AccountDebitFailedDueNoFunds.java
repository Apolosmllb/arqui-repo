package pe.edu.upc.banking.accounts.contracts.events;

import lombok.Value;
import java.time.Instant;

@Value
public class AccountDebitFailedDueNoFunds {
    private String accountId;
    private String transactionId;
    private Instant occurredOn;
}