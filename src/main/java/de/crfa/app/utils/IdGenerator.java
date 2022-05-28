package de.crfa.app.utils;

public class IdGenerator {

    // TODO this is total fake and needs to be replaced by proper ids in json documents in https://github.com/Cardano-Fans/crfa-offchain-data-registry
    public static String generateId(String name) {
        return name.toUpperCase()
                .replaceAll(" ", "_").
                replaceAll("\\.", "_");
    }

}
