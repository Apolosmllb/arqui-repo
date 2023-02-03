package pe.edu.upc.banking.customers.command.application.handlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.customers.command.infra.*;
import pe.edu.upc.banking.customers.contracts.events.*;
import java.util.Optional;

@Component
@ProcessingGroup("customerDni")
public class CustomerEventHandler {
    private final CustomerDniRepository customerDniRepository;

    public CustomerEventHandler(CustomerDniRepository customerDniRepository) {
        this.customerDniRepository = customerDniRepository;
    }

    @EventHandler
    public void on(CustomerRegistered event) {
        customerDniRepository.save(new CustomerDni(event.getDni(), event.getCustomerId()));
    }

    @EventHandler
    public void on(CustomerEdited event) {
        Optional<CustomerDni> CustomerDniOptional = customerDniRepository.getDniByCustomerId(event.getCustomerId());
        CustomerDniOptional.ifPresent(customerDniRepository::delete);
        customerDniRepository.save(new CustomerDni(event.getDni(), event.getCustomerId()));
    }
}