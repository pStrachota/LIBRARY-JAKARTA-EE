package pl.lodz.p.pas.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class EndedRentException extends WebApplicationException {
    public EndedRentException(String message) {
        super(message, Response.Status.BAD_REQUEST);
    }
}
