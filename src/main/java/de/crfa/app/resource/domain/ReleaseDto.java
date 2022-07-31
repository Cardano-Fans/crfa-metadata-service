package de.crfa.app.resource.domain;

import io.micronaut.core.annotation.Nullable;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseDto {

    String releaseName;

    float releaseNumber;

    String description;

    List<ScriptMappingDto> scripts;

    @Nullable
    AuditDto audit;

    @Nullable
    ContractDto contract;

}
