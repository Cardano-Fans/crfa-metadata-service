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

    @Nullable
    String mintPolicyID;

    @Nullable
    // auditId is deprecated on script / version level
    @Deprecated
    String auditId;

    @Nullable
    @Deprecated
    // contractId is deprecated on script / version level
    String contractId;

    public Version() {
    }

}
