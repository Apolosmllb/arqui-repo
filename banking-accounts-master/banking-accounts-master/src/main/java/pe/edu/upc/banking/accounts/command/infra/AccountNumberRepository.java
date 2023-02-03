package pe.edu.upc.banking.accounts.command.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountNumberRepository extends JpaRepository<AccountNumber, String> {
}