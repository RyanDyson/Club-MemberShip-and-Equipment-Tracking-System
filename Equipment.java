import java.util.*;

public class Equipment {
    private String equipmentCode;
    private String name;
    private ArrayList<EquipmentSet> equipmentSet;

    public Equipment(String equipmentCode, String name) {
        this.equipmentCode = equipmentCode;
        this.name = name;
        equipmentSet = new ArrayList<EquipmentSet>();
        Club.getInstance().addEquipment(this);
    }

    public void borrowEquipmentSets(String[] args, Member borrower) {
        int borrowDays = (args.length >= 4) ? Integer.parseInt(args[3]) : 7;

        Day returnDate = SystemDate.getInstance().clone();
        returnDate.addDays(borrowDays);

        for (EquipmentSet e: equipmentSet) {
            if (e.isAvailable()) {
                e.borrowSet(borrower, returnDate);
            }
        }

        System.out.println(borrower.toString() + " borrows " + equipmentCode + "(" + name + ") for " + SystemDate.getInstance().clone().toString() + " to " + returnDate.toString());
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

        int i=0;
        for (EquipmentSet e: this.equipmentSet) {
            if (!e.isAvailable()) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(String.format("%s(%s)", e.getCode(), e.getBorrower().getMemberId()));
            }
            i++;
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
        for (EquipmentSet e: equipmentSet) {
            if (e.getBorrower().equals(m)) {
                e.returnSet();
            }
        }
    }
}