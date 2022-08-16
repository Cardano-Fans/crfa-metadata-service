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
public class Script {

    @Nullable // temporary nullable
    String id;

    String name;

    Purpose purpose;

    List<Version> versions;

    public String getNameWithFallback() {
        return name != null ? name : "";
    }

}
