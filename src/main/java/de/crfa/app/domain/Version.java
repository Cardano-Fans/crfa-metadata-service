package de.crfa.app.domain;

import lombok.*;

@Builder
@Getter
@ToString
@Setter
@AllArgsConstructor
public class Version {

    int version;
    String scriptHash;
    String contractAddress;
    String mintPolicyID;

    public Version() {
    }

}
