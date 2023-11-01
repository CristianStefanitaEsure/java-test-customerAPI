package utils;


public enum EnvironmentsNew {
    DEV("dev", "https://ngdev-api-eks.escloud.co.uk/dev/",
            "https://eissuite-integration-dxp-app-%s.escloud.co.uk/dxp-api/",
            "https://dxp-gateway-%s.escloud.co.uk/",
            "https://eissuite-integration-lookups-app-%s.escloud.co.uk/",
            "https://api%s.digitaltesting.co.uk/"),
    DEV07("dev", "https://ngdev-api-fullsize-eks.escloud.co.uk/full03/",
            "https://eissuite-integration-dxp-app-%s.escloud.co.uk/dxp-api/",
            "https://dxp-gateway-%s.escloud.co.uk/",
            "https://eissuite-integration-lookups-app-%s.escloud.co.uk/",
            "https://api%s.digitaltesting.co.uk/"),
    PERF("perf", "https://ngdev-api-perf-eks.escloud.co.uk/perf/",
            "https://eissuite-integration-dxp-app-%s.escloud.co.uk/dxp-api/",
            "https://dxp-gateway-%s.escloud.co.uk/",
            "https://eissuite-integration-lookups-app-%s.escloud.co.uk/",
            "https://api%s.digitaltesting.co.uk/"),
    STAGING("staging", "https://staging-api-eks.escloud.co.uk/staging/",
            "https://eissuite-integration-dxp-app-staging.escloud.co.uk/dxp-api/",
            "https://dxp-gateway-staging.escloud.co.uk/",
            "https://eissuite-integration-lookups-app-staging.escloud.co.uk/",
            "https://apistaging.digitaltesting.co.uk/");

    private static EnvironmentsNew CURRENT_ENV;
    private static String EIS_ENV;
    private final String environmentName;
    private final String esureBaseUrl;
    private final String dxpV12BaseUrl;
    private final String dxpV20BaseUrl;
    private final String openLLookupsBaseUrl;
    private final String apiGWBaseUrl;

    private EnvironmentsNew(String envName, String esureBaseUrl, String dxpV12BaseUrl, String dxpV20BaseUrl, String openLBaseUrl, String apiGWBaseUrl) {
        this.environmentName = envName;
        this.esureBaseUrl = esureBaseUrl;
        this.dxpV12BaseUrl = dxpV12BaseUrl;
        this.dxpV20BaseUrl = dxpV20BaseUrl;
        this.openLLookupsBaseUrl = openLBaseUrl;
        this.apiGWBaseUrl = apiGWBaseUrl;
    }

    public static void setup(String eisEnv) {
        EIS_ENV = eisEnv;
        String env = System.getProperty("env");
        if (CURRENT_ENV == null) {
            if (env != null) {
                try {
                    CURRENT_ENV = valueOf(env.toUpperCase());
                } catch (Error var3) {
                    throw new RuntimeException("Unable to find environment details for: " + env + "\nPlease check your parameters and try again.\nYou may need to add the details to java-test-common");
                }
            } else {
                if ("app06".equals(eisEnv)) {
                    CURRENT_ENV = DEV;
                } else if ("app07".equals(eisEnv)) {
                    CURRENT_ENV = DEV07;
                }
            }
        }

    }

    public static void DEBUG_ONLY_overrideDefaultEnvironment(String eisEnv, String env) {
        EIS_ENV = eisEnv;

        try {
            CURRENT_ENV = valueOf(env.toUpperCase());
        } catch (Error var3) {
            throw new RuntimeException("Unable to find environment details for: " + env + "\nPlease check your parameters and try again.\nYou may need to add the details to java-test-common");
        }
    }

    public static EnvironmentsNew getCurrentEnv() {
        return CURRENT_ENV;
    }

    public String getDxpV12BaseUrl() {
        return String.format(this.dxpV12BaseUrl, EIS_ENV);
    }

    public String getDxpV20BaseUrl() {
        return String.format(this.dxpV20BaseUrl, EIS_ENV);
    }

    public String getOpenLLookupsBaseUrl() {
        return String.format(this.openLLookupsBaseUrl, EIS_ENV);
    }

    public String getApiGWBaseUrl() {
        return String.format(this.apiGWBaseUrl, EIS_ENV);
    }

    public String getEnvironmentName() {
        return this.environmentName;
    }

    public String getEsureBaseUrl() {
        return this.esureBaseUrl;
    }
}