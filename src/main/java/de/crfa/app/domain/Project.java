package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Nullable;
import lombok.*;

import java.util.List;

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

    @Nullable
    List<Audit> audits;

    @Nullable
    List<Contract> contracts;

    List<String> features; // TODO strongly typed?

    @Nullable // for now nullable but in the future will be mandatory field
    List<Release> releases;

}
