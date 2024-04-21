package io.platformengineer.rdicpfhighvolume.person;

import io.platformengineer.rdicpfhighvolume.address.AddressDTO;
import io.platformengineer.rdicpfhighvolume.vehicle.VehicleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreationDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Long cpf;
    private String zipCode;
    private AddressDTO address;
    private List<VehicleDTO> vehicles;

}
