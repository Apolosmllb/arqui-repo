package pe.edu.upc.banking.customers.command.application.dtos.response;

import lombok.Value;

@Value
public class EditCustomerResponse {
	private String customerId;
	private String firstName;
	private String lastName;
	private String dni;
}