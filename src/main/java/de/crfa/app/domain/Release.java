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

    Optional<String> description = Optional.empty();;

    List<ScriptMapping> scripts;

    Optional<String> auditId  = Optional.empty();;

    Optional<String> contractId = Optional.empty();

}
