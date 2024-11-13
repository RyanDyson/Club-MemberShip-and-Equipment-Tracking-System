public class ExBorrowRequestPeriodOverlaps extends Exception {
    public ExBorrowRequestPeriodOverlaps() {
        super("The period overlaps with a current period that the member requests the equipment.");
    }
    public ExBorrowRequestPeriodOverlaps(String Message) {
        super(Message);
    }
}