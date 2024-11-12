public class EquipmentSet implements Comparable<EquipmentSet> {
    private String equipmentSetCode;
    private boolean isAvailable;
    private Day returnDate;
    private Member borrower;
    private int numset;

    @Override
    public int compareTo(EquipmentSet another) {
        if (another.returnDate == null) {
            return -1;
        }
        if (this.returnDate == null) {
            return 1;
        }
        return this.returnDate.compareTo(another.returnDate);
    }

    public EquipmentSet(String equipmentSetCode, int numset) {
        this.equipmentSetCode = equipmentSetCode;
        this.numset = numset + 1;
        isAvailable = true;
    }
    public Boolean isAvailable() {
        return isAvailable;
    }
    public void borrowSet(Member member, Day date ) {
        this.borrower = member;
        this.returnDate = date;
        isAvailable = false;
    }
    public Member getBorrower() {
        return borrower;
    }
    public Day getReturnDate() {
        return returnDate;
    }
    public String toString() {
        return equipmentSetCode+"_"+numset;
    }
    public void listEquipmentSet() {
        System.out.printf("", equipmentSetCode, numset);
    }
    public void returnSet() {
        isAvailable = true;
        borrower = null;
        returnDate = null;
    }
    public String getCode() {
        return equipmentSetCode;
    }

    public void currentEquipmentStatus() {
        System.out.println(this.toString());
        if (isAvailable) {
            System.out.println("    Current status: Available");
            return;
        }
        System.out.println("    Current status: " + borrower.toString() + " borrows for " + SystemDate.getInstance().clone().toString() + " to " + returnDate.toString());
    }
}
