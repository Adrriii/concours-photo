package filters;

import model.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class JerseySecurityContext implements SecurityContext {
    private final User user;

    public JerseySecurityContext(User user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return new Principal() {
            @Override
            public String getName() {
                return user.username;
            }
        };
    }

    @Override
    public boolean isUserInRole(String s) {
        // TODO Faire un "user.hasRole(s)"
        return true;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
