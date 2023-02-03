package pe.edu.upc.banking.customers.query.projections;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.customers.command.domain.*;
import pe.edu.upc.banking.customers.contracts.events.*;
import java.time.Instant;
import java.util.Optional;

@Component
public class CustomerHistoryViewProjection {
	private final CustomerHistoryViewRepository customerHistoryViewRepository;

	public CustomerHistoryViewProjection(CustomerHistoryViewRepository customerHistoryViewRepository) {
        this.customerHistoryViewRepository = customerHistoryViewRepository;
    }
	
	@EventHandler
    public void on(CustomerRegistered event, @Timestamp Instant timestamp) {
		CustomerHistoryView customerHistoryView = new CustomerHistoryView(event.getCustomerId(), event.getFirstName(), event.getLastName(), event.getDni(), CustomerStatus.ACTIVE.toString(), event.getOccurredOn());
		customerHistoryViewRepository.save(customerHistoryView);
    }
	
	@EventHandler
    public void on(CustomerEdited event, @Timestamp Instant timestamp) {
		Optional<CustomerHistoryView> customerHistoryViewOptional = customerHistoryViewRepository.getLastByCustomerId(event.getCustomerId().toString());
		if (customerHistoryViewOptional.isPresent()) {
			CustomerHistoryView customerHistoryView = customerHistoryViewOptional.get();
			customerHistoryView = new CustomerHistoryView(customerHistoryView);
			customerHistoryView.setFirstName(event.getFirstName());
			customerHistoryView.setLastName(event.getLastName());
			customerHistoryView.setDni(event.getDni());
			customerHistoryView.setCreatedAt(event.getOccurredOn());
			customerHistoryViewRepository.save(customerHistoryView);
		}
    }
}