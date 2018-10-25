package uk.co.nhs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "uk.co.nhs.repository")
@SpringBootApplication
public class AppointLettersApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointLettersApplication.class, args);
	}
}
