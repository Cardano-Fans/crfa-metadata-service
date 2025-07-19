package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Release {

    float releaseNumber;

    String releaseName;

    @Builder.Default
    Optional<String> description = Optional.empty();

    List<ScriptMapping> scripts;

    @Builder.Default
    Optional<String> auditId  = Optional.empty();

    @Builder.Default
    Optional<String> contractId = Optional.empty();

}
