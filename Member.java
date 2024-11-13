import java.util.ArrayList;

public class Member implements Comparable<Member>{
    private String id;
    private String name;
    private Day joinDate;
    private int numBorrowed;
    private int numRequested;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.joinDate = SystemDate.getInstance().clone();
        Club.getInstance().addMember(this);
    }

    @Override
    public int compareTo(Member another) {
        return this.id.compareTo(another.id);
    }

    public static void list(ArrayList<Member> allMembers) {
         System.out.printf("%-5s%-9s%11s%11s%13s\n", "ID", "Name","Join Date ", "#Borrowed", "#Requested");
        for (Member m: allMembers) {
            System.out.printf("%-5s%-9s%11s%7d%13d\n", m.id, m.name, m.joinDate, m.numBorrowed, m.numRequested);
        }
    }

    public static Member getMemberById(ArrayList<Member> allMembers, String memberId) {
        for (Member member: allMembers) {
            if (member.id.equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }

    public String getMemberId(){
        return id;
    }

    public void borrowEquipmentSet() {
        numBorrowed++;
    }

    public void returnEquipmentSet() {
        numBorrowed--;
    }

    public int numOfBorrowed() {
        return numBorrowed;
    }

    public void currentMemberStatus() {
        System.out.println("["+ this.toString() +"]");
        Club myClub = Club.getInstance();
        boolean hasRecord = false;
        hasRecord = myClub.printBorrowedEquipmentSetByMember(this);

        boolean temp = false;
        temp = myClub.printRequestedEquipmentByMember(this);
        hasRecord = hasRecord || temp;
        
        if (!hasRecord) {
            System.out.println("No record.");
        }
    }

    public void requestEquipmentSet() {
        numRequested++;
    }

    public void cancelRequest() {
        numRequested--;
    }
}
