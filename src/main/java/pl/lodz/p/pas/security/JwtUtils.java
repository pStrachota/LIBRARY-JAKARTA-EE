package pl.lodz.p.pas.security;


import io.fusionauth.jwt.InvalidJWTException;
import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class JwtUtils {

    private final String AUTH_HEADER = "Authorization";
    private final String BEARER = "Bearer ";

    @Inject
    @ConfigProperty(name = "jwt.issuer")
    private String ISSUER;

    @Inject
    @ConfigProperty(name = "jwt.timeout")
    private int TIMEOUT;

    @Inject
    @ConfigProperty(name = "jwt.key")
    private String SECRET;

    public String generateJWT(CredentialValidationResult validationResult) {
        String subject = validationResult.getCallerPrincipal().getName();
        String role = (String) validationResult.getCallerGroups().toArray()[0];

        Signer signer = HMACSigner.newSHA512Signer(SECRET);

        JWT jwt = new JWT().setIssuer(ISSUER)
                .setIssuedAt(ZonedDateTime.now())
                .setSubject(subject)
                .addClaim("role", role)
                .setExpiration(ZonedDateTime.now().plusMinutes(TIMEOUT));

        return JWT.getEncoder().encode(jwt, signer);
    }

    public JWT getJwtFromRequest(HttpServletRequest request) throws AuthenticationException {
        String authHeader = request.getHeader(AUTH_HEADER);
        return getJwtFromAuthHeader(authHeader);
    }

    public JWT getJwtFromAuthHeader(String authHeader) throws AuthenticationException {
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            return null;
        }
        return decodeJwt(authHeader.substring(BEARER.length()));
    }

    public JWT decodeJwt(String token) throws AuthenticationException {
        try {
            Verifier verifier = HMACVerifier.newVerifier(SECRET);
            return JWT.getDecoder().decode(token, verifier);
        } catch (InvalidJWTException | JWTExpiredException exception) {
            throw new AuthenticationException();
        }
    }

}
