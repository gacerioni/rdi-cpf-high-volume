package io.platformengineer.rdicpfhighvolume.address;

import io.platformengineer.rdicpfhighvolume.person.Person;
import jakarta.persistence.*;
import lombok.Data;

// Address entity for MySQL
@Data
@Entity(name = "Address")
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "street", nullable = false, columnDefinition = "TEXT")
    private String street;

    @Column(name = "city", nullable = false, columnDefinition = "TEXT")
    private String city;

    @Column(name = "state", nullable = false, columnDefinition = "TEXT")
    private String state;

    @Column(name = "postal_code", nullable = false, columnDefinition = "TEXT")
    private String postalCode;

    @Column(name = "country", nullable = false, columnDefinition = "TEXT")
    private String country;

    public Address() {
    }

    public Address(String street, String city, String state, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }
}

