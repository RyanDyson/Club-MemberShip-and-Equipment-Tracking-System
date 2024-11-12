public interface Command {
    public void execute(String[] cmdParts) throws ExInsufficientArgument, ExMemberIdInUse, ExInvalidDateFormat, ExBorrowRequestPeriodOverlaps, ExEquipmentSetAlreadyBorrowed, ExEquipmentCodeInUse, ExEquipmentNotFound, ExMemberNotFound, ExNumberOfDaysLessThanOne, ExMemberAlreadyBorrowedSet;
}
