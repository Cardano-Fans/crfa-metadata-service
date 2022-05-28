package de.crfa.app.resource.domain;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVersionDto {

    String releaseName;

    int releaseNumber;

    String description;

    List<ScriptMappingDto> scripts;

}
