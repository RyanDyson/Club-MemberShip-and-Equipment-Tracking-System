public class CmdBorrow extends RecordedCommand {
    private EquipmentSet equipmentSet;
    private Member borrower;
    private String[] cmdParts;

    @Override
    public void execute(String args[]) throws ExInsufficientArgument, ExMemberNotFound, ExMemberNotFound, ExEquipmentNotFound, ExEquipmentSetAlreadyBorrowed, ExMemberAlreadyBorrowedSet{
        cmdParts = args;
        if (cmdParts.length < 3) {
            throw new ExInsufficientArgument();
        }
        String borrowerId = cmdParts[1];
        Club myClub = Club.getInstance();
        Equipment equipment = null;

        borrower = myClub.findMember(borrowerId);
        equipment = myClub.findEquipment(cmdParts[2]);
      
        if (borrower != null) {
          equipmentSet = myClub.borrowEquipment(cmdParts, borrower);
          if (equipmentSet != null && equipment != null) {
              borrower.borrowEquipmentSet();
              addUndoCommand(this);
              clearRedoList();
              System.out.println(borrower.toString() + " borrows " + equipmentSet.toString() + " (" + equipment.getName() + ") for " + SystemDate.getInstance().clone().toString() + " to " + equipmentSet.getReturnDate().toString());
              System.out.println("Done.");
          }
        }    
    }

    @Override
    public void undoMe() {
        if (equipmentSet != null) {
          borrower.returnEquipmentSet();
          equipmentSet.returnSet();
          addRedoCommand(this);
        }
    }

    @Override
    public void redoMe() {
        if (equipmentSet != null) { 
          borrower.borrowEquipmentSet();
          Club myClub = Club.getInstance();
          try {
            myClub.borrowEquipment(cmdParts, borrower);
          }
          catch (ExEquipmentNotFound e) {
            System.out.println(e.getMessage());
          }
          catch (ExEquipmentSetAlreadyBorrowed e) {
            System.out.println(e.getMessage());
          }
          catch (ExMemberAlreadyBorrowedSet e) {
            System.out.println(e.getMessage());
          }
          addUndoCommand(this);
        }
    }
}
