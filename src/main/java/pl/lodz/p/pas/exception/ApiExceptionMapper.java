package pl.lodz.p.pas.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        String message = e.getMessage();

        Response response = e.getResponse();
        Response.Status status = response.getStatusInfo().toEnum();

        return Response.status(status)
                .entity(status.getStatusCode() + " | " + status.getReasonPhrase() + " | " + message)
                .build();
    }
}
