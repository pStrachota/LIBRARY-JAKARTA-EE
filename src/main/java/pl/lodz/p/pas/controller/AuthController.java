package pl.lodz.p.pas.controller;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.pas.dto.user.ClientDto;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.security.JwtUtils;


@Path("/auth")
@DenyAll
@RequestScoped
public class AuthController {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Inject
    private UserManager userManager;

    @Inject
    private JwtUtils jwtUtils;

    @POST
    @Path("/register")
    @RolesAllowed("guest")
    public Response register(@Valid ClientDto clientDto) {

        if (userManager.checkIfLoginExist(clientDto.getLogin())) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        userManager.addUser(clientDto);

        return Response.ok("User created successfully").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/jwt")
    @RolesAllowed("guest")
    public Response login(@FormParam("login") String login,
                          @FormParam("password") String password) {


        CredentialValidationResult result = identityStoreHandler
                .validate(new UsernamePasswordCredential(login, password));

        String token = jwtUtils.generateJWT(result);

        return Response.ok(token).build();
    }
}
