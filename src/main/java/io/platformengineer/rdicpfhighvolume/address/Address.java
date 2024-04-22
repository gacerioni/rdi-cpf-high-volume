package io.platformengineer.rdicpfhighvolume.address;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.platformengineer.rdicpfhighvolume.person.Person;
import jakarta.persistence.*;
import lombok.Data;

// Address entity for MySQL
@Data
@Entity(name = "Address")
@Table(name = "address")
public class Address {

    @Id
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

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JsonBackReference
    private Person person;

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

