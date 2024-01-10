package io.mosip.totpbinderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class TotpBinderService {

	public static void main(String[] args) {
		SpringApplication.run(TotpBinderService.class, args);
	}

}
