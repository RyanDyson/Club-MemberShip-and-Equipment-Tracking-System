public class CmdRegister extends RecordedCommand{
    private Member m;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArgument, ExMemberIdInUse{
        if (cmdParts.length < 3) {
            throw new ExInsufficientArgument();
        }
        String id = cmdParts[1];
        String name = cmdParts[2];
        Club myClub = Club.getInstance();
        try {
            m = myClub.findMember(id);
        }
        catch (ExMemberNotFound e) {
            m = null;
        }
        
        if (m != null) {
            throw new ExMemberIdInUse(m);
        }
        else {
            m = new Member(id, name);
            if (m != null) {
                addUndoCommand(this);
                clearRedoList();
                System.out.println("Done. ");
            }
        }
    }

    @Override
    public void undoMe() {
        Club myClub = Club.getInstance();
        myClub.removeMember(m);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        if (m != null) {
            Club myClub = Club.getInstance();
            myClub.addMember(m);
            addUndoCommand(this);
        }
    }
}
