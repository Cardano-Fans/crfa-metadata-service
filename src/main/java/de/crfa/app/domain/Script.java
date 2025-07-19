package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Script {

    //@Nullable // temporary nullable
    String id;

    @Builder.Default
    Optional<String> name = Optional.empty();

    Purpose purpose;

    List<ScriptVersion> versions;

}
