package de.crfa.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.crfa.app.domain.Project;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.io.IOUtils;
import io.micronaut.core.util.StringUtils;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Slf4j
public class MetaDataService {

    private final File tmpFile;

    private File repoPath;

    private static final String SUBPATH = "crfa-offchain-data-registry";

    @Inject
    private ObjectMapper objectMapper;

    @Value("${crfa-offchain-repo-branch:}")
    private String crfaOffchainRepoBranch;

    public MetaDataService() {
        this.tmpFile = new File(System.getProperty("java.io.tmpdir"));
    }

    @EventListener
    public void onStartup(ServerStartupEvent event) throws GitAPIException {
        cloneRepo();
    }

    @Scheduled(fixedDelay = "24h", initialDelay = "1m")
    public void cloneRepo() throws GitAPIException {
        log.info("Cloning repo, tmpPath:{}", this.tmpFile);
        this.repoPath = new File(tmpFile, SUBPATH + System.currentTimeMillis());
        log.info("new repo path:{}", repoPath);

        val repo = Git.cloneRepository()
                .setURI("https://github.com/Cardano-Fans/" + SUBPATH)
                .setDirectory(this.repoPath);

        if (StringUtils.hasText(crfaOffchainRepoBranch)) {
            repo.setBranch(crfaOffchainRepoBranch);
        }

        repo.call();
    }

    @Scheduled(fixedDelay = "144h", initialDelay = "144h")
    public void cleanTemp() throws IOException, GitAPIException {
        log.info("Cleaning temp...");

        if (tmpFile != null) {
            FileUtils.deleteDirectory(new File(tmpFile, SUBPATH));
        }

        cloneRepo();
    }

    public List<Project> loadProjects() throws IOException {
        val projects = new ArrayList<Project>();

        if (repoPath != null) {
            Files.list(Paths.get(repoPath.toString(), "dApps"))
                    .forEach(path -> {
                        try (val fr = new BufferedReader(new FileReader(path.toFile()))){
                            val fileContent = IOUtils.readText(fr);
                            val project = objectMapper.readValue(fileContent, Project.class);

                            projects.add(project);
                        } catch (IOException e) {
                            log.error("Error reading file", e);
                        }
                    });
        }

        return List.copyOf(projects);
    }

}
