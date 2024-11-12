public class ExMemberAlreadyBorrowedSet extends Exception {
    public ExMemberAlreadyBorrowedSet() {
        super("The member is currently borrowing a set of this equipment. He/she cannot borrow one more at the same time.");
    }
    public ExMemberAlreadyBorrowedSet(String message) {
        super(message);
    }
}
