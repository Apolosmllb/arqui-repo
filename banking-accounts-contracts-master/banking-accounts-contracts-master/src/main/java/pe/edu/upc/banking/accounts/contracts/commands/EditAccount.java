package pe.edu.upc.banking.accounts.contracts.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.math.BigDecimal;

@Value
public class EditAccount {
    @TargetAggregateIdentifier
    private String accountId;
    private BigDecimal overdraftLimit;
}