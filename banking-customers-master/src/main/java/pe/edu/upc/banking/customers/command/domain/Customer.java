package pe.edu.upc.banking.customers.command.domain;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import pe.edu.upc.banking.customers.contracts.commands.*;
import pe.edu.upc.banking.customers.contracts.events.*;
import java.time.Instant;

@Aggregate
public class Customer {
    @AggregateIdentifier
    private String customerId;
    private String firstName;
    private String lastName;
    private String dni;
    private CustomerStatus status;

    public Customer() {
    }

    @CommandHandler
    public Customer(RegisterCustomer command) {
        Instant now = Instant.now();
        apply(
            new CustomerRegistered(
                command.getCustomerId(),
                command.getFirstName(),
                command.getLastName(),
                command.getDni(),
                now
            )
        );
    }

    @CommandHandler
    public void handle(EditCustomer command) {
        Instant now = Instant.now();
        apply(
            new CustomerEdited(
                command.getCustomerId(),
                command.getFirstName(),
                command.getLastName(),
                command.getDni(),
                now
            )
        );
    }

    @EventSourcingHandler
    protected void on(CustomerRegistered event) {
        customerId = event.getCustomerId();
        firstName = event.getFirstName();
        lastName = event.getLastName();
        dni = event.getDni();
        status = CustomerStatus.ACTIVE;
    }

    @EventSourcingHandler
    protected void on(CustomerEdited event) {
        firstName = event.getFirstName();
        lastName = event.getLastName();
        dni = event.getDni();
    }
}