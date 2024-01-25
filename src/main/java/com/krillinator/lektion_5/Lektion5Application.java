package com.krillinator.lektion_5;

import com.krillinator.lektion_5.models.user.Roles;
import com.krillinator.lektion_5.models.user.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.krillinator.lektion_5.models.user.Roles.ADMIN;
import static com.krillinator.lektion_5.models.user.Roles.values;

@SpringBootApplication
public class Lektion5Application {

	public static void main(String[] args) {
		SpringApplication.run(Lektion5Application.class, args);

		// System.out.println(ADMIN.splitPermissions());
		// System.out.println(ADMIN.getAuthorities());

		UserEntity userEntity = new UserEntity();
		userEntity.setRole(ADMIN);

		System.out.println("DEBUGGING " + ADMIN.name());
		System.out.println("DEBUGGING #2 " + userEntity.getAuthorities());
		System.out.println("DEBUGGING #3 " + userEntity.getRole().splitPermissions());

		System.out.println((int) TimeUnit.DAYS.toSeconds(21));

	}

}
