package pl.lodz.p.pas.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DeactivatedUserException extends WebApplicationException {
    public DeactivatedUserException(String message) {
        super(message, Response.Status.BAD_REQUEST);
    }
}
