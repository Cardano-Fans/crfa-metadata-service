package de.crfa.app.resource.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.crfa.app.domain.Purpose;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonIgnoreProperties
public class DappScriptDto {

    String id;

    String category;
    String subCategory;
    String projectName;
    String scriptName;
    Integer version;
    String url;
    String icon;
    String contractAddress;
    String scriptHash;
    Purpose purpose;
    String mintPolicyID;
    String twitter;

}
