package io.platformengineer.rdicpfhighvolume.person;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

@RequiredArgsConstructor(staticName = "of")
@Data
@TypeAlias("PersonRedis")
//@Document(prefixes = {"io.platformengineer.rdicpfhighvolume.person.PersonRedis:", "person:"})
//@Document(prefixes = {"gabriel:"})
@Document("person:cpf")
public class PersonRedis {

    @Id
    @NonNull
    private Long cpf; // Use CPF as the ID

    @NonNull
    @Searchable
    private String first_name;

    @NonNull
    @Indexed
    private String last_name;

    @NonNull
    @Indexed
    private Integer age;

    @NonNull
    @Searchable
    private String email;

    @NonNull
    private String zip_code;
}
