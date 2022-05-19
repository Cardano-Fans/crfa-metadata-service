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

    String projectName;
    String link;
    String twitter;

    String category;
    String subCategory;

    List<Script> scripts;
    @Nullable
    List<Audit> audits;

    public Project() {
    }

}
