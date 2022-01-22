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
    Purpose purpose;

    List<Version> versions;

    public Script() {
    }

}
