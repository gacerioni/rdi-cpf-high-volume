package io.platformengineer.rdicpfhighvolume.address;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    // Endpoint to create an address for a person
    @PostMapping("/{cpf}")
    public ResponseEntity<Address> createAddress(@PathVariable Long cpf, @RequestBody Address address) {
        try {
            Address newAddress = addressService.createAddress(cpf, address);
            return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
