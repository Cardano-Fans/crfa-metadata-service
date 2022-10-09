package de.crfa.app.resource.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Builder
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractDto {

    Optional<Boolean> openSource;

    Optional<String> contractLink;

}
