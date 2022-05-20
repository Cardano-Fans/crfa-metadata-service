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
@JsonIgnoreProperties
public class Project {

    String projectId;

    String projectName;
    String link;
    String twitter;

    String category;
    String subCategory;

    List<Script> scripts;
    @Nullable
    List<Audit> audits;
    List<Audit> contracts;
    List<String> features; // TODO strongly typed?

    public Project() {
    }

}
