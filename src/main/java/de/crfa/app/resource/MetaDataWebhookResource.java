package de.crfa.app.resource;

import de.crfa.app.MetaDataService;
import de.crfa.app.resource.domain.GithubWebhookRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;

@Controller("/webhook")
@Slf4j
public class MetaDataWebhookResource {

    private final MetaDataService metaDataService;

    public MetaDataWebhookResource(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @Post(uri = "/crfa-offchain-registry-updated")
    public HttpResponse<String> updateRegistry(@Body GithubWebhookRequest githubWebhookRequest) throws GitAPIException {
        log.info("Received githubWebhookRequest:{}", githubWebhookRequest);

        if ("refs/heads/main".equalsIgnoreCase(githubWebhookRequest.getRef())) {
            log.info("update was for main branch, refreshing metadata service.");
            metaDataService.cloneRepo();
        }

        return HttpResponse.ok();
    }
}
