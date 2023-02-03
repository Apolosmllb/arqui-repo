package pe.edu.upc.banking.customers.command.application.dtos.request;

import lombok.Value;

@Value
public class RegisterCustomerRequest {
	private String firstName;
	private String lastName;
	private String dni;
}