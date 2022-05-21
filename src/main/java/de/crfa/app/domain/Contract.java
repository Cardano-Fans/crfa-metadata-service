package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Nullable;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties
public class Contract {

    String contractId;

    @Nullable
    Boolean openSource;

    @Nullable
    String contractLink;

    public Contract() {
    }

}
