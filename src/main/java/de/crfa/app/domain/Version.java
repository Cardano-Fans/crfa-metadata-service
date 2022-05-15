package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@Setter
@AllArgsConstructor
@JsonIgnoreProperties
public class Version {

    int version;
    String scriptHash;
    String contractAddress;
    String mintPolicyID;
    List<Audit> audits;

    public Version() {
    }

    public boolean wasAudited() {
        return audits != null && !audits.isEmpty();
    }

}
