package compasso.estagio.grupo.projeto5.Telas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TelasApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelasApplication.class, args);
	}

}
