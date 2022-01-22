package de.crfa.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.crfa.app.domain.Project;
import io.micronaut.core.io.IOUtils;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
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
    private final ObjectMapper objectMapper;

    private static String SUBPATH = "crfa-offchain-data-registry";

    public MetaDataService() throws GitAPIException {
        this.objectMapper = new ObjectMapper();
        this.tmpFile = new File(System.getProperty("java.io.tmpdir"));
        cloneRepo();
    }

    @Scheduled(fixedDelay = "1h", initialDelay = "1h")
    public void cloneRepo() throws GitAPIException {
        log.info("Cloning repo, tmpPath:{}", this.tmpFile);
        this.repoPath = new File(tmpFile, SUBPATH + System.currentTimeMillis());
        log.info("new repo path:{}", repoPath);

        Git.cloneRepository()
                .setURI("https://github.com/Cardano-Fans/" + SUBPATH)
                .setDirectory(this.repoPath)
                .call();
    }

    @Scheduled(fixedDelay = "24h", initialDelay = "24h")
    public void cleanTemp() throws IOException, GitAPIException {
        log.info("Cleaning temp...");

        if (tmpFile != null) {
            FileUtils.deleteDirectory(new File(tmpFile, SUBPATH));
        }

        cloneRepo();
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
