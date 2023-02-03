package pe.edu.upc.banking.accounts.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class AccountView {
	@Id
	@Column(length=36)
    @Getter
	private String accountId;
	@Column(length=36)
	@Getter
	private String customerId;
	@Getter @Setter
	private String customerName;
	@Getter @Setter
	private String customerDni;
	@Getter
	@Column(length=25)
	private String number;
	@Getter @Setter
	private BigDecimal balance;
	@Getter @Setter private BigDecimal overdraftLimit;
	private Instant createdAt;
	@Column(nullable = true)
	@Getter @Setter private Instant updatedAt;

    public AccountView() {
    }
    
    public AccountView(String accountId, String customerId, String number, BigDecimal balance, BigDecimal overdraftLimit, Instant createdAt) {
        this.accountId = accountId;
		this.customerId = customerId;
		this.number = number;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
		this.createdAt = createdAt;
    }
}