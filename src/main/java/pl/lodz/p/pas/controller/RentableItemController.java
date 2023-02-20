package pl.lodz.p.pas.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import pl.lodz.p.pas.dto.resource.ArticleDto;
import pl.lodz.p.pas.dto.resource.BookDto;
import pl.lodz.p.pas.manager.RentableItemManager;


@Path("/rentable-item")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class RentableItemController {

    @Inject
    RentableItemManager rentableItemManager;

    @GET
    @Produces("application/json")
    public Response getAllRentableItems() {
        return Response.ok(rentableItemManager.getRentableItems()).build();
    }

    @GET
    @Path("{id}")
    public Response getRentableItemById(@PathParam("id") @Min(0) Long id) {
        return Response.ok(rentableItemManager.getRentableItem(id)).build();
    }

    @POST
    @Path("/book")
    @Consumes("application/json")
    public Response createBookRentableItem(@Valid BookDto bookDto) {
        rentableItemManager.addRentableItem(bookDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/article")
    public Response createArticleRentableItem(@Valid ArticleDto articleDto) {
        rentableItemManager.addRentableItem(articleDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/book/{id}")
    public Response updateBookRentableItem(@PathParam("id") @Min(0) Long id,
                                           @Valid BookDto bookDto) {
        rentableItemManager.updateRentableItem(id, bookDto);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/title-search/{title}")
    public Response getRentableItemsByTitle(@QueryParam("title") String title) {
        return Response.ok(rentableItemManager.findByTitleContains(title)).build();
    }

    @PUT
    @Path("/article/{id}")
    public Response updateArticleRentableItem(@PathParam("id") @Min(0) Long id,
                                              @Valid ArticleDto articleDto) {

        rentableItemManager.updateRentableItem(id, articleDto);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


    @DELETE
    @Path("{id}")
    public Response deleteRentableItem(@PathParam("id") @Min(0) Long id) {
        rentableItemManager.deleteRentableItem(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
