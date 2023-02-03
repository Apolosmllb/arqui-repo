package pe.edu.upc.banking.transactions.contracts.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class MarkTransferAsFailed {
    @TargetAggregateIdentifier
    private String transactionId;
}