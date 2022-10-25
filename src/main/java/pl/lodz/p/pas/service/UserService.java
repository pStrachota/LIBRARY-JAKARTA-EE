package pl.lodz.p.pas.service;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import pl.lodz.p.pas.dto.ClientDto;
import pl.lodz.p.pas.dto.UserDto;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Admin;
import pl.lodz.p.pas.model.user.Client;
import pl.lodz.p.pas.model.user.Manager;

@Path("/user")
@Produces("application/json")
@Consumes("application/json")
public class UserService {

    @Inject
    UserManager userManager;

    @GET
    public Response getAllUsers() {
        return Response.ok(userManager.getUsers()).build();
    }

    @POST
    @Path("/admin")
    public Response createAdmin(Admin admin) {
        userManager.addUser(admin);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/client")
    public Response createClient(Client client) {
        userManager.addUser(client);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/manager")
    public Response createManager(Manager manager) {
        userManager.addUser(manager);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") @Min(0) Long id) {

        try {
            return Response.ok(userManager.getUser(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("admin/{id}")
    public Response updateAdmin(@PathParam("id") @Min(0) Long id, UserDto userDto) {
        try {
            userManager.updateUser(id, userDto);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("admin/{id}")
    public Response updateManager(@PathParam("id") @Min(0) Long id, UserDto userDto) {
        try {
            userManager.updateUser(id, userDto);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("admin/{id}")
    public Response updateClient(@PathParam("id") @Min(0) Long id, ClientDto clientDto) {
        try {
            userManager.updateUser(id, clientDto);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PATCH
    @Path("activate/{id}")
    public Response activateUser(@PathParam("id") @Min(0) long id) {
        try {
            userManager.activateUser(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PATCH
    @Path("deactivate/{id}")
    public Response deactivateUser(@PathParam("id") @Min(0) long id) {
        try {
            userManager.deactivateUser(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }


}
