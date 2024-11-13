import java.util.*;

public class Equipment implements Comparable<Equipment> {
    private String equipmentCode;
    private String name;
    private ArrayList<EquipmentSet> equipmentSet;

    public Equipment(String equipmentCode, String name) {
        this.equipmentCode = equipmentCode;
        this.name = name;
        equipmentSet = new ArrayList<EquipmentSet>();
        Club.getInstance().addEquipment(this);
    }

    public EquipmentSet borrowEquipmentSets(String[] args, Member borrower) throws ExEquipmentSetAlreadyBorrowed, ExMemberAlreadyBorrowedSet, ExBorrowRequestPeriodOverlaps{
        for (EquipmentSet e: equipmentSet) {
            if (!e.isAvailable()) {
                if (e.getBorrower().equals(borrower)) {
                    throw new ExMemberAlreadyBorrowedSet();
                }
            }
        }
        int borrowDays = (args.length >= 4) ? Integer.parseInt(args[3]) : 7;

        Day returnDate = SystemDate.getInstance().clone();
        EquipmentSet borrowedset = null;
        returnDate.addDays(borrowDays);

        for (EquipmentSet e: equipmentSet) {
            if (e.isAvailable()) {
                try {
                e.borrowSet(borrower, returnDate);
                borrowedset = e;
                break;
                }
                catch (ExBorrowRequestPeriodOverlaps e1) {
                    System.out.println(e1.getMessage());
                    return null;
                }
            }
        }

        if (borrowedset != null) {
            Collections.sort(equipmentSet);
            return borrowedset;
        }
        throw new ExEquipmentSetAlreadyBorrowed();
    }

    public static void listEquipment(ArrayList<Equipment> allEquipments) {
        System.out.printf("%-5s%-15s%5s\n", "Code", "Name", "#Sets");
        for (Equipment e : allEquipments) {
            boolean isAnyBorrowed = e.isAnyBorrowed();
            String borrowedSets = e.equipmentSet.isEmpty() ? "" : " (Borrowed set(s): " + e.getBorrowedSets() + ")";
            System.out.printf("%-5s%-15s%5d%s\n", e.equipmentCode, e.name, e.equipmentSet.size(), (isAnyBorrowed ? borrowedSets : ""));
        }
    }
    
    private String getBorrowedSets() {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (EquipmentSet e : this.equipmentSet) {
            if (!e.isAvailable()) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(String.format("%s(%s)", e.toString(), e.getBorrower().getMemberId()));
                i++;
            }
        }

        return sb.toString();
    }

    private boolean isAnyBorrowed() {
        for (EquipmentSet e: equipmentSet) {
            if (!e.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public void addEquipmentSet(EquipmentSet e) {
        equipmentSet.add(e);
    }

    public static Equipment getEquipmentById(String id, ArrayList<Equipment> allEquipments) throws ExEquipmentNotFound {
        for (Equipment e: allEquipments) {
            if (e.equipmentCode.equals(id)) {
                return e;
            }
        }
        throw new ExEquipmentNotFound();
    }

    public int size() {
        return equipmentSet.size();
    }

    public void removeEquipmentSet(EquipmentSet e) {
        equipmentSet.remove(e);
    }

    public String toString(boolean withPareenthesees) {
        return withPareenthesees ? equipmentCode + "(" + name + ")" : equipmentCode + " " + name;
    }

    public void returnEquipmentSet(Member m) {
        for (EquipmentSet e : equipmentSet) {
            if (e.getBorrower() != null && e.getBorrower().equals(m)) {
                e.returnSet();
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Equipment another) {
        return this.equipmentCode.compareTo(another.equipmentCode);
    }

    public void currentEquipmentStatus() {
        System.out.println("[" + this.toString(false) + "]");
        if (equipmentSet.isEmpty()) {
            System.out.println("  We do not have any sets for this equipment.");
            return;
        }
        for (EquipmentSet e : equipmentSet) {
            System.out.print("  ");
            e.currentEquipmentStatus();
        }
    }

    public EquipmentSet getBorrowedSetByMember(Member m) {
        for (EquipmentSet e : equipmentSet) {
            if (!e.isAvailable() && e.getBorrower().equals(m)) {
                return e;
            }
        }
        return null; // Return null if no borrowed set is found
    }

    public EquipmentSet requestSet(Member requester, Day start, Day end) throws ExBorrowRequestPeriodOverlaps, ExEquipmentSetAlreadyBorrowed {
        for (EquipmentSet e : equipmentSet) {
            for (RequestPeriod request : e.getAllRequests()) {
                if (request.requester.equals(requester) &&
                    ((start.compareTo(request.start) >= 0 && start.compareTo(request.end) <= 0) ||
                     (end.compareTo(request.start) >= 0 && end.compareTo(request.end) <= 0) ||
                     (start.compareTo(request.start) <= 0 && end.compareTo(request.end) >= 0))) {
                    throw new ExBorrowRequestPeriodOverlaps("The period overlaps with a current period that the member borrows / requests the equipment.");
                }
            }
        }

        EquipmentSet requestedSet = null;
        for (EquipmentSet e : equipmentSet) {
            if (e.isAvailable(start, end)) {
                e.requestSet(requester, start, end, this);
                requestedSet = e;
                break;
            }
        }

        if (requestedSet == null) {
            throw new ExEquipmentSetAlreadyBorrowed("There is no available set of this equipment for the command.");
        }

        return requestedSet;
    }

    public ArrayList<RequestPeriod> printRequestedSetByMember(Member requester) {
        ArrayList<RequestPeriod> allRequestsByMember = new ArrayList<RequestPeriod>();

        for (EquipmentSet e: equipmentSet) {
            ArrayList<RequestPeriod> requests = e.getRequestsByMember(requester);
            if (requests.size() > 0) {
                allRequestsByMember.addAll(requests);
            }
        }

        return allRequestsByMember;
    }
}