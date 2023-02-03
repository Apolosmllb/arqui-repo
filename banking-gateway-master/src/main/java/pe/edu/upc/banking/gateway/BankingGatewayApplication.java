package pe.edu.upc.banking.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BankingGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankingGatewayApplication.class, args);
	}
}