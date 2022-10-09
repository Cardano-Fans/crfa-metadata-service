package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Optional;

@Builder
@Getter
@ToString
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audit {

    String auditId;
    String auditor;
    String auditLink;
    Optional<AuditType> auditType;

    public Audit() {
    }

}
