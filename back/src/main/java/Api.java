import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/v1")
public class Api extends ResourceConfig {
    public Api() {
        packages("", "routes");
        register(CorsFilter.class);
    }
}
