package de.crfa.app.resource.domain;

import de.crfa.app.domain.Purpose;
import io.micronaut.core.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class DappScriptDto {

    String projectId;

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
    String twitter;

}
