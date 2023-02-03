package pe.edu.upc.banking.accounts.query.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.Instant;

@Value
public class Customer {
	private String customerId;
	private String firstName;
	private String lastName;
	private String dni;
	private String status;
	private Instant createdAt;
	private Instant updatedAt;
}