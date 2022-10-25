package pl.lodz.p.pas.service;

import javax.inject.Inject;
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
import javax.ws.rs.core.Response;
import pl.lodz.p.pas.dto.RentDto;
import pl.lodz.p.pas.manager.RentManager;

@Path("/rent")
@Produces("application/json")
@Consumes("application/json")
public class RentService {

    @Inject
    RentManager rentManager;

    @GET
    public Response getAllRents() {
        return Response.ok(rentManager.getRents()).build();
    }

    @GET
    @Path("{id}")
    public Response getRentById(@PathParam("id") @Min(0) Long id) {

        try {
            return Response.ok(rentManager.getRent(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createRent(RentDto rentDto) {

        try {
            rentManager.addRent(rentDto);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PATCH
    @Path("end/{id}")
    public Response endRent(@PathParam("id") @Min(0) Long id) {
        try {
            rentManager.removeRent(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @PUT
    @Path("{id}")
    public Response updateRent(@PathParam("id") @Min(0) Long id, RentDto rentDto) {

        try {
            rentManager.updateRent(id, rentDto);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteRent(@PathParam("id") @Min(0) Long id) {

        try {
            rentManager.removeRent(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}
