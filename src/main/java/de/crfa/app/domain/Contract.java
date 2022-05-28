package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Nullable;
import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties
@NoArgsConstructor
public class Contract {

    String contractId;

    @Nullable
    Boolean openSource;

    @Nullable
    String contractLink;

}
