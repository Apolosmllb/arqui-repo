package pe.edu.upc.banking.accounts.command.application.dtos.response;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class EditAccountResponse {
	private String accountId;
	private BigDecimal overdraftLimit;
}