package com.cos.mixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MixinApplication {

	public static void main(String[] args) {
		SpringApplication.run(MixinApplication.class, args);
	}

}
