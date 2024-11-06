import java.util.*;

public abstract class RecordedCommand implements Command{
    public static ArrayList<RecordedCommand> undoList = new ArrayList<RecordedCommand>();
    public static ArrayList<RecordedCommand> redoList = new ArrayList<RecordedCommand>();

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArgument, ExMemberIdInUse, ExInvalidDateFormat, ExBorrowRequestPeriodOverlaps, ExEquipmentAlreadyBorrowed, ExEquipmentCodeInUse, ExEquipmentNotFound, ExMemberNotFound, ExNumberOfDaysLessThanOne{};

    public abstract void undoMe();
    public abstract void redoMe();

    public void addUndoCommand(RecordedCommand cmd) {
        undoList.add(cmd);
    }
    public void addRedoCommand(RecordedCommand cmd) {
        redoList.add(cmd);
    }

    public void clearRedoList() {
        redoList.clear();
    }

    public static void undoOneCommand() {
        if (undoList.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        undoList.remove(undoList.size()-1).undoMe();
    }

    public static void redoOneCommand() {
        if (redoList.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }
        redoList.remove(redoList.size()-1).redoMe();
    }
}
