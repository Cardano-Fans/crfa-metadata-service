package de.crfa.app.client.crfa_db_sync_api;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;

import java.math.BigInteger;
import java.util.Map;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client("http://api.cardano.fans:8081")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = "application/json")
public interface CRFADbSyncApi {

    @Get("/top_addresses_with_asset/{policyId}?top=3000")
    Map<String, BigInteger> assetHolders(String policyId);

}
