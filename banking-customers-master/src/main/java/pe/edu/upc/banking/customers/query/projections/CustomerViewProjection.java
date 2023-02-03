package pe.edu.upc.banking.customers.query.projections;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.customers.command.domain.*;
import pe.edu.upc.banking.customers.contracts.events.*;
import java.time.Instant;
import java.util.Optional;

@Component
public class CustomerViewProjection {
	private final CustomerViewRepository customerViewRepository;
	
	public CustomerViewProjection(CustomerViewRepository customerViewRepository) {
        this.customerViewRepository = customerViewRepository;
    }
	
	@EventHandler
    public void on(CustomerRegistered event, @Timestamp Instant timestamp) {
		CustomerView customerView = new CustomerView(event.getCustomerId(), event.getFirstName(), event.getLastName(), event.getDni(), CustomerStatus.ACTIVE.toString(), event.getOccurredOn());
		customerViewRepository.save(customerView);
    }
	
	@EventHandler
    public void on(CustomerEdited event, @Timestamp Instant timestamp) {
		Optional<CustomerView> customerViewOptional = customerViewRepository.findById(event.getCustomerId().toString());
		if (customerViewOptional.isPresent()) {
			CustomerView customerView = customerViewOptional.get();
			customerView.setFirstName(event.getFirstName());
			customerView.setLastName(event.getLastName());
			customerView.setDni(event.getDni());
			customerView.setUpdatedAt(event.getOccurredOn());
			customerViewRepository.save(customerView);
		}
    }
}