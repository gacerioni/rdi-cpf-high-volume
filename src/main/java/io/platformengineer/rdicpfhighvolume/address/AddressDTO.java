package io.platformengineer.rdicpfhighvolume.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;


}
