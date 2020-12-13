import filters.AuthRequestFilter;
import filters.CorsFilter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/v1")
public class Api extends ResourceConfig {
    public Api() {
        packages("", "routes", "filters");
        register(CorsFilter.class);
        register(AuthRequestFilter.class);
        register(new ApplicationBinder());
    }
}
