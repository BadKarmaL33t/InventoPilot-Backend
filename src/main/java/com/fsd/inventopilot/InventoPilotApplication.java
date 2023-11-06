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

		if (null == testingAdminAcc) {
			User adm1n = new User();
			adm1n.setUsername("Adm1n");
			adm1n.setFirstname("Ad");
			adm1n.setLastname("min");
			adm1n.setEmail("adm1n.dummy@nothing.com");
			adm1n.setRole(Role.ADMIN);
			adm1n.setPassword(new BCryptPasswordEncoder().encode(System.getenv("adm1nPw")));
			userRepository.save(adm1n);
		}
		if (null == testingSuperAcc) {
			User Sup3r = new User();
			Sup3r.setUsername("Adm1n");
			Sup3r.setFirstname("Ad");
			Sup3r.setLastname("min");
			Sup3r.setEmail("Sup3r.dummy@nothing.com");
			Sup3r.setRole(Role.ADMIN);
			Sup3r.setPassword(new BCryptPasswordEncoder().encode(System.getenv("Sup3rPw")));
			userRepository.save(Sup3r);
		}
		if (null == testingUserAcc) {
			User U5er = new User();
			U5er.setUsername("Adm1n");
			U5er.setFirstname("Ad");
			U5er.setLastname("min");
			U5er.setEmail("U5er.dummy@nothing.com");
			U5er.setRole(Role.ADMIN);
			U5er.setPassword(new BCryptPasswordEncoder().encode(System.getenv("U5erPw")));
			userRepository.save(U5er);
		}
	}
}
