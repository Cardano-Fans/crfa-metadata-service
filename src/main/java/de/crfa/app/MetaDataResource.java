package de.crfa.app;

import de.crfa.app.domain.Project;
import de.crfa.app.domain.ProjectDto;
import de.crfa.app.domain.Script;
import de.crfa.app.domain.Version;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Controller("/metadata")
@Slf4j
public class MetaDataResource {

    private MetaDataService metaDataService;

    public MetaDataResource(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @Get(uri = "/by-script-hash/{hash}", produces = "application/json")
    public Optional<ProjectDto> byScriptHash(@PathVariable String hash) throws IOException {
        var projects = metaDataService.loadProjects();

        for (Project p : projects) {
            for (Script s : p.getScripts()) {
                for (Version v : s.getVersions()) {
                    if (hash.equals(v.getScriptHash()) || hash.equals(v.getMintPolicyID())) {
                        var dto = ProjectDto
                                .builder()
                                .scriptHash(v.getScriptHash())
                                .projectName(p.getProjectName())
                                .scriptName(s.getName())
                                .version(v.getVersion())
                                .url(p.getLink())
                                .contractAddress(v.getContractAddress())
                                .mintPolicyID(v.getMintPolicyID())
                                .icon(String.format("https://t2.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=%s&size=64", p.getLink()))
                                .twitter(p.getTwitter())
                                .build();

                        return Optional.of(dto);
                    }
                }
            }
        }

        return Optional.empty();
    }
}
