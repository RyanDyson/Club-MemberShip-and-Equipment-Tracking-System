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
        Collections.sort(allMembers);
    }

    public void listClubMembers() {
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
        Equipment.listEquipment(allEquipment);
    }

    public Equipment findEquipment(String equipmentId) throws ExEquipmentNotFound {
        return Equipment.getEquipmentById(equipmentId, allEquipment);
    }

    public Equipment borrowEquipment(String[] args, Member borrower) {
        try {
            Equipment e = findEquipment(args[2]);
            e.borrowEquipmentSets(args, borrower);
            return e;
        }
        catch (ExEquipmentNotFound e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
