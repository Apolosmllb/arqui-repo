package pe.edu.upc.banking.accounts.command.application.validators;

import org.springframework.stereotype.Component;
import pe.edu.upc.banking.accounts.command.application.dtos.request.OpenAccountRequest;
import pe.edu.upc.banking.accounts.command.infra.AccountNumber;
import pe.edu.upc.banking.accounts.command.infra.AccountNumberRepository;
import pe.edu.upc.banking.common.application.Notification;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class OpenAccountValidator {
    private final AccountNumberRepository accountNumberRepository;

    public OpenAccountValidator(AccountNumberRepository accountNumberRepository) {
        this.accountNumberRepository = accountNumberRepository;
    }

    public Notification validate(OpenAccountRequest openAccountRequest)
    {
        Notification notification = new Notification();
        String number = openAccountRequest.getNumber().trim();
        if (number.isEmpty()) {
            notification.addError("Account number is required");
        }
        String customerId = openAccountRequest.getCustomerId().trim();
        if (customerId.isEmpty()) {
            notification.addError("Account customerId is required");
        }
        BigDecimal overdraftLimit = openAccountRequest.getOverdraftLimit();
        if (overdraftLimit.doubleValue() < 0) {
            notification.addError("Account overdraftLimit must be greater or equal than zero");
        }
        if (notification.hasErrors()) {
            return notification;
        }
        Optional<AccountNumber> accountNumberOptional = accountNumberRepository.findById(number);
        if (accountNumberOptional.isPresent()) {
            notification.addError("Account number is taken");
        }
        return notification;
    }
}