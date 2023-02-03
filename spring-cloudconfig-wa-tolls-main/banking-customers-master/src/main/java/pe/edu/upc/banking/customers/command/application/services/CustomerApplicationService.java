package pe.edu.upc.banking.customers.command.application.services;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.common.application.Notification;
import pe.edu.upc.banking.common.application.Result;
import pe.edu.upc.banking.common.application.ResultType;
import pe.edu.upc.banking.customers.command.application.dtos.request.EditCustomerRequest;
import pe.edu.upc.banking.customers.command.application.dtos.request.RegisterCustomerRequest;
import pe.edu.upc.banking.customers.command.application.dtos.response.EditCustomerResponse;
import pe.edu.upc.banking.customers.command.application.dtos.response.RegisterCustomerResponse;
import pe.edu.upc.banking.customers.command.application.validators.EditCustomerValidator;
import pe.edu.upc.banking.customers.command.application.validators.RegisterCustomerValidator;
import pe.edu.upc.banking.customers.command.infra.CustomerDniRepository;
import pe.edu.upc.banking.customers.contracts.commands.EditCustomer;
import pe.edu.upc.banking.customers.contracts.commands.RegisterCustomer;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class CustomerApplicationService {
    private final RegisterCustomerValidator registerCustomerValidator;
    private final EditCustomerValidator editCustomerValidator;
    private final CommandGateway commandGateway;
    private final CustomerDniRepository customerDniRepository;

    public CustomerApplicationService(RegisterCustomerValidator registerCustomerValidator, EditCustomerValidator editCustomerValidator, CommandGateway commandGateway, CustomerDniRepository customerDniRepository) {
        this.registerCustomerValidator = registerCustomerValidator;
        this.editCustomerValidator = editCustomerValidator;
        this.commandGateway = commandGateway;
        this.customerDniRepository = customerDniRepository;
    }

    public Result<RegisterCustomerResponse, Notification> register(RegisterCustomerRequest registerCustomerRequest) throws Exception {
        Notification notification = this.registerCustomerValidator.validate(registerCustomerRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        String customerId = UUID.randomUUID().toString();
        RegisterCustomer registerCustomer = new RegisterCustomer(
            customerId,
            registerCustomerRequest.getFirstName().trim(),
            registerCustomerRequest.getLastName().trim(),
            registerCustomerRequest.getDni().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(registerCustomer);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        RegisterCustomerResponse registerCustomerResponseDto = new RegisterCustomerResponse(
            registerCustomer.getCustomerId(),
            registerCustomer.getFirstName(),
            registerCustomer.getLastName(),
            registerCustomer.getDni()
        );
        return Result.success(registerCustomerResponseDto);
    }

    public Result<EditCustomerResponse, Notification> edit(EditCustomerRequest editCustomerRequest) throws Exception {
        Notification notification = this.editCustomerValidator.validate(editCustomerRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        EditCustomer editCustomer = new EditCustomer(
            editCustomerRequest.getCustomerId().trim(),
            editCustomerRequest.getFirstName().trim(),
            editCustomerRequest.getLastName().trim(),
            editCustomerRequest.getDni().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(editCustomer);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        EditCustomerResponse editCustomerResponse = new EditCustomerResponse(
            editCustomer.getCustomerId(),
            editCustomer.getFirstName(),
            editCustomer.getLastName(),
            editCustomer.getDni()
        );
        return Result.success(editCustomerResponse);
    }
}