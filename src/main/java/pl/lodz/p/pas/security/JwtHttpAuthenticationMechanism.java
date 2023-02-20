package pl.lodz.p.pas.security;


import io.fusionauth.jwt.domain.JWT;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class JwtHttpAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private JwtUtils jwtUtils;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) {

        JWT jwt = null;
        try {
            jwt = jwtUtils.getJwtFromRequest(httpServletRequest);
        } catch (AuthenticationException e) {
            return httpMessageContext.responseUnauthorized();
        }
        if (jwt != null) {
            String role = jwt.getString("role");
            return httpMessageContext.notifyContainerAboutLogin(
                    jwt.getString("sub"), new HashSet<>(Arrays.asList(role))
            );
        } else {
            CallerPrincipal login = new CallerPrincipal("guest");
            Set<String> roles = new HashSet<>() {{
                add("guest");
            }};
            return httpMessageContext.notifyContainerAboutLogin(login, roles);
        }
    }
}
