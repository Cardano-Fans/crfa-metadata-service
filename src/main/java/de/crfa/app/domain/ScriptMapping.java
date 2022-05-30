package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Getter
@ToString
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class ScriptMapping {

    String id;

    int version;

}
