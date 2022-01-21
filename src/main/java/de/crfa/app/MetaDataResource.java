package de.crfa.app;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/metadata")
public class MetaDataResource {

    private MetaDataService metaDataService;

    public MetaDataResource(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @Get(uri="/by-script-hash", produces="text/plain")
    public List<HashMap<Object, Object>> all() throws IOException {
        var projects = metaDataService.loadProjects();

        var map = new HashMap<>();

        return projects.forEach(project -> {

            map.put("name", project.getProjectName());
            map.put("link", project.getLink());
            map.put("twitter", project.getTwitter());

            project.getScripts().forEach(script -> {
                map.put("")
            });

        }).collect(Collectors.toList());

        return map;

    }

}

// haskell