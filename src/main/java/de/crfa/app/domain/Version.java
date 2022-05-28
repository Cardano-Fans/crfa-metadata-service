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
public class Version {

    Integer version;
    String scriptHash;
    String contractAddress;

    @Nullable
    String mintPolicyID;

    @Nullable
    String auditId;

    @Nullable
    String contractId;

}
