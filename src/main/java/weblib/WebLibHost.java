package weblib;

import spark.Spark;
import spark.route.HttpMethod;
import weblib.routing.Route;
import weblib.views.IView;

public class WebLibHost {
    /**
     * Initializes WebLibHost (without) SSL.
     *
     * @param port
     */
    public WebLibHost(int port, String staticFileLocation) {
        initialize(port, staticFileLocation);
    }

    /**
     * Initializes WebLibHost with SSL enabled.
     *
     * @param port
     * @param staticFileLocation
     * @param useSSL
     * @param sslConfig
     */
    public WebLibHost(int port, String staticFileLocation, boolean useSSL, SSLConfiguration sslConfig) {
        // If we have a valid sslConfig object, let's use it!
        if (sslConfig != null) {

            // Set up SSL in Spark!
            Spark.secure(
                    sslConfig.KeyStorePath, sslConfig.KeyStorePassword,
                    sslConfig.TrustStorePath, sslConfig.TrustStorePassword
            );
        }

        initialize(port, staticFileLocation);
    }

    private void initialize(int port, String staticFileLocation) {
        // This updates the Views object we will later use to add our routes.
        ViewFinder.getAnnotatedViews();

        // Set port and forces Spark to init.
        Spark.port(port);

        Spark.staticFileLocation("/public");

        for (IView view : ViewFinder.Views) {
            Route route = view.getClass().getAnnotation(Route.class);

            String path = route.Path();
            String template = route.Template();
            HttpMethod method = route.Method();

            if (method == HttpMethod.get) {
                Spark.get(path,
                        (request, response) -> view.getViewString(
                                path, template, request, response, method)
                );
            }

            if (method == HttpMethod.post) {
                Spark.post(path,
                        (request, response) -> view.getViewString(
                                path, template, request, response, method)
                );
            }

            if (method == HttpMethod.put) {
                Spark.put(path,
                        (request, response) -> view.getViewString(
                                path, template, request, response, method)
                );
            }

            if (method == HttpMethod.delete) {
                Spark.delete(path,
                        (request, response) -> view.getViewString(

                                path, template, request, response, method)
                );
            }
        }
    }
}
