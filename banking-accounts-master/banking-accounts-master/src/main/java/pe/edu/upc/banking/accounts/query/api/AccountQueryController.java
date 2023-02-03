package pe.edu.upc.banking.accounts.query.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.banking.accounts.query.dtos.Customer;
import pe.edu.upc.banking.accounts.query.projections.AccountLogView;
import pe.edu.upc.banking.accounts.query.projections.AccountLogViewRepository;
import pe.edu.upc.banking.accounts.query.projections.AccountView;
import pe.edu.upc.banking.accounts.query.projections.AccountViewRepository;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts")
public class AccountQueryController {
    private final AccountViewRepository accountViewRepository;

    private final AccountLogViewRepository accountLogViewRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ReactiveCircuitBreakerFactory circuitBreakerFactory;

    public AccountQueryController(AccountViewRepository accountViewRepository, AccountLogViewRepository accountLogViewRepository) {
        this.accountViewRepository = accountViewRepository;
        this.accountLogViewRepository = accountLogViewRepository;
    }

    @GetMapping("")
    @Operation(summary = "Get all accounts")
    public ResponseEntity<List<AccountView>> getAll() {
        try {
            List<AccountView> acounts = accountViewRepository.findAll().stream().map(account -> {
                Customer customer = getCustomer(account).block();
                account.setCustomerDni(customer.getDni());
                account.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
                account.setCustomerDni(customer.getDni());
                account.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
                return account;
            }).collect(Collectors.toList());
            return new ResponseEntity<List<AccountView>>(acounts, HttpStatus.OK);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get account by id")
    public ResponseEntity<AccountView> getById(@PathVariable("id") String id) {
        try {
            Optional<AccountView> accountViewOptional = accountViewRepository.findById(id);
            if (accountViewOptional.isPresent()) {
                AccountView accountView = accountViewOptional.get();
                Customer customer = getCustomer(accountView).block();
                accountView.setCustomerDni(customer.getDni());
                accountView.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
                return new ResponseEntity<AccountView>(accountView, HttpStatus.OK);
            }
            return new ResponseEntity("NOT_FOUND", HttpStatus.NOT_FOUND);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Mono<Customer> getCustomer(AccountView account) {
        ReactiveCircuitBreaker reactiveCircuitBreaker = circuitBreakerFactory.create("clientCircuitBreaker");
        String customerEndpoint = "http://localhost:8080/customers/id/" + account.getCustomerId();
        Mono<Customer> customer = reactiveCircuitBreaker.run(webClientBuilder.build().get()
                .uri(customerEndpoint)
                .retrieve()
                .bodyToMono(Customer.class)
            , throwable -> getDefaultCustomer());
        return customer;
    }

    private Mono<Customer> getDefaultCustomer() {
        System.out.println("Fallback method called");
        return Mono.just(new Customer("none","none","none", "none", "ACTIVE", null, null));
    }

    @GetMapping(path = "/number/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get account by number")
    public ResponseEntity<AccountView> getByDocument(@PathVariable("number") String number) {
        try {
            Optional<AccountView> accountViewOptional = accountViewRepository.getByNumber(number);
            if (accountViewOptional.isPresent()) {
                return new ResponseEntity<AccountView>(accountViewOptional.get(), HttpStatus.OK);
            }
            return new ResponseEntity("NOT_FOUND", HttpStatus.NOT_FOUND);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/log/{id}")
    @Operation(summary = "Get account log")
    public ResponseEntity<List<AccountLogView>> getLogById(@PathVariable("id") String id) {
        try {
            List<AccountLogView> accountLog = accountLogViewRepository.getLogById(id);
            return new ResponseEntity<List<AccountLogView>>(accountLog, HttpStatus.OK);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}