public class CmdStartNewDay extends RecordedCommand{
    private SystemDate date = SystemDate.getInstance();
    private Day newDatee;
    private Day prevDate;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArgument, ExInvalidDateFormat {
        if (cmdParts.length < 2) {
            throw new ExInsufficientArgument();
        }

        String newDate = cmdParts[1];
        newDatee = new Day(newDate);
        prevDate = date.clone();
        if (newDatee.compareTo(date) < 0) {
            throw new ExInvalidDateFormat("Invalid new day.  The new day has to be later than the current date " + date.toString() + ".");
        }

        date.set(newDate);

        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done. ");
    }

    @Override
    public void undoMe() {
        if (prevDate != null) {
            try {
                date.set(prevDate.toString());
                addRedoCommand(this);
            } 
            catch (ExInvalidDateFormat e) {
                System.out.println(e.getMessage());
            } 
        }
    }

    @Override
    public void redoMe() {
        if (date != null) {
            try {
                date.set(newDatee.toString());
                addUndoCommand(this);
            } 
            catch (ExInvalidDateFormat e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
