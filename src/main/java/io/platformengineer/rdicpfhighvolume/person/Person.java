package io.platformengineer.rdicpfhighvolume.person;

import io.platformengineer.rdicpfhighvolume.address.Address;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Person")
@Table(name = "person")
public class Person {

    @Id
    private Long cpf; // Make CPF the primary key

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;
    @Column(name = "age", nullable = false, columnDefinition = "INTEGER")
    private Integer age;
    @Column(name = "zip_code", nullable = false, columnDefinition = "TEXT")
    private String zipCode;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;


    public Person() {
    }

    public Person(String firstName, String lastName, String email, Integer age, Long cpf, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.cpf = cpf;
        this.zipCode = zipCode;
    }

}
