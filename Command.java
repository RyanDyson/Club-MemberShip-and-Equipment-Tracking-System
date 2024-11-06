public interface Command {
    public void execute(String[] cmdParts) throws ExInsufficientArgument, ExMemberIdInUse, ExInvalidDateFormat, ExBorrowRequestPeriodOverlaps, ExEquipmentAlreadyBorrowed, ExEquipmentCodeInUse, ExEquipmentNotFound, ExMemberNotFound, ExNumberOfDaysLessThanOne;
}
