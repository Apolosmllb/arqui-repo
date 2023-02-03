package pe.edu.upc.banking.customers.command.application.dtos.request;

import lombok.Getter;
import lombok.Setter;

public class EditCustomerRequest {
	private @Setter @Getter String customerId;
	private @Getter String firstName;
	private @Getter String lastName;
	private @Getter String dni;
}