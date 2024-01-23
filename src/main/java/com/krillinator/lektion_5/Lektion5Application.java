package com.krillinator.lektion_5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.krillinator.lektion_5.models.user.Roles.ADMIN;

@SpringBootApplication
public class Lektion5Application {

	public static void main(String[] args) {
		SpringApplication.run(Lektion5Application.class, args);

		System.out.println(ADMIN.splitPermissions());
		System.out.println(ADMIN.getAuthorities());
	}

}
