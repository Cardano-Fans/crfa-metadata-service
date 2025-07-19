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

    @Builder.Default
    Optional<String> contractAddress = Optional.empty();

    @Builder.Default
    Optional<String> mintPolicyID = Optional.empty();

    @Builder.Default
    Optional<String> includeScriptBalanceFromAsset = Optional.empty();

    @Builder.Default
    Optional<Integer> plutusVersion = Optional.empty();

    // for now optional but we want to make it mandatory
    @Builder.Default
    Optional<String> fullScriptHash = Optional.empty();

}
