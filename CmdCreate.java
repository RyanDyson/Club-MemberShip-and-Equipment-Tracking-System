public class CmdCreate extends RecordedCommand{
    private Club myClub = Club.getInstance();
    private Equipment newEquipment;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArgument, ExEquipmentCodeInUse {
        if (cmdParts.length < 3) {
            throw new ExInsufficientArgument();
        }
        String EquipmentCode = cmdParts[1];
        Equipment e;
        try {
            e = myClub.findEquipment(EquipmentCode);
        }
        catch (ExEquipmentNotFound e1) {
            e = null;
        }

        if (e != null) {
            throw new ExEquipmentCodeInUse(e);
        }
        else {
            String Name = cmdParts[2];
            newEquipment = new Equipment(EquipmentCode, Name);
            System.out.println("Done.");
            addUndoCommand(this);
            clearRedoList();
        }
    }

    @Override
    public void undoMe() {
        if (newEquipment != null) {
            myClub.removeEquipment(newEquipment);
            addRedoCommand(this);
        }
    }

    @Override
    public void redoMe() {
        if (newEquipment != null) {
            myClub.addEquipment(newEquipment);
            addUndoCommand(this);
        }
    }
}
