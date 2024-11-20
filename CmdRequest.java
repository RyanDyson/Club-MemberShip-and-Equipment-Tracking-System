public class CmdRequest extends RecordedCommand {
    private Member requester;
    private EquipmentSet requestedSet;
    private Equipment equipmentType;
    private Day startDay;
    private Day endDay;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 5) {
                throw new ExInsufficientArgument();
            }

            int numDays;
            try {
                numDays = Integer.parseInt(cmdParts[4]);
            }
            catch (NumberFormatException e) {
                System.out.println("Please provide an integer for the number of days.");
                return;
            }
            
            String memberId = cmdParts[1];
            String EquipmentCode = cmdParts[2];
            String startDate = cmdParts[3];
            if (numDays < 1) {
                throw new ExNumberOfDaysLessThanOne();
            }

            Club myClub = Club.getInstance();

            requester = myClub.findMember(memberId);
            equipmentType = myClub.findEquipment(EquipmentCode);

            startDay = new Day(startDate);
            endDay = startDay.clone();
            endDay.addDays(numDays);

            if (requester != null) {
                requestedSet = equipmentType.requestSet(requester, startDay, endDay);

                if (requestedSet != null) {
                    requester.requestEquipmentSet();
                    addUndoCommand(this);
                    clearRedoList();
                    System.out.println(requester.toString() + " requests " + requestedSet.toString() + " (" + equipmentType.getName() + ") for " + startDay.toString() + " to " + endDay.toString());
                    System.out.println("Done.");
                }
            }
        }
        catch (ExInsufficientArgument e) {
            System.out.println(e.getMessage());
        }
        catch (ExNumberOfDaysLessThanOne e) {
            System.out.println(e.getMessage());
        } 
        catch (ExMemberNotFound e) {
            System.out.println(e.getMessage());
        }
        catch (ExInvalidDateFormat e) {
            System.out.println(e.getMessage());
        }
        catch (ExEquipmentNotFound e) {
            System.out.println(e.getMessage());
        }
        catch (ExEquipmentSetAlreadyBorrowed e) {
            System.out.println(e.getMessage());
        }
        catch (ExBorrowRequestPeriodOverlaps e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        if (requestedSet != null && requester != null) {
            requestedSet.cancelRequest(requester, startDay, endDay);
            requester.cancelRequest();
            addRedoCommand(this);
        }
    }

    @Override
    public void redoMe() {
        if (requestedSet != null && requester != null && startDay != null && endDay != null) {
            requester.requestEquipmentSet();
            try {
            requestedSet.requestSet(requester, startDay, endDay, equipmentType);
            }
            catch (ExBorrowRequestPeriodOverlaps e) {
                System.out.println(e.getMessage());
            }
            addUndoCommand(this);
        }
    }
}
