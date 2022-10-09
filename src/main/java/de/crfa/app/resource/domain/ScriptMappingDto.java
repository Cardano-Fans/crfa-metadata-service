package de.crfa.app.resource.domain;

import de.crfa.app.domain.Purpose;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Builder
@Getter
@ToString
public class ScriptMappingDto {

    String id;

    String name;

    int version;

    Purpose purpose;

    String scriptHash;

    Optional<String> contractAddress;

    Optional<String> mintPolicyID;

    int plutusVersion;

    Optional<String> includeScriptBalanceFromAsset;

}
