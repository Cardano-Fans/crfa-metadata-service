package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
    AuditType auditType;

    public Audit() {
    }

}
