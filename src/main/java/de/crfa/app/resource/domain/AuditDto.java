package de.crfa.app.resource.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.crfa.app.domain.AuditType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditDto {

    String auditor;
    String auditLink;
    AuditType auditType;

}
