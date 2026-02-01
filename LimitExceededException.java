package bank;

public class LimitExceededException extends ATMException {
    public LimitExceededException(String message) {
        super(message);
    }
}
