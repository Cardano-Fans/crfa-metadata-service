package de.crfa.app.resource.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.crfa.app.domain.Purpose;
import io.micronaut.core.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@Builder
@Getter
@ToString
@JsonIgnoreProperties
@Deprecated
public class DappScriptDto {

    String id;

    String category;

    String subCategory;

    String projectName;

    String scriptName;

    Integer version;

    String url;

    String icon;

    @Nullable
    String contractAddress;

    String scriptHash;

    Purpose purpose;

    @Nullable
    String mintPolicyID;

    @Nullable
    Collection<String> tokenHolders; // this makes any sense only in case of fetchAndTurnTokenHoldersIntoContractAddresses set to true and mintPolicyId being present

    String twitter;

}
