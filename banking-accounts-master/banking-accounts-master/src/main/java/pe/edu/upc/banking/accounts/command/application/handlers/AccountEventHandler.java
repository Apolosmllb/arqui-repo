package pe.edu.upc.banking.accounts.command.application.handlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.accounts.command.infra.AccountNumber;
import pe.edu.upc.banking.accounts.command.infra.AccountNumberRepository;
import pe.edu.upc.banking.accounts.contracts.events.AccountOpened;

@Component
@ProcessingGroup("accountNumber")
public class AccountEventHandler {
    private final AccountNumberRepository accountNumberRepository;

    public AccountEventHandler(AccountNumberRepository accountNumberRepository) {
        this.accountNumberRepository = accountNumberRepository;
    }

    @EventHandler
    public void on(AccountOpened event) {
        accountNumberRepository.save(new AccountNumber(event.getNumber(), event.getCustomerId()));
    }
}