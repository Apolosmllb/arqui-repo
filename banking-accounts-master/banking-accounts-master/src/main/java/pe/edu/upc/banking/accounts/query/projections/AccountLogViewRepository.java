package pe.edu.upc.banking.accounts.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountLogViewRepository extends JpaRepository<AccountLogView, String> {
	AccountLogView findOneByAccountId(String accountId);
	@Query(value = "SELECT * FROM account_log_view WHERE account_log_id = (SELECT MAX(account_log_id) FROM account_log_view WHERE account_id = :accountId)", nativeQuery = true)
	Optional<AccountLogView> getLastByAccountId(String accountId);

	@Query(value = "SELECT * FROM account_log_view WHERE account_id = :accountId ORDER BY created_at", nativeQuery = true)
	List<AccountLogView> getLogById(String accountId);
}