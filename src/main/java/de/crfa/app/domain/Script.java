package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.crfa.app.utils.IdGenerator;
import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties
@NoArgsConstructor
public class Script {

    String name;
    Purpose purpose;

    List<Version> versions;

    public String getNameWithFallback() {
        return name != null ? name : "DEFAULT";
    }

    // TODO this is a bit clunky, this id should be in json metadata...
    public String getId() {
        return IdGenerator.generateId(getNameWithFallback());
    }

}
