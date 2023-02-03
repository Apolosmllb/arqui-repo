package pe.edu.upc.banking.accounts.command.application.dtos.response;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class OpenAccountResponse {
	private String accountId;
	private String number;
	private BigDecimal balance;
	private BigDecimal overdraftLimit;
	private String customerId;
}