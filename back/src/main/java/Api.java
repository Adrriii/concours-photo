import filters.AuthRequestFilter;
import filters.CorsFilter;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationPath("/api/v1")
public class Api extends ResourceConfig {
    public Api() {
        packages("", "routes", "filters");

        register(CorsFilter.class);
        register(AuthRequestFilter.class);
        register(RolesAllowedDynamicFeature.class);
        register(new ApplicationBinder());
        register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO,
                LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));
    }
}
