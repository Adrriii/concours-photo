package filters;

import model.User;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthRequestFilter implements ContainerRequestFilter {
    @Context HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            containerRequestContext.setSecurityContext(new JerseySecurityContext(user));
            Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME).info("User not null !");
        } else {
            Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME).info("User is null !");
        }
    }
}