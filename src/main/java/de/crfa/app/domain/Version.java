package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Nullable;
import lombok.*;

@Builder
@Getter
@ToString
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Version {

    int version;

    String scriptHash;

    @Nullable
    String contractAddress;

    @Nullable
    String mintPolicyID;

    @Nullable
    String includeScriptBalanceFromAsset;

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
