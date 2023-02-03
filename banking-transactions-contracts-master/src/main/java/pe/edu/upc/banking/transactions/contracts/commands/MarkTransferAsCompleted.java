package pe.edu.upc.banking.transactions.contracts.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class MarkTransferAsCompleted {
    @TargetAggregateIdentifier
    private String transactionId;
}