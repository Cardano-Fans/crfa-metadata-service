package de.crfa.app.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GithubWebhookRequest {

    String ref;

}
