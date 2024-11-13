public class ExNumberOfDaysLessThanOne extends Exception{
    public ExNumberOfDaysLessThanOne() {
        super("The number of days must be at least 1.");
    }
    public ExNumberOfDaysLessThanOne(String message) {
        super(message);
    }
}
