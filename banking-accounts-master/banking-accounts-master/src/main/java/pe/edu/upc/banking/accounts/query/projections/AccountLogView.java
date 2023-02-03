package pe.edu.upc.banking.accounts.query.projections;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class AccountLogView {
	@Id
	@GeneratedValue
	private Long accountLogId;
	@Column(length=36)
    private String accountId;
	@Column(length=36)
    private String customerId;
    private BigDecimal balance;
	private BigDecimal overdraftLimit;
	private Instant createdAt;

    public AccountLogView() {
    }
    
    public AccountLogView(String accountId, String customerId, BigDecimal balance, BigDecimal overdraftLimit, Instant createdAt) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
        this.createdAt = createdAt;
    }

	public AccountLogView(AccountLogView accountLogView) {
		this.accountId = accountLogView.getAccountId();
		this.customerId = accountLogView.getCustomerId();
		this.balance = accountLogView.getBalance();
		this.overdraftLimit = accountLogView.getOverdraftLimit();
		this.createdAt = accountLogView.getCreatedAt();
	}

	public String getAccountId() { return accountId; }

	public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getCustomerId() { return customerId; }

	public void setCustomerId(String customerId) { this.customerId = customerId; }

	public BigDecimal getBalance() { return balance; }

	public void setBalance(BigDecimal balance) { this.balance = balance; }

	public BigDecimal getOverdraftLimit() { return overdraftLimit; }

	public void setOverdraftLimit(BigDecimal overdraftLimit) { this.overdraftLimit = overdraftLimit; }

	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

	public Instant getCreatedAt() { return createdAt; }
}