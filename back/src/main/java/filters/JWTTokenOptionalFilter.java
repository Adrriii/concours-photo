package filters;

import io.jsonwebtoken.Jwts;
import util.KeyGenerator;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;

@Provider
@JWTTokenOptional
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenOptionalFilter implements ContainerRequestFilter {
    @Inject
    private KeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            Key key = keyGenerator.generateKey();

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            String username = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            requestContext.setProperty("username", username);
        } catch (Exception ignored) {}
    }
}
