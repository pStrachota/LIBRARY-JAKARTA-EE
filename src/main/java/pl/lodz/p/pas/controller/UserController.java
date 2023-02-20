package pl.lodz.p.pas.controller;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import pl.lodz.p.pas.dto.user.AdminDto;
import pl.lodz.p.pas.dto.user.ClientDto;
import pl.lodz.p.pas.dto.user.ManagerDto;
import pl.lodz.p.pas.manager.UserManager;

@Path("/user")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
@RolesAllowed("admin")
public class UserController {

    @Inject
    UserManager userManager;

    @GET
    public Response getAllUsers() {
        return Response.ok(userManager.getUsers()).build();
    }

    @POST
    @Path("/admin")
    public Response createAdmin(@Valid AdminDto userDto) {
        userManager.addUser(userDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/client")
    public Response createClient(@Valid ClientDto clientDto) {
        userManager.addUser(clientDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/login-search")
    @RolesAllowed({"admin", "manager"})
    public Response findByLoginContains(@QueryParam("login") String login) {
        return Response.ok(userManager.findByLoginContains(login)).build();
    }

    @GET
    @Path("/name-search")
    @RolesAllowed({"admin", "manager"})
    public Response findByNameContains(@QueryParam("name") String name) {
        return Response.ok(userManager.findByNameContains(name)).build();
    }

    @POST
    @Path("/manager")
    public Response createManager(@Valid ManagerDto userDto) {
        userManager.addUser(userDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") @Min(0) Long id) {
        return Response.ok(userManager.getUser(id)).build();
    }

    @PUT
    @Path("admin/{id}")
    public Response updateAdmin(@PathParam("id") @Min(0) Long id, @Valid AdminDto adminDto) {
        userManager.updateUser(id, adminDto);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("manager/{id}")
    public Response updateManager(@PathParam("id") @Min(0) Long id, @Valid ManagerDto managerDto) {
        userManager.updateUser(id, managerDto);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("client/{id}")
    public Response updateClient(@PathParam("id") @Min(0) Long id, @Valid ClientDto clientDto) {
        userManager.updateUser(id, clientDto);
        return Response.status(Response.Status.OK).build();
    }

    @PATCH
    @Path("activate/{id}")
    public Response activateUser(@PathParam("id") @Min(0) long id) {
        return Response.ok(userManager.activateUser(id)).build();
    }

    @PATCH
    @Path("deactivate/{id}")
    public Response deactivateUser(@PathParam("id") @Min(0) long id) {
        return Response.ok(userManager.deactivateUser(id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") @Min(0) long id) {
        userManager.removeUser(id);
        return Response.status(Response.Status.OK).build();

    }
}
