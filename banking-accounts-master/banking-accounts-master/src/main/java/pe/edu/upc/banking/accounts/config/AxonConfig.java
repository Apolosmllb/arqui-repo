package pe.edu.upc.banking.accounts.config;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.upc.banking.accounts.command.domain.Account;

@Configuration
public class AxonConfig {
    @Bean
    public EventSourcingRepository<Account> eventSourcingRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(Account.class)
            .eventStore(eventStore)
            .build();
    }
}