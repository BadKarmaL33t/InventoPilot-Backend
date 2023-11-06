package com.fsd.inventopilot;

import com.fsd.inventopilot.models.Role;
import com.fsd.inventopilot.models.User;
import com.fsd.inventopilot.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class InventoPilotApplication implements CommandLineRunner {
	private final UserRepository userRepository;

	public InventoPilotApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(InventoPilotApplication.class, args);
	}

	// for testing purposes only! Delete after prototype is approved.
	public void run(String... args) {
		User testingAdminAcc = userRepository.findByRole(Role.ADMIN);
		User testingSuperAcc = userRepository.findByRole(Role.SUPERUSER);
		User testingUserAcc = userRepository.findByRole(Role.USER);

		if (testingAdminAcc == null) {
			User adm1n = new User();
			adm1n.setUsername("Adm1n");
			adm1n.setFirstname("Ad");
			adm1n.setLastname("Min");
			adm1n.setEmail("adm1n.dummy@nothing.com");
			adm1n.setRole(Role.ADMIN);
			adm1n.setPassword(new BCryptPasswordEncoder().encode(System.getenv("adm1nPw")));
			userRepository.save(adm1n);
		}
		if (testingSuperAcc == null) {
			User sup3r = new User();
			sup3r.setUsername("Sup3r");
			sup3r.setFirstname("Su");
			sup3r.setLastname("Per");
			sup3r.setEmail("sup3r.dummy@nothing.com");
			sup3r.setRole(Role.SUPERUSER);
			sup3r.setPassword(new BCryptPasswordEncoder().encode(System.getenv("sup3rPw")));
			userRepository.save(sup3r);
		}
		if (testingUserAcc == null) {
			User u5er = new User();
			u5er.setUsername("U5er");
			u5er.setFirstname("Us");
			u5er.setLastname("Er");
			u5er.setEmail("u5er.dummy@nothing.com");
			u5er.setRole(Role.USER);
			u5er.setPassword(new BCryptPasswordEncoder().encode(System.getenv("u5erPw")));
			userRepository.save(u5er);
		}
	}
}
