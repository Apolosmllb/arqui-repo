package pe.edu.upc.banking.accounts.command.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.banking.accounts.command.application.dtos.request.EditAccountRequest;
import pe.edu.upc.banking.accounts.command.application.dtos.request.OpenAccountRequest;
import pe.edu.upc.banking.accounts.command.application.dtos.response.EditAccountResponse;
import pe.edu.upc.banking.accounts.command.application.dtos.response.OpenAccountResponse;
import pe.edu.upc.banking.accounts.command.application.services.AccountApplicationService;
import pe.edu.upc.banking.accounts.command.infra.AccountNumberRepository;
import pe.edu.upc.banking.common.api.ApiController;
import pe.edu.upc.banking.common.application.Notification;
import pe.edu.upc.banking.common.application.Result;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts")
public class AccountCommandController {
	private final AccountApplicationService accountApplicationService;
	private final CommandGateway commandGateway;
	private final AccountNumberRepository accountNumberRepository;

	public AccountCommandController(AccountApplicationService accountApplicationService, CommandGateway commandGateway, AccountNumberRepository accountNumberRepository) {
		this.accountApplicationService = accountApplicationService;
		this.commandGateway = commandGateway;
		this.accountNumberRepository = accountNumberRepository;
	}
	
	@PostMapping("")
	public ResponseEntity<Object> open(@Validated @RequestBody OpenAccountRequest openAccountRequest) {
		try {
			Result<OpenAccountResponse, Notification> result = accountApplicationService.open(openAccountRequest);
			if (result.isSuccess()) {
				return ApiController.created(result.getSuccess());
			}
			return ApiController.error(result.getFailure().getErrors());
		} catch(Exception e) {
			return ApiController.serverError();
		}
	}
	
	@PutMapping("/{accountId}")
	public ResponseEntity<Object> edit(@PathVariable("accountId") String accountId, @RequestBody EditAccountRequest editAccountRequest) {
		try {
			editAccountRequest.setAccountId(accountId);
			Result<EditAccountResponse, Notification> result = accountApplicationService.edit(editAccountRequest);
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