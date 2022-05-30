package de.crfa.app.resource;

import de.crfa.app.MetaDataService;
import de.crfa.app.domain.*;
import de.crfa.app.resource.domain.DappScriptDto;
import de.crfa.app.resource.domain.ProjectDto;
import de.crfa.app.resource.domain.ReleaseDto;
import de.crfa.app.resource.domain.ScriptMappingDto;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
                                    .id(p.getId())
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
    public List<ProjectDto> listAllDApps() throws Exception {
        var projects = metaDataService.loadProjects();

        var projectDTOs = new ArrayList<ProjectDto>();

        for (var p : projects) {
            if (p.getReleases() == null) {
                continue;
            }

            var scriptsGroupedById = p.getScripts()
                    .stream()
                    .collect(groupingBy(Script::getId));

            var notUniqueScriptIds = scriptsGroupedById.entrySet().stream().anyMatch(stringListEntry -> stringListEntry.getValue().size() >  1);

            if (notUniqueScriptIds) {
                throw new Exception("Not unique script names!");
            }

            var scriptIdToScriptMap = new HashMap<String, Script>();

            for (var entry : scriptsGroupedById.entrySet()) {
                var val = entry.getValue().get(0);

                scriptIdToScriptMap.put(entry.getKey(), val);
            }

            var projectDTO = ProjectDto.builder()
                    .id(p.getId())
                    .name(p.getProjectName())
                    .twitter(p.getTwitter())
                    .url(p.getLink())
                    .category(p.getCategory())
                    .subCategory(p.getSubCategory())
                    .icon(generateIcon(p.getLink()))
                    .type(findProjectType(p))
                    .releases(convertReleases(p.getReleases(), scriptIdToScriptMap))
                    .build();

            projectDTOs.add(projectDTO);
        }

        return projectDTOs;
    }

    private static List<ReleaseDto> convertReleases(List<Release> releases, Map<String, Script> scriptsGroupedById) {
        return releases.stream().map(release -> {
            return ReleaseDto.builder()
                    .description(release.getDescription())
                    .releaseNumber(release.getReleaseNumber())
                    .releaseName(release.getReleaseName())
                    .scripts(convertScripts(release.getScripts(), scriptsGroupedById))
                    .build();
        }).collect(Collectors.toList());
    }

    private static List<ScriptMappingDto> convertScripts(List<ScriptMapping> scriptMappings, Map<String, Script> scriptsGroupedById) {

        var scriptMappingDtos = new ArrayList<ScriptMappingDto>();

        for (var scriptMapping : scriptMappings) {
            var id = scriptMapping.getId();
            var version = scriptMapping.getVersion();

            var maybeFoundScript = Optional.ofNullable(scriptsGroupedById.get(id));

            if (maybeFoundScript.isEmpty()) {
                log.warn("Unable to find script for id:{}", id);
                continue;
            }

            var script = maybeFoundScript.get();

            var maybeFoundVersion = findVersion(script.getVersions(), version);

            if (maybeFoundVersion.isEmpty()) {
                log.warn("Unable to find version script id:" + id + ",version:" + version);
                continue;
            }

            var versionVersion = maybeFoundVersion.get();

            var scriptMappingDto = ScriptMappingDto
                    .builder()
                    .scriptHash(versionVersion.getScriptHash())
                    .contractAddress(versionVersion.getContractAddress())
                    .name(script.getNameWithFallback())
                    .purpose(script.getPurpose())
                    .mintPolicyID(versionVersion.getMintPolicyID())
                    .hasAudit(versionVersion.getAuditId() != null) // this usually means there has been at least manual or automatic audit
                    .hasContract(versionVersion.getContractId() != null) // the fact that it has contract doesn't mean it is open sourced
                    .version(scriptMapping.getVersion())
                    .id(scriptMapping.getId())
                    .build();

            scriptMappingDtos.add(scriptMappingDto);
        }

        return scriptMappingDtos;
    }

    private static Optional<Version> findVersion(List<Version> versions, int version) {
        return versions.stream().filter(versionVersion -> version == versionVersion.getVersion()).findFirst();
    }

    // TODO fake...
    // simply latest script versions for now from all scripts
    private static ReleaseDto createFakeProjectVersion1(Project p) {
        var projectVersionDto = new ReleaseDto();
        projectVersionDto.setReleaseName("LAST");
        projectVersionDto.setReleaseNumber(0);

        // this is of course not acceptable, this needs to be manually verified as coherent dapp -> list<scripts> + versions mapping
        projectVersionDto.setDescription("Latest version for each script belonging to the dApp");

        var scriptMappingDtos = new ArrayList<ScriptMappingDto>();

        var groupedById = p.getScripts()
                .stream()
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
    public Optional<ProjectDto> dappById(String id) throws Exception {
        return listAllDApps().stream().filter(projectDto -> projectDto.getId().equalsIgnoreCase(id)).findFirst();
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
        var mintOnly = p.getScripts().stream().allMatch(script -> script.getPurpose() == Purpose.MINT);
        var spendOnly = p.getScripts().stream().allMatch(script -> script.getPurpose() == Purpose.SPEND);

        if (mintOnly) {
            return ProjectType.MINT_ONLY;
        }

        if (spendOnly) {
            return ProjectType.SPEND_ONLY;
        }

        return ProjectType.MINT_AND_SPEND;
    }

}
