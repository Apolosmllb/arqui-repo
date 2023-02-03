package pe.edu.upc.banking.accounts.command.application.dtos.request;

import lombok.Getter;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OpenAccountRequest {
	@Getter @NotNull
	private String number;
	private @Getter BigDecimal overdraftLimit;
	@Getter @NotNull
	private String customerId;
}