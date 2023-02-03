package pe.edu.upc.banking.accounts.command.application.services;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.accounts.command.application.dtos.request.EditAccountRequest;
import pe.edu.upc.banking.accounts.command.application.dtos.request.OpenAccountRequest;
import pe.edu.upc.banking.accounts.command.application.dtos.response.EditAccountResponse;
import pe.edu.upc.banking.accounts.command.application.dtos.response.OpenAccountResponse;
import pe.edu.upc.banking.accounts.command.application.validators.EditAccountValidator;
import pe.edu.upc.banking.accounts.command.application.validators.OpenAccountValidator;
import pe.edu.upc.banking.accounts.command.infra.AccountNumberRepository;
import pe.edu.upc.banking.accounts.contracts.commands.EditAccount;
import pe.edu.upc.banking.accounts.contracts.commands.OpenAccount;
import pe.edu.upc.banking.common.application.Notification;
import pe.edu.upc.banking.common.application.Result;
import pe.edu.upc.banking.common.application.ResultType;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class AccountApplicationService {
    private final OpenAccountValidator openAccountValidator;
    private final EditAccountValidator editAccountValidator;
    private final CommandGateway commandGateway;
    private final AccountNumberRepository accountNumberRepository;

    public AccountApplicationService(OpenAccountValidator openAccountValidator, EditAccountValidator editAccountValidator, CommandGateway commandGateway, AccountNumberRepository accountNumberRepository) {
        this.openAccountValidator = openAccountValidator;
        this.editAccountValidator = editAccountValidator;
        this.commandGateway = commandGateway;
        this.accountNumberRepository = accountNumberRepository;
    }

    public Result<OpenAccountResponse, Notification> open(OpenAccountRequest openAccountRequest) throws Exception {
        Notification notification = this.openAccountValidator.validate(openAccountRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        String accountId = UUID.randomUUID().toString();
        OpenAccount openAccount = new OpenAccount(
            accountId,
            openAccountRequest.getNumber().trim(),
            openAccountRequest.getOverdraftLimit(),
            openAccountRequest.getCustomerId().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(openAccount);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        OpenAccountResponse openAccountResponse = new OpenAccountResponse(
            openAccount.getCustomerId(),
            openAccount.getNumber(),
            BigDecimal.ZERO,
            openAccount.getOverdraftLimit(),
            openAccount.getCustomerId()
        );
        return Result.success(openAccountResponse);
    }

    public Result<EditAccountResponse, Notification> edit(EditAccountRequest editAccountRequest) throws Exception {
        Notification notification = this.editAccountValidator.validate(editAccountRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        EditAccount editAccount = new EditAccount(
            editAccountRequest.getAccountId().trim(),
            editAccountRequest.getOverdraftLimit()
        );
        CompletableFuture<Object> future = commandGateway.send(editAccount);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        EditAccountResponse editAccountResponse = new EditAccountResponse(
            editAccount.getAccountId(),
            editAccount.getOverdraftLimit()
        );
        return Result.success(editAccountResponse);
    }
}