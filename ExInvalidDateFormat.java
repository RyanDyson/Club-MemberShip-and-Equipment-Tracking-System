public class ExInvalidDateFormat extends Exception{
    public ExInvalidDateFormat() {
        super("Invalid date.");
    }
    public ExInvalidDateFormat(String message) {
        super(message);
    }
}
