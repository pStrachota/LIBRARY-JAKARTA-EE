package pl.lodz.p.pas.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DuplicatedSerialNumberException extends WebApplicationException {
    public DuplicatedSerialNumberException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
