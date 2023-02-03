package pe.edu.upc.banking.accounts.query.projections;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.accounts.contracts.events.*;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AccountViewProjection {
	private final AccountViewRepository accountViewRepository;
	
	public AccountViewProjection(AccountViewRepository accountViewRepository) {
        this.accountViewRepository = accountViewRepository;
    }
	
	@EventHandler
    public void on(AccountOpened event) {
		AccountView accountView = new AccountView(event.getAccountId(), event.getCustomerId(), event.getNumber(), BigDecimal.ZERO, event.getOverdraftLimit(), event.getOccurredOn());
		accountViewRepository.save(accountView);
    }
	
	@EventHandler
    public void on(AccountEdited event) {
		Optional<AccountView> accountViewOptional = accountViewRepository.findById(event.getAccountId());
		if (accountViewOptional.isPresent()) {
			AccountView accountView = accountViewOptional.get();
			accountView.setOverdraftLimit(event.getOverdraftLimit());
			accountView.setUpdatedAt(event.getOccurredOn());
			accountViewRepository.save(accountView);
		}
    }
	
	@EventHandler
    public void on(AccountDebited event) {
		Optional<AccountView> accountViewOptional = accountViewRepository.findById(event.getAccountId());
		if (accountViewOptional.isPresent()) {
			AccountView accountView = accountViewOptional.get();
			accountView.setBalance(accountView.getBalance().subtract(event.getAmount()));
			accountViewRepository.save(accountView);
		}
    }

	@EventHandler
	public void on(FromAccountDebited event) {
		Optional<AccountView> accountViewOptional = accountViewRepository.findById(event.getAccountId());
		if (accountViewOptional.isPresent()) {
			AccountView accountView = accountViewOptional.get();
			accountView.setBalance(accountView.getBalance().subtract(event.getAmount()));
			accountViewRepository.save(accountView);
		}
	}
	
	@EventHandler
    public void on(AccountCredited event) {
		Optional<AccountView> accountViewOptional = accountViewRepository.findById(event.getAccountId());
		if (accountViewOptional.isPresent()) {
			AccountView accountView = accountViewOptional.get();
			accountView.setBalance(accountView.getBalance().add(event.getAmount()));
			accountViewRepository.save(accountView);
		}
    }

	@EventHandler
	public void on(ToAccountCredited event) {
		Optional<AccountView> accountViewOptional = accountViewRepository.findById(event.getAccountId());
		if (accountViewOptional.isPresent()) {
			AccountView accountView = accountViewOptional.get();
			accountView.setBalance(accountView.getBalance().add(event.getAmount()));
			accountViewRepository.save(accountView);
		}
	}
}