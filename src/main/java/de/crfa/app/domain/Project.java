package de.crfa.app.domain;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Project {

    String projectName;
    String link;
    String twitter;

    List<Script> scripts;

    public Project() {
    }

}
