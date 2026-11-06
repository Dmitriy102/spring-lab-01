package kz.iitu.springlab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Set;

@SpringBootApplication
public class SpringLab01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringLab01Application.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase(AppUserRepository repository) {
		return args -> {
			if (repository.findByUsername("user").isEmpty()) {
				AppUser user = new AppUser();
				user.setUsername("user");
				// Твой личный хеш для user123
				user.setPasswordHash("$2b$12$6P7NemuXGYSr6h6DFWcaiucPNInmL9QzqrX6FtgrIbZXfxFY.s8KG");
				user.setEnabled(true);
				user.setRoles(Set.of("USER"));
				repository.save(user);
			}

			if (repository.findByUsername("admin").isEmpty()) {
				AppUser admin = new AppUser();
				admin.setUsername("admin");
				// Твой личный хеш для admin123
				admin.setPasswordHash("$2b$12$7LRoevLvkqUz/JVKS7S9rOmuhkB0uroitwSCCxBWLjncBt1wnukp2");
				admin.setEnabled(true);
				admin.setRoles(Set.of("USER", "ADMIN"));
				repository.save(admin);
			}
		};
	}
}