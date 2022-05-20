package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonIgnoreProperties
public class ProjectDto {

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
