package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Optional;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contract {

    String contractId;

    Optional<Boolean> openSource;

    Optional<String> contractLink;

    public Contract() {
    }

}
