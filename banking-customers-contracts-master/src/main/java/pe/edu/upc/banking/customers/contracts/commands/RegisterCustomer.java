package pe.edu.upc.banking.customers.contracts.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class RegisterCustomer {
    @TargetAggregateIdentifier
    private String customerId;
    private String firstName;
    private String lastName;
    private String dni;
}