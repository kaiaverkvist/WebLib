package weblib;

public class SSLConfiguration {

    public String KeyStorePath;
    public String KeyStorePassword;

    public String TrustStorePath;
    public String TrustStorePassword;

    /**
     * Object passed into the WebLibHost to configure SSL.
     *
     * @param keyStorePath       path to the KS
     * @param keyStorePassword   password to the KS
     * @param trustStorePath     path to the TS
     * @param trustStorePassword password to the TS
     */
    public SSLConfiguration(String keyStorePath, String keyStorePassword,
                            String trustStorePath, String trustStorePassword) {
        KeyStorePath = keyStorePath;
        KeyStorePassword = keyStorePassword;

        TrustStorePath = trustStorePath;
        TrustStorePassword = trustStorePassword;
    }
}
