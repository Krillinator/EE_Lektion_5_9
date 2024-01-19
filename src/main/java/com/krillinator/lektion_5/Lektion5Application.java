package com.krillinator.lektion_5;

import com.krillinator.lektion_5.models.user.Roles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.krillinator.lektion_5.models.user.Roles.ADMIN;

@SpringBootApplication
public class Lektion5Application {

	public static void main(String[] args) {
		SpringApplication.run(Lektion5Application.class, args);

		System.out.println(Roles.ADMIN.splitPermissions());
		System.out.println(ADMIN.getAuthorities());
	}

}
