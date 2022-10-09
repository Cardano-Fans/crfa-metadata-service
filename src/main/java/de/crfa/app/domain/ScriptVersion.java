package de.crfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Optional;

@Builder
@Getter
@ToString
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ScriptVersion {

    int version;

    String scriptHash;

    Optional<String> contractAddress = Optional.empty();

    Optional<String> mintPolicyID = Optional.empty();

    Optional<String> includeScriptBalanceFromAsset = Optional.empty();

    Optional<Integer> plutusVersion = Optional.empty();

}
