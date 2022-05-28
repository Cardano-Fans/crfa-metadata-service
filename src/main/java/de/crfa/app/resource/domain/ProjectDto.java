package de.crfa.app.resource.domain;

import de.crfa.app.domain.ProjectType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class ProjectDto {

    String id;
    String name;
    String category;
    String subCategory;

    String url;
    String twitter;
    String icon;

    ProjectType type;

    List<ProjectVersionDto> versions;

}
