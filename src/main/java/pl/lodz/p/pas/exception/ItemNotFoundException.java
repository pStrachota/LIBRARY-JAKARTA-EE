package pl.lodz.p.pas.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ItemNotFoundException extends WebApplicationException {

    public ItemNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
