package pe.edu.upc.banking.accounts.command.application.validators;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.accounts.command.application.dtos.request.EditAccountRequest;
import pe.edu.upc.banking.accounts.command.domain.Account;
import pe.edu.upc.banking.common.application.Notification;

@Component
public class EditAccountValidator {
    private final EventSourcingRepository<Account> accountRepository;

    public EditAccountValidator(EventSourcingRepository<Account> accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Notification validate(EditAccountRequest editAccountRequest)
    {
        Notification notification = new Notification();
        String accountId = editAccountRequest.getAccountId().trim();
        if (accountId.isEmpty()) {
            notification.addError("Account id is required");
            return notification;
        }
        loadAccountAggregate(accountId);
        return notification;
    }

    private void loadAccountAggregate(String accountId) {
        UnitOfWork unitOfWork = null;
        try {
            unitOfWork = DefaultUnitOfWork.startAndGet(null);
            accountRepository.load(accountId);
            unitOfWork.commit();
        } catch (AggregateNotFoundException ex) {
            unitOfWork.commit();
            throw ex;
        } catch(Exception ex) {
            if (unitOfWork != null) unitOfWork.rollback();
        }
    }
}