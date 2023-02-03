package pe.edu.upc.banking.customers.command.application.validators;

import org.springframework.stereotype.Component;
import pe.edu.upc.banking.common.application.Notification;
import pe.edu.upc.banking.customers.command.application.dtos.request.RegisterCustomerRequest;
import pe.edu.upc.banking.customers.command.infra.CustomerDni;
import pe.edu.upc.banking.customers.command.infra.CustomerDniRepository;
import java.util.Optional;

@Component
public class RegisterCustomerValidator {
    private final CustomerDniRepository customerDniRepository;

    public RegisterCustomerValidator(CustomerDniRepository customerDniRepository) {
        this.customerDniRepository = customerDniRepository;
    }

    public Notification validate(RegisterCustomerRequest registerCustomerRequest)
    {
        Notification notification = new Notification();
        String firstName = registerCustomerRequest.getFirstName().trim();
        if (firstName.isEmpty()) {
            notification.addError("Customer firstname is required");
        }
        String lastName = registerCustomerRequest.getLastName().trim();
        if (lastName.isEmpty()) {
            notification.addError("Customer lastname is required");
        }
        String dni = registerCustomerRequest.getDni().trim();
        if (dni.isEmpty()) {
            notification.addError("Customer dni is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }
        Optional<CustomerDni> customerDniOptional = customerDniRepository.findById(dni);
        if (customerDniOptional.isPresent()) {
            notification.addError("Customer dni is taken");
        }
        return notification;
    }
}