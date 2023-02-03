package pe.edu.upc.banking.customers.config;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.upc.banking.customers.command.domain.Customer;

@Configuration
public class AxonConfig {
    @Bean
    public Repository<Customer> eventSourcingRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(Customer.class)
            .eventStore(eventStore)
            .build();
    }
}