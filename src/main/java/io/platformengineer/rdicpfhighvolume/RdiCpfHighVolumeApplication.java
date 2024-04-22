package io.platformengineer.rdicpfhighvolume;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import io.platformengineer.rdicpfhighvolume.person.Person;
import io.platformengineer.rdicpfhighvolume.person.PersonRedis;
import io.platformengineer.rdicpfhighvolume.person.PersonRedisRepository;
import io.platformengineer.rdicpfhighvolume.person.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.github.javafaker.Faker;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Locale;

@SpringBootApplication(scanBasePackages = "io.platformengineer.rdicpfhighvolume")
@EnableScheduling
@EnableTransactionManagement
@EnableRedisDocumentRepositories(basePackages = "io.platformengineer.rdicpfhighvolume.*")
public class RdiCpfHighVolumeApplication {

	private static final Logger log = LoggerFactory.getLogger(RdiCpfHighVolumeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RdiCpfHighVolumeApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(
			PersonService personService) {
		return args -> {
			Faker faker = new Faker(new Locale("pt-BR"));

			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
			Integer age = faker.number().numberBetween(17, 55);
			Long cpf = faker.number().randomNumber(11, true);
			String zipCode = "04128000";

			Person person = new Person(
					firstName,
					lastName,
					email,
					age,
					cpf,
					zipCode);

			personService.save(person);

		};
	}

	/*
	@Bean
	CommandLineRunner loadTestData(PersonRedisRepository repo) {
		return args -> {
			repo.deleteAll();

			Faker faker = new Faker(new Locale("pt-BR"));

			// Generate and save 10 fake PersonRedis entities
			for (int i = 0; i < 5; i++) {
				String firstName = faker.name().firstName();
				String lastName = faker.name().lastName();
				int age = faker.number().numberBetween(18, 30); // Random age between 18 and 30
				String email = faker.internet().emailAddress();
				Long cpf = faker.number().randomNumber(11, true);
				String zipCode = faker.address().zipCode();

				PersonRedis personRedis = PersonRedis.of(cpf, firstName, lastName, age, email, zipCode);
				repo.save(personRedis);
			}
		};
	}
	*/
}
