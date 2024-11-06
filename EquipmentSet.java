public class EquipmentSet {
    private String equipmentSetCode;
    private boolean isAvailable;
    private Day returnDate;
    private Member borrower;
    private int numset;

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
    public String getCode() {
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
}
