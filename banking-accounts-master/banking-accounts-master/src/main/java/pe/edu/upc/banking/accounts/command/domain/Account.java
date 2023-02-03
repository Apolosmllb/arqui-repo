package pe.edu.upc.banking.accounts.command.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.spring.stereotype.Aggregate;
import pe.edu.upc.banking.accounts.contracts.commands.*;
import pe.edu.upc.banking.accounts.contracts.events.*;
import java.math.BigDecimal;
import java.time.Instant;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Account {
	@AggregateIdentifier
    private String accountId;
    private String number;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;
    private String customerId;
    
    public Account() {
    }
    
    @CommandHandler
    public Account(OpenAccount openAccount) {
        Instant now = Instant.now();
        apply(new AccountOpened(openAccount.getAccountId(), openAccount.getNumber(), openAccount.getOverdraftLimit(), openAccount.getCustomerId(), now));
    }
    
    @CommandHandler
    public void handle(EditAccount editAccount) {
        Instant now = Instant.now();
        apply(new AccountEdited(editAccount.getAccountId(), editAccount.getOverdraftLimit(), now));
    }

    @CommandHandler
    public void credit(CreditAccount creditAccount) throws Exception {
        Instant now = Instant.now();
        try {
            //accountRepository.load(creditAccount.getAccountId());
            apply(new AccountCredited(accountId, creditAccount.getTransactionId(), creditAccount.getAmount(), now));
        } catch (AggregateNotFoundException ex) {
            apply(new AccountNotFound(creditAccount.getAccountId(), creditAccount.getTransactionId(), now));
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @CommandHandler
    public void debit(DebitAccount debitAccount) throws Exception {
        Instant now = Instant.now();
        try {
            //accountRepository.load(debitAccount.getAccountId());
            double currentBalance = balance.add(overdraftLimit).doubleValue();
            if (debitAccount.getAmount().doubleValue() > currentBalance) {
                apply(new AccountDebitFailedDueNoFunds(accountId, debitAccount.getTransactionId(), now));
                throw new Exception("Account Debit Failed Due No Funds");
            }
            apply(new AccountDebited(accountId, debitAccount.getTransactionId(), debitAccount.getAmount(), now));
        } catch (AggregateNotFoundException ex) {
            apply(new AccountNotFound(debitAccount.getAccountId(), debitAccount.getTransactionId(), now));
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @CommandHandler
    public void creditToAccount(CreditToAccount creditToAccount) throws Exception {
        Instant now = Instant.now();
        try {
            //accountRepository.load(creditToAccount.getAccountId());
            apply(new ToAccountCredited(creditToAccount.getAccountId(), creditToAccount.getTransactionId(), creditToAccount.getAmount(), now));
        } catch (AggregateNotFoundException ex) {
            apply(new ToAccountNotFound(creditToAccount.getAccountId(), creditToAccount.getTransactionId(), now));
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @CommandHandler
    public void debitFromAccount(DebitFromAccount debitFromAccount) throws Exception {
        Instant now = Instant.now();
        try {
            //accountRepository.load(debitFromAccount.getAccountId());
            double currentBalance = balance.add(overdraftLimit).doubleValue();
            if (debitFromAccount.getAmount().doubleValue() > currentBalance) {
                apply(new FromAccountDebitFailedDueNoFunds(accountId, debitFromAccount.getTransactionId(), now));
                throw new Exception("From Account Debit Failed Due No Funds");
            }
            apply(new FromAccountDebited(accountId, debitFromAccount.getTransactionId(), debitFromAccount.getAmount(), now));
        } catch (AggregateNotFoundException ex) {
            apply(new FromAccountNotFound(debitFromAccount.getAccountId(), debitFromAccount.getTransactionId(), now));
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    @EventSourcingHandler
    protected void on(AccountOpened event) {
        this.accountId = event.getAccountId();
        this.number = event.getNumber();
        this.balance = BigDecimal.ZERO;
        this.overdraftLimit = event.getOverdraftLimit();
        this.customerId = event.getCustomerId();
    }
    
    @EventSourcingHandler
    protected void on(AccountEdited event) {
        this.overdraftLimit = event.getOverdraftLimit();
    }

    @EventSourcingHandler
    public void on(AccountCredited event) {
        balance = balance.add(event.getAmount());
    }

    @EventSourcingHandler
    public void on(AccountDebited event) {
        balance = balance.subtract(event.getAmount());
    }

    @EventSourcingHandler
    public void on(FromAccountDebited event) {
        balance = balance.subtract(event.getAmount());
    }

    @EventSourcingHandler
    public void on(ToAccountCredited event) {
        balance = balance.add(event.getAmount());
    }
}