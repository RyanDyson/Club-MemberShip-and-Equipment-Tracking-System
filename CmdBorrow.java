public class CmdBorrow extends RecordedCommand {
    private Equipment equipment;
    private Member borrower;
    private String[] cmdParts;

    @Override
    public void execute(String args[]) throws ExInsufficientArgument{
        cmdParts = args;
        if (cmdParts.length < 3) {
            throw new ExInsufficientArgument();
        }
        String borrowerId = cmdParts[1];

        Club myClub = Club.getInstance();
        try {
          borrower = myClub.findMember(borrowerId);
        }
        catch (ExMemberNotFound e) {
          System.out.println(e.getMessage());
        }
        finally {if (borrower != null) {
          equipment = myClub.borrowEquipment(cmdParts, borrower);
          if (equipment != null) {
              borrower.borrowEquipmentSet();
              System.out.println("Done.");
              addUndoCommand(this);
              clearRedoList();
          }
        } 
      }    
    }

    @Override
    public void undoMe() {
        if (equipment != null) {
          borrower.returnEquipmentSet();
          equipment.returnEquipmentSet(borrower);
          addRedoCommand(this);
        }
    }

    @Override
    public void redoMe() {
        if (equipment != null) { 
          borrower.borrowEquipmentSet();
          Club myClub = Club.getInstance();
          myClub.borrowEquipment(cmdParts, borrower);
          addUndoCommand(this);
        }
    }
}
