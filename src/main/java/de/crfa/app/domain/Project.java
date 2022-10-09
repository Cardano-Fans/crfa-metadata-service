package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Project {

    String id;

    String projectName;
    String link;
    String twitter;

    String category;
    String subCategory;

    List<Script> scripts;

    Optional<List<Audit>> audits;

    Optional<List<Contract>> contracts;

    List<String> features; // TODO strongly typed?

    List<Release> releases;

}
