package pe.edu.upc.banking.customers.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerViewRepository extends JpaRepository<CustomerView, String> {
	Optional<CustomerView> getByDni(String dni);

	@Query(value = "SELECT * FROM customer_view WHERE customer_id <> :customerId AND dni = :dni", nativeQuery = true)
	Optional<CustomerView> getByCustomerIdAndDni(String customerId, String dni);
}