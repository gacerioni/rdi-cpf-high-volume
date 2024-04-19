package io.platformengineer.rdicpfhighvolume.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByLastName(String lastName);

    // Custom query - just to keep in brain
    @Query("SELECT b FROM Person b WHERE b.lastName =:firstname")
    List<Person> findByFirstName(@Param("firstname") String firstname);

    @Query("SELECT b FROM Person b WHERE b.cpf =:cpf")
    List<Person> findByCPF(String cpf);
}
