package de.crfa.app.resource.domain;

import io.micronaut.core.annotation.Nullable;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseDto {

    String releaseName;

    float releaseNumber;

    Optional<String> description;

    List<ScriptMappingDto> scripts;

    Optional<AuditDto> audit;

    Optional<ContractDto> contract;

}
