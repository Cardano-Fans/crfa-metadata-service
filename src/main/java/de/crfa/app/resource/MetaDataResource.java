package de.crfa.app.resource;

import de.crfa.app.domain.*;
import de.crfa.app.resource.domain.*;
import de.crfa.app.service.MetaDataService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Controller("/metadata")
@Slf4j
public class MetaDataResource {

    @Inject
    private MetaDataService metaDataService;

    public MetaDataResource() {
    }

    @Get(uri = "/dapps/list", produces = "application/json")
    public List<ProjectDto> listAllDApps() throws Exception {
        val projects = metaDataService.loadProjects();

        val projectDTOs = new ArrayList<ProjectDto>();

        for (val project : projects) {
            if (project.getReleases() == null) {
                log.warn("Unable to find releases section for project id:{}", project.getId());
                continue;
            }

            val scriptsGroupedById = project.getScripts()
                    .stream()
                    .collect(groupingBy(Script::getId));

            val notUniqueScriptIds = scriptsGroupedById.entrySet()
                    .stream()
                    .anyMatch(stringListEntry -> stringListEntry.getValue().size() > 1);

            if (notUniqueScriptIds) {
                val dups = scriptsGroupedById.entrySet().stream().filter(stringListEntry -> stringListEntry.getValue().size() > 1)
                        .toList();

                dups.forEach(entry -> {
                    log.error("Duplicates: {}", entry.getValue());
                });

                throw new Exception("Not unique script ids!");
            }

            val scriptIdToScriptMap = new HashMap<String, Script>();

            for (val entry : scriptsGroupedById.entrySet()) {
                val value = entry.getValue().get(0);

                scriptIdToScriptMap.put(entry.getKey(), value);
            }

            val projectDTO = ProjectDto.builder()
                    .id(project.getId())
                    .name(project.getProjectName())
                    .twitter(project.getTwitter())
                    .url(project.getLink())
                    .category(project.getCategory())
                    .subCategory(project.getSubCategory())
                    .icon(generateIcon(project.getLink()))
                    .type(findProjectType(project))
                    .releases(convertReleases(project, project.getReleases(), scriptIdToScriptMap))
                    .build();

            projectDTOs.add(projectDTO);
        }

        return projectDTOs;
    }

    private List<ReleaseDto> convertReleases(Project p,
                                             List<Release> releases,
                                             Map<String, Script> scriptsGroupedById) {
        return releases.stream().map(release -> {
            return ReleaseDto.builder()
                    .description(release.getDescription())
                    .releaseNumber(release.getReleaseNumber())
                    .releaseName(release.getReleaseName())
                    .scripts(convertScripts(release.getScripts(), scriptsGroupedById))
                    .contract(generateContract(findContractById(p, release.getContractId())))
                    .audit(generateAudit(findByAuditId(p, release.getAuditId())))
                    .build();
        }).toList();
    }

    private List<ScriptMappingDto> convertScripts(
            List<ScriptMapping> scriptMappings,
            Map<String, Script> scriptsGroupedById) {

        val scriptMappingDtos = new ArrayList<ScriptMappingDto>();

        for (val scriptMapping : scriptMappings) {
            val id = scriptMapping.getId();
            val version = scriptMapping.getVersion();

            val maybeFoundScript = Optional.ofNullable(scriptsGroupedById.get(id));

            if (maybeFoundScript.isEmpty()) {
                log.warn("Unable to find script for id:{}", id);
                continue;
            }

            val script = maybeFoundScript.get();

            val maybeFoundVersion = findVersion(script.getVersions(), version);

            if (maybeFoundVersion.isEmpty()) {
                log.warn("Unable to find version script id:" + id + ",version:" + version);
                continue;
            }

            val scriptVersion = maybeFoundVersion.get();

            val mintPolicyID = scriptVersion.getMintPolicyID();

            val scriptMappingDtoBuilder = ScriptMappingDto
                    .builder()
                    .id(scriptMapping.getId())
                    .scriptHash(scriptVersion.getScriptHash())
                    .contractAddress(scriptVersion.getContractAddress())
                    .name(script.getName())
                    .purpose(script.getPurpose())
                    .mintPolicyID(mintPolicyID)
                    .plutusVersion(scriptVersion.getPlutusVersion().orElse(1))
                    .fullScriptHash(scriptVersion.getFullScriptHash())
                    .includeScriptBalanceFromAsset(scriptVersion.getIncludeScriptBalanceFromAsset())
                    .version(scriptMapping.getVersion());

            scriptMappingDtos.add(scriptMappingDtoBuilder.build());
        }

        return scriptMappingDtos;
    }

    private static Optional<AuditDto> generateAudit(Optional<Audit> maybeAudit) {
        return maybeAudit.map(a -> {
            return AuditDto.builder()
                    .auditLink(a.getAuditLink())
                    .auditType(a.getAuditType())
                    .auditor(a.getAuditor())
                    .build();
        });
    }

    private static Optional<ContractDto> generateContract(Optional<Contract> maybeContract) {
        return maybeContract.map(c -> {
           return ContractDto.builder()
                   .contractLink(c.getContractLink())
                   .openSource(c.getOpenSource())
                   .build();
        });
    }

    private static Optional<ScriptVersion> findVersion(List<ScriptVersion> scriptVersions, int version) {
        return scriptVersions.stream().filter(versionScriptVersion -> version == versionScriptVersion.getVersion()).findFirst();
    }

    private static Optional<Contract> findContractById(Project p, Optional<String> contractId) {
        return contractId.flatMap(cId -> {
            return p.getContracts()
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(contract -> contract.getContractId().equalsIgnoreCase(cId))
                    .findFirst();
        });
    }

    private static Optional<Audit> findByAuditId(Project p, Optional<String> auditId) {
        return auditId.flatMap(aId -> {
            return p.getAudits()
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(contract -> contract.getAuditId().equalsIgnoreCase(aId))
                    .findFirst();
        });
    }

    @Get(uri = "/dapps/by-id/{id}", produces = "application/json")
    public Optional<ProjectDto> dappById(String id) throws Exception {
        return listAllDApps().stream().filter(projectDto -> projectDto.getId().equalsIgnoreCase(id)).findFirst();
    }

    private static Optional<ScriptVersion> findMaxScriptVersionId(List<ScriptVersion> scriptVersions) {
        return scriptVersions.stream()
                .max(Comparator.comparing(ScriptVersion::getVersion))
                .stream()
                .findFirst();
    }

    private static String generateIcon(String link) {
        return String.format("https://t2.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=%s&size=128", link);
    }

    private static ProjectType findProjectType(Project p) {
        val mintOnly = p.getScripts().stream().allMatch(script -> script.getPurpose() == Purpose.MINT);
        val spendOnly = p.getScripts().stream().allMatch(script -> script.getPurpose() == Purpose.SPEND);

        if (mintOnly) {
            return ProjectType.MINT_ONLY;
        }

        if (spendOnly) {
            return ProjectType.SPEND_ONLY;
        }

        return ProjectType.MINT_AND_SPEND;
    }

}
