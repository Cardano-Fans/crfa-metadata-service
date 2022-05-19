package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Nullable;
import lombok.*;

@Builder
@Getter
@ToString
@Setter
@AllArgsConstructor
@JsonIgnoreProperties
public class Version {

    int version;
    String scriptHash;
    String contractAddress;
    String mintPolicyID;
    @Nullable
    String auditId;

    public Version() {
    }

}
