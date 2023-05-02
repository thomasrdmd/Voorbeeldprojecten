package nl.han.ica.veiligveilen.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "nl.han.ica.veiligveilen.server")
@EnableJpaRepositories(basePackages = "nl.han.ica.veiligveilen.server")
@EntityScan(basePackages = "nl.han.ica.veiligveilen.server")
public class Server {
	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}
}
