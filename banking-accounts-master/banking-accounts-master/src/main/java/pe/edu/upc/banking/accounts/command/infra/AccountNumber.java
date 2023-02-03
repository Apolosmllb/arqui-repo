package pe.edu.upc.banking.accounts.command.infra;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountNumber {
    @Id
    @Column(length=25)
    public String number;
    public String customerId;

    public AccountNumber() {
    }

    public AccountNumber(String number, String customerId) {
        this.number = number;
        this.customerId = customerId;
    }
}