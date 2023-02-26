package pl.lodz.p.pas.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "userRealm")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:app/rocky/myCon",
        callerQuery = "#{'select password from User where login = ?'}",
        groupsQuery = "select role from User where login = ?",
        priorityExpression = "#{100}",
        hashAlgorithmParameters = {
                "Pbkdf2PasswordHash.Iterations=16000",
                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
                "Pbkdf2PasswordHash.SaltSizeBytes=64"
        }
)
public class MySqlIdentityStore {
}
