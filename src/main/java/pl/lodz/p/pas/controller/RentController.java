package pl.lodz.p.pas.controller;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import pl.lodz.p.pas.dto.RentDto;
import pl.lodz.p.pas.manager.RentManager;

@Path("/rent")
@Produces("application/json")
@Consumes("application/json")
public class RentController {

    @EJB
    RentManager rentManager;

    @GET
    public Response getAllRents() {
        return Response.ok(rentManager.getRents()).build();
    }

    @GET
    @Path("{id}")
    public Response getRentById(@PathParam("id") @Min(0) Long id) {
        return Response.ok(rentManager.getRent(id)).build();
    }

    @POST
    public Response createRent(@Valid RentDto rentDto) {
        rentManager.addRent(rentDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{id}")
    public Response endRent(@PathParam("id") @Min(0) Long id) {
        rentManager.removeRent(id);
        return Response.ok().build();
    }
}
