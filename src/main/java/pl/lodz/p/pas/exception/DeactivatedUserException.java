package pl.lodz.p.pas.exception;

public class DeactivatedUserException extends RuntimeException {
    public DeactivatedUserException(String message) {
        super(message);
    }
}
