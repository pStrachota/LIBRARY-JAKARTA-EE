package pl.lodz.p.pas.exception;

public class ExceededLimitException extends RuntimeException {
    public ExceededLimitException(String message) {
        super(message);
    }
}
