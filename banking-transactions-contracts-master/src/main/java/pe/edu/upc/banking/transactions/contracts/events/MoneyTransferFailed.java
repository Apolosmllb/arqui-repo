package pe.edu.upc.banking.transactions.contracts.events;

import lombok.Value;
import java.time.Instant;

@Value
public class MoneyTransferFailed {
    private String transactionId;
    private Instant occurredOn;
}