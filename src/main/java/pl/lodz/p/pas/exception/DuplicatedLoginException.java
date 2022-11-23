package pl.lodz.p.pas.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DuplicatedLoginException extends WebApplicationException {
    public DuplicatedLoginException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
