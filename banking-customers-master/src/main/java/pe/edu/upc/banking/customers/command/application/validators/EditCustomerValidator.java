package pe.edu.upc.banking.customers.command.application.validators;

import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Component;
import pe.edu.upc.banking.common.application.Notification;
import pe.edu.upc.banking.customers.command.application.dtos.request.EditCustomerRequest;
import pe.edu.upc.banking.customers.command.domain.Customer;
import pe.edu.upc.banking.customers.command.infra.CustomerDni;
import pe.edu.upc.banking.customers.command.infra.CustomerDniRepository;
import java.util.Optional;

@Component
public class EditCustomerValidator {
    private final CustomerDniRepository customerDniRepository;
    private final Repository<Customer> customerRepository;

    public EditCustomerValidator(CustomerDniRepository customerDniRepository, Repository<Customer> customerRepository) {
        this.customerDniRepository = customerDniRepository;
        this.customerRepository = customerRepository;
    }

    public Notification validate(EditCustomerRequest editCustomerRequest)
    {
        Notification notification = new Notification();
        String customerId = editCustomerRequest.getCustomerId().trim();
        if (customerId.isEmpty()) {
            notification.addError("Customer id is required");
        }
        loadCustomerAggregate(customerId);
        String firstName = editCustomerRequest.getFirstName().trim();
        if (firstName.isEmpty()) {
            notification.addError("Customer firstname is required");
        }
        String lastName = editCustomerRequest.getLastName().trim();
        if (lastName.isEmpty()) {
            notification.addError("Customer lastname is required");
        }
        String dni = editCustomerRequest.getDni().trim();
        if (dni.isEmpty()) {
            notification.addError("Customer dni is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }
        Optional<CustomerDni> customerDni = customerDniRepository.getByDniForDistinctCustomerId(dni, customerId);
        if (customerDni.isPresent()) {
            notification.addError("Customer dni is taken");
        }
        return notification;
    }

    private void loadCustomerAggregate(String customerId) {
        UnitOfWork unitOfWork = null;
        try {
            unitOfWork = DefaultUnitOfWork.startAndGet(null);
            customerRepository.load(customerId);
            unitOfWork.commit();
        } catch (AggregateNotFoundException ex) {
            unitOfWork.commit();
            throw ex;
        } catch(Exception ex) {
            if (unitOfWork != null) unitOfWork.rollback();
        }
    }
}