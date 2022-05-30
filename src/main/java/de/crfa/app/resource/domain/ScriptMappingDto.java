package de.crfa.app.resource.domain;

import de.crfa.app.domain.Purpose;
import io.micronaut.core.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ScriptMappingDto {

    String id;

    String name;

    int version;

    Purpose purpose;

    @Nullable
    String contractAddress;

    @Nullable
    String mintPolicyID;

    @Nullable
    String scriptHash;

    @Nullable
    Boolean hasContract;

    @Nullable
    Boolean hasAudit;

    public static String discoverId(Purpose purpose, String mintPolicyID, String scriptHash) {
        if (purpose == Purpose.MINT) {
            return mintPolicyID;
        }

        return scriptHash;
    }

}
