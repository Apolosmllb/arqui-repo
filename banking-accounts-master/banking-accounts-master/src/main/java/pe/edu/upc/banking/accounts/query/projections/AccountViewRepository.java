package pe.edu.upc.banking.accounts.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountViewRepository extends JpaRepository<AccountView, String> {
    Optional<AccountView> getByNumber(String number);
}