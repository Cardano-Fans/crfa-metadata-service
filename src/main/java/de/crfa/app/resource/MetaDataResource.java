package de.crfa.app.resource;

import de.crfa.app.MetaDataService;
import de.crfa.app.domain.*;
import de.crfa.app.resource.domain.DappScriptDto;
import de.crfa.app.resource.domain.ProjectDto;
import de.crfa.app.resource.domain.ProjectVersionDto;
import de.crfa.app.resource.domain.ScriptMappingDto;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.crfa.app.utils.IdGenerator.generateId;
import static java.util.stream.Collectors.groupingBy;

@Controller("/metadata")
@Slf4j
public class MetaDataResource {

    private final MetaDataService metaDataService;

    public MetaDataResource(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @Get(uri = "/by-hash/{hash}", produces = "application/json")
    public Optional<DappScriptDto> listDappScriptByHash(@PathVariable String hash) throws IOException {
        var projects = metaDataService.loadProjects();

        for (Project p : projects) {
            for (Script s : p.getScripts()) {
                for (Version v : s.getVersions()) {
                    if (hash.equals(v.getScriptHash()) || hash.equals(v.getMintPolicyID())) {

                            var dto = DappScriptDto
                                    .builder()
                                    .projectId(generateId(p.getProjectName()))
                                    .projectName(p.getProjectName())
                                    .scriptName(s.getNameWithFallback())
                                    .version(v.getVersion())
                                    .url(p.getLink())
                                    .contractAddress(v.getContractAddress())
                                    .mintPolicyID(v.getMintPolicyID())
                                    .purpose(s.getPurpose())
                                    .category(p.getCategory())
                                    .subCategory(p.getSubCategory())
                                    .icon(generateIcon(p.getLink()))
                                    .twitter(p.getTwitter())
                                    .build();

                        return Optional.of(dto);
                    }
                }
            }
        }

        return Optional.empty();
    }

    @Get(uri = "/dapps/list", produces = "application/json")
    public List<ProjectDto> listAllDApps() throws IOException {
        var projects = metaDataService.loadProjects();

        return projects.stream().map(p -> {
            return ProjectDto.builder()
                    .id(generateId(p.getProjectName()))
                    .name(p.getProjectName())
                    .twitter(p.getTwitter())
                    .url(p.getLink())
                    .category(p.getCategory())
                    .subCategory(p.getSubCategory())
                    .icon(generateIcon(p.getLink()))
                    .type(findProjectType(p))
                    .versions(List.of(createFakeProjectVersion1(p)))
                    .build();

        }).collect(Collectors.toList());
    }

    // TODO fake...
    // simply latest script versions for now from all scripts
    private static ProjectVersionDto createFakeProjectVersion1(Project p) {
        var projectVersionDto = new ProjectVersionDto();
        projectVersionDto.setReleaseName("LAST");
        projectVersionDto.setReleaseNumber(0);

        // this is of course not acceptable, this needs to be manually verified as coherent dapp -> list<scripts> + versions mapping
        projectVersionDto.setDescription("Latest version for each script belonging to the dApp");

        var scriptMappingDtos = new ArrayList<ScriptMappingDto>();

        var groupedById = p.getScripts()
                .stream()
                //.filter(script -> script.getPurpose() == SPEND)
                .collect(groupingBy(Script::getId));

        groupedById.forEach((id, scripts) -> {
            scripts.stream().findFirst().ifPresent(s -> {
                findMaxScriptVersionId(s.getVersions()).ifPresent(v -> {
                    scriptMappingDtos.add(ScriptMappingDto
                            .builder()
                            .id(ScriptMappingDto.discoverId(s.getPurpose(), v.getMintPolicyID(), v.getScriptHash()))
                            .name(s.getNameWithFallback())
                            .contractAddress(v.getContractAddress())
                            .mintPolicyID(v.getMintPolicyID())
                            .purpose(s.getPurpose())
                            .version(v.getVersion())
                            .scriptHash(v.getScriptHash())
                            .build()
                    );
                });
            });
        });

        projectVersionDto.setScripts(scriptMappingDtos);

        return projectVersionDto;
    }

    @Get(uri = "/dapps/by-id/{id}", produces = "application/json")
    public Optional<ProjectDto> byId(String id) throws IOException {
        return listAllDApps().stream().filter(projectDto -> projectDto.getId().equals(id)).findFirst();
    }

    private static Optional<Version> findMaxScriptVersionId(List<Version> versions) {
        return versions.stream()
                .max(Comparator.comparing(Version::getVersion))
                .stream()
                .findFirst();
    }

    private static String generateIcon(String link) {
        return String.format("https://t2.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=%s&size=64", link);
    }

    private static ProjectType findProjectType(Project p) {
        var mintOnly = p.getScripts().stream()
                .allMatch(script -> script.getPurpose() == Purpose.MINT);

        return mintOnly ? ProjectType.MINT_ONLY : ProjectType.MINT_AND_SPEND;
    }

}
