package pe.edu.upc.banking.customers.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class CustomerHistoryView {
	@Id @GeneratedValue @Getter @Setter
	private Long customerHistoryId;
	@Column(length=36) @Getter @Setter
    private String customerId;
	@Column(length=50) @Getter @Setter
	private String firstName;
	@Column(length=50) @Getter @Setter
	private String lastName;
	@Column(length=8) @Getter @Setter
	private String dni;
	@Column(length=20) @Getter @Setter
	private String status;
	@Getter @Setter
	private Instant createdAt;

	public CustomerHistoryView() {
	}

	public CustomerHistoryView(String customerId, String firstName, String lastName, String dni, String status, Instant createdAt) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.status = status;
		this.createdAt = createdAt;
    }

	public CustomerHistoryView(CustomerHistoryView customerHistoryView) {
		this.customerId = customerHistoryView.getCustomerId();
		this.firstName = customerHistoryView.getFirstName();
		this.lastName = customerHistoryView.getLastName();
		this.dni = customerHistoryView.getDni();
		this.status = customerHistoryView.getStatus();
		this.createdAt = customerHistoryView.getCreatedAt();
	}
}