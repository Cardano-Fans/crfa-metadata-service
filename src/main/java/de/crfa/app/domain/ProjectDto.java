package de.crfa.app.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProjectDto {

    String projectName;
    String scriptName;
    int version;
    String url;
    String icon;
    String contractAddress;
    String scriptHash;
    Purpose purpose;
    String mintPolicyID;
    String twitter;

}
