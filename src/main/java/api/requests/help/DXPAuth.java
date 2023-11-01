package api.requests.help;

import aws.SecretsManager;
import utils.EnvironmentsNew;

public class DXPAuth {
    public static DXPAuth INSTANCE = null;
    private static final String env = EnvironmentsNew.getCurrentEnv().getEnvironmentName();
    private final String username = this.retrieveDxpUsername();
    private final String password = this.retrieveDxpPassword();

    private DXPAuth() {
    }

    private String retrieveDxpUsername() {
        String[] usernamePaths = new String[]{"eis-dxp-gateway/username", String.format("/%s/eis-dxp-gateway/username", env)};
        return SecretsManager.getSecretWithAlternatives(usernamePaths);
    }

    private String retrieveDxpPassword() {
        String[] passwordPaths = new String[]{"eis-dxp-gateway/password", String.format("/%s/eis-dxp-gateway/password", env)};
        return SecretsManager.getSecretWithAlternatives(passwordPaths);
    }

    public static DXPAuth getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DXPAuth();
        }

        return INSTANCE;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
