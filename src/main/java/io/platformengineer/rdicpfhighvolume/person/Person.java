package io.platformengineer.rdicpfhighvolume.person;

import com.fasterxml.jackson.annotation.*;
import io.platformengineer.rdicpfhighvolume.address.Address;
import io.platformengineer.rdicpfhighvolume.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @JsonManagedReference
    private Address address;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Vehicle> vehicles = new HashSet<>();


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
