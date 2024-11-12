import java.util.ArrayList;
import java.util.Collections;

public class Club {
    private ArrayList<Member> allMembers;
    private ArrayList<Equipment> allEquipment;

    private static Club instance = new Club();

    private Club() {
        allMembers = new ArrayList<Member>();
        allEquipment = new ArrayList<Equipment>();
    }

    public static Club getInstance() {
        return instance;
    }

    public void addMember(Member newMember) {
        allMembers.add(newMember);
    }

    public void listClubMembers() {
        Collections.sort(allMembers);
        Member.list(allMembers);
    }

    public void removeMember(Member m) {
        allMembers.remove(m);
    }

    public Member findMember(String memberId) throws ExMemberNotFound {
        Member e = Member.getMemberById(allMembers, memberId);
        if (e != null) {
            return e;
        }
        throw new ExMemberNotFound();
    }

    public void addEquipment(Equipment newEquipment) {
        allEquipment.add(newEquipment);
    }

    public void removeEquipment(Equipment e) {
        allEquipment.remove(e);
    }

    public void listEquipment() {
        Collections.sort(allEquipment);
        Equipment.listEquipment(allEquipment);
    }

    public Equipment findEquipment(String equipmentId) throws ExEquipmentNotFound {
        return Equipment.getEquipmentById(equipmentId, allEquipment);
    }

    public EquipmentSet borrowEquipment(String[] args, Member borrower) throws ExEquipmentNotFound, ExEquipmentSetAlreadyBorrowed, ExMemberAlreadyBorrowedSet {
        Equipment e = findEquipment(args[2]);
        EquipmentSet borrowedSet = e.borrowEquipmentSets(args, borrower);
        return borrowedSet;
    }

    public void listMemberStatus() {
        for (Member m: allMembers) {
            m.currentMemberStatus();
            System.out.println();
        }
    }

    public void listEquipmentStatus() {
        for (Equipment e : allEquipment) {
            e.currentEquipmentStatus();
            System.out.println();
        }
    }

    public void printBorrowedEquipmentSetByMember(Member m) {
        boolean haveBorrowed = false;
        for (Equipment e: allEquipment) {
            EquipmentSet es = e.getBorrowedSetByMember(m);
            if (es != null) {
                haveBorrowed = true;
                System.out.println("- borrows " + es.toString() + " (" + e.getName() + ") for " + SystemDate.getInstance().clone().toString() + " to " + es.getReturnDate().toString());
            }
        }
        if (!haveBorrowed) {
            System.out.println("No record.");
        }
    }
}
