package de.crfa.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.crfa.app.domain.Project;
import io.micronaut.core.io.IOUtils;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
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

    private File repoPath;
    private final ObjectMapper objectMapper;

    public MetaDataService() throws GitAPIException, IOException {
        cloneRepo();

        this.objectMapper = new ObjectMapper();
    }

    @Scheduled(fixedDelay = "1h", initialDelay = "1h")
    public void cloneRepo() throws IOException, GitAPIException {
        log.info("Cloning repo...");
        var tmpPath = System.getProperty("java.io.tmpdir");

        var tmpFile = new File(tmpPath);

        this.repoPath = new File(tmpFile, "crfa-offchain-data-registry" + System.currentTimeMillis());

        log.info("Closed repo path:{}", repoPath);

        var git = Git.cloneRepository()
                .setURI("https://github.com/Cardano-Fans/crfa-offchain-data-registry")
                .setDirectory(this.repoPath)
                .call();
    }

    public List<Project> loadProjects() throws IOException {
        var projects = new ArrayList<Project>();

        if (repoPath != null) {
            Files.list(Paths.get(repoPath.toString(), "dApps"))
                    .forEach(path -> {
                        try {
                            var fileContent = IOUtils.readText(new BufferedReader(new FileReader(path.toFile())));
                            var project = objectMapper.readValue(fileContent, Project.class);

                            projects.add(project);
                        } catch (IOException e) {
                            log.error("Error reading file", e);
                        }
                    });
        }

        return List.copyOf(projects);
    }

}
