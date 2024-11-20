public class CmdArrive extends RecordedCommand {
    private EquipmentSet arrivedSet;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 2) {
                throw new ExInsufficientArgument();
            }

            String equipmentCode = cmdParts[1];
            Equipment e;
            try {
                e = Club.getInstance().findEquipment(equipmentCode);
            }
            catch (ExEquipmentNotFound e1) {
                throw new ExEquipmentNotFound("Missing record for Equipment "+ equipmentCode + ".  Cannot mark this item arrival.");
            }

            if (e != null) {
                String arrivedsetCode = cmdParts[1];
                int numset = e.size();
                arrivedSet = new EquipmentSet(arrivedsetCode, numset);
                e.addEquipmentSet(arrivedSet);
                System.out.println("Done.");
                addUndoCommand(this);
                clearRedoList();
            }
        }
        catch (ExInsufficientArgument e) {
            System.out.println(e.getMessage());
        }
        catch (ExEquipmentNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        try {
            Equipment e = Club.getInstance().findEquipment(arrivedSet.getCode());
            e.removeEquipmentSet(arrivedSet);
            addRedoCommand(this);
        } catch (ExEquipmentNotFound ex) {
            System.out.println("Equipment not found during undo: " + ex.getMessage());
        }
    }

    @Override
    public void redoMe() {
        try {
            Equipment e = Club.getInstance().findEquipment(arrivedSet.getCode());
            e.addEquipmentSet(arrivedSet);
            addUndoCommand(this);
        } catch (ExEquipmentNotFound ex) {
            System.out.println("Equipment not found during redo: " + ex.getMessage());
        }
    }
}
