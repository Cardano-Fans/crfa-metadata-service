package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVersion {

    int version;

    String description;

    List<ScriptMapping> scripts;

}
