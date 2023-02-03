package pe.edu.upc.banking.accounts.command.application.dtos.request;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

public class EditAccountRequest {
	private @Setter @Getter String accountId;
	private @Getter BigDecimal overdraftLimit;
}