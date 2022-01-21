package de.crfa.app.domain;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Script {

    String name;
    String purpose;

    List<Version> versions;

    public Script() {
    }

}
