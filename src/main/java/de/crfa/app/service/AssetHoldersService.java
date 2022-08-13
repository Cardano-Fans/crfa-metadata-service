package de.crfa.app.service;

import de.crfa.app.client.crfa_db_sync_api.CRFADbSyncApi;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class AssetHoldersService {

    @Inject
    private CRFADbSyncApi crfaDbSyncApi;

    public Set<String> loadAssetHolders(String policyId) {
        return crfaDbSyncApi.assetHolders(policyId)
                .keySet().stream()
                .filter(addr -> addr.startsWith("addr1"))
                .collect(Collectors.toSet());
    }

}
