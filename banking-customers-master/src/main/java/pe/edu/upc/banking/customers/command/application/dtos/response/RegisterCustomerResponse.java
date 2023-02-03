package pe.edu.upc.banking.customers.command.application.dtos.response;

import lombok.Value;

@Value
public class RegisterCustomerResponse {
	private String customerId;
	private String firstName;
	private String lastName;
	private String dni;
}