package pe.edu.upc.banking.customers.contracts.events;

import lombok.Value;
import java.time.Instant;

@Value
public class CustomerRegistered {
    private String customerId;
    private String firstName;
    private String lastName;
    private String dni;
    private Instant occurredOn;
}