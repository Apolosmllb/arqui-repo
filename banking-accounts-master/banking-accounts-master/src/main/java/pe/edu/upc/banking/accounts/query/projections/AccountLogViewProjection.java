package pe.edu.upc.banking.accounts.query.projections;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.accounts.contracts.events.*;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AccountLogViewProjection {
	private final AccountLogViewRepository accountLogViewRepository;
	
	public AccountLogViewProjection(AccountLogViewRepository accountLogViewRepository) {
        this.accountLogViewRepository = accountLogViewRepository;
    }
	
	@EventHandler
    public void on(AccountOpened event) {
		AccountLogView accountLogView = new AccountLogView(event.getAccountId(), event.getCustomerId(), BigDecimal.ZERO, event.getOverdraftLimit(), event.getOccurredOn());
		accountLogViewRepository.save(new AccountLogView(accountLogView));
    }
	
	@EventHandler
    public void on(AccountEdited event) {
		Optional<AccountLogView> accountLogViewOptional = accountLogViewRepository.getLastByAccountId(event.getAccountId());
		if (accountLogViewOptional.isPresent()) {
			AccountLogView accountLogView = accountLogViewOptional.get();
			accountLogView = new AccountLogView(accountLogView);
			accountLogView.setOverdraftLimit(event.getOverdraftLimit());
			accountLogView.setCreatedAt(event.getOccurredOn());
			accountLogViewRepository.save(accountLogView);
		}
    }
	
	@EventHandler
    public void on(AccountDebited event) {
		Optional<AccountLogView> accountLogViewOptional = accountLogViewRepository.getLastByAccountId(event.getAccountId());
		if (accountLogViewOptional.isPresent()) {
			AccountLogView accountLogView = accountLogViewOptional.get();
			accountLogView.setBalance(accountLogView.getBalance().subtract(event.getAmount()));
			accountLogViewRepository.save(accountLogView);
		}
    }

	@EventHandler
	public void on(FromAccountDebited event) {
		Optional<AccountLogView> accountLogViewOptional = accountLogViewRepository.getLastByAccountId(event.getAccountId());
		if (accountLogViewOptional.isPresent()) {
			AccountLogView accountLogView = accountLogViewOptional.get();
			accountLogView.setBalance(accountLogView.getBalance().subtract(event.getAmount()));
			accountLogViewRepository.save(accountLogView);
		}
	}
	
	@EventHandler
    public void on(AccountCredited event) {
		Optional<AccountLogView> accountLogViewOptional = accountLogViewRepository.getLastByAccountId(event.getAccountId());
		if (accountLogViewOptional.isPresent()) {
			AccountLogView accountLogView = accountLogViewOptional.get();
			accountLogView.setBalance(accountLogView.getBalance().add(event.getAmount()));
			accountLogViewRepository.save(accountLogView);
		}
    }

	@EventHandler
	public void on(ToAccountCredited event) {
		Optional<AccountLogView> accountLogViewOptional = accountLogViewRepository.getLastByAccountId(event.getAccountId());
		if (accountLogViewOptional.isPresent()) {
			AccountLogView accountLogView = accountLogViewOptional.get();
			accountLogView.setBalance(accountLogView.getBalance().add(event.getAmount()));
			accountLogViewRepository.save(accountLogView);
		}
	}
}