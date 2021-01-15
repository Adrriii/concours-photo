
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationPath("/api/v1")
public class Api extends ResourceConfig {
    public Api() {
        packages("", "routes", "filters");


        register(new ApplicationBinder());
        register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO,
                LoggingFeature.Verbosity.HEADERS_ONLY, 10000));
        register(MultiPartFeature.class);
    }
}
