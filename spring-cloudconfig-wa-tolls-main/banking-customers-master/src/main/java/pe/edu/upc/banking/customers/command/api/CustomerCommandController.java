package pe.edu.upc.banking.customers.command.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.banking.common.api.ApiController;
import pe.edu.upc.banking.common.application.Notification;
import pe.edu.upc.banking.common.application.Result;
import pe.edu.upc.banking.customers.command.application.dtos.request.EditCustomerRequest;
import pe.edu.upc.banking.customers.command.application.dtos.request.RegisterCustomerRequest;
import pe.edu.upc.banking.customers.command.application.dtos.response.EditCustomerResponse;
import pe.edu.upc.banking.customers.command.application.dtos.response.RegisterCustomerResponse;
import pe.edu.upc.banking.customers.command.application.services.CustomerApplicationService;
import pe.edu.upc.banking.customers.command.infra.CustomerDniRepository;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customers")
public class CustomerCommandController {
    private final CustomerApplicationService customerApplicationService;
    private final CommandGateway commandGateway;
    private final CustomerDniRepository customerDniRepository;

    public CustomerCommandController(CustomerApplicationService customerApplicationService, CommandGateway commandGateway, CustomerDniRepository customerDniRepository) {
        this.customerApplicationService = customerApplicationService;
        this.commandGateway = commandGateway;
        this.customerDniRepository = customerDniRepository;
    }

    @PostMapping(path= "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@RequestBody RegisterCustomerRequest registerCustomerRequest) {
        try {
            Result<RegisterCustomerResponse, Notification> result = customerApplicationService.register(registerCustomerRequest);
            if (result.isSuccess()) {
                return ApiController.created(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch(Exception e) {
            return ApiController.serverError();
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Object> edit(@PathVariable("customerId") String customerId, @RequestBody EditCustomerRequest editCustomerRequest) {
        try {
            editCustomerRequest.setCustomerId(customerId);
            Result<EditCustomerResponse, Notification> result = customerApplicationService.edit(editCustomerRequest);
            if (result.isSuccess()) {
                return ApiController.ok(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (AggregateNotFoundException exception) {
            return ApiController.notFound();
        } catch(Exception e) {
            return ApiController.serverError();
        }
    }
}