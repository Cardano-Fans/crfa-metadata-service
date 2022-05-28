package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Nullable;
import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties
@NoArgsConstructor
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
    List<Contract> contracts;
    List<String> features; // TODO strongly typed?

}
