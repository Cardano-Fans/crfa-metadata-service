package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    //@Nullable // temporary nullable
    String id;

    String name;

    Purpose purpose;

    List<ScriptVersion> versions;

    public String getNameWithFallback() {
        return name != null ? name : "";
    }

}
