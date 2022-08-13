package de.crfa.app.resource.domain;

import de.crfa.app.domain.Purpose;
import io.micronaut.core.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

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
    Collection<String> tokenHolders; // this makes any sense only in case of fetchAndTurnTokenHoldersIntoContractAddresses set to true and mintPolicyId being present

    @Nullable
    String scriptHash;

    @Deprecated
    Boolean hasContract;

    @Deprecated
    Boolean hasAudit;

    @Nullable
    @Deprecated
    AuditDto audit;

    @Nullable
    @Deprecated
    ContractDto contract;

    public static String discoverId(Purpose purpose, String mintPolicyID, String scriptHash) {
        if (purpose == Purpose.MINT) {
            return mintPolicyID;
        }

        return scriptHash;
    }

}
