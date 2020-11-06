import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Api extends ResourceConfig {
    public Api() {
        packages("");
    }
}
