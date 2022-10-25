package pl.lodz.p.pas.exception;

public class DuplicatedLoginException extends RuntimeException {
    public DuplicatedLoginException(String message) {
        super(message);
    }
}
