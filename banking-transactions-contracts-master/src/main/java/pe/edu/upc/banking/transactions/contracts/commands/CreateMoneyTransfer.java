package pe.edu.upc.banking.transactions.contracts.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.math.BigDecimal;

@Value
public class CreateMoneyTransfer {
    @TargetAggregateIdentifier
    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
}