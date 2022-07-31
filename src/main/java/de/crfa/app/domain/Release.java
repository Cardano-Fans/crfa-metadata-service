package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Nullable;
import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class Release {

    float releaseNumber;

    String releaseName;

    @Nullable
    String description;

    List<ScriptMapping> scripts;

    @Nullable
    String auditId;

    @Nullable
    String contractId;

}
