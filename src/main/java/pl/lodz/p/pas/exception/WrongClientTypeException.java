package pl.lodz.p.pas.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class WrongClientTypeException extends WebApplicationException {
    public WrongClientTypeException(String message) {
        super(message, Response.Status.BAD_REQUEST);
    }
}
