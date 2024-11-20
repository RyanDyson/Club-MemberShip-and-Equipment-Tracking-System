import java.util.*;

public class EquipmentSet implements Comparable<EquipmentSet> {
    private String equipmentSetCode;
    private boolean isAvailable;
    private Day borrowDate;
    private Day returnDate;
    private ArrayList<RequestPeriod> allRequests = new ArrayList<RequestPeriod>();
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
    public Boolean isAvailable(Day... days) {
        if (days.length == 0) {
            return isAvailable;
        } else {
            Day start = days[0];
            Day end = days[1];
            if (start == null || end == null) {
                throw new NullPointerException("start or end is null");
            }
            if (start.compareTo(returnDate) < 0) {
                return false;
            }
            for (RequestPeriod prevRequest : allRequests) {
                if (prevRequest.overlaps(start, end)) {
                    return false;
                }
            }
            return true;
        }
    }    

    public void borrowSet(Member member, Day date ) throws ExBorrowRequestPeriodOverlaps {
        if (allRequests.size() > 0) {
            for (RequestPeriod prevRequest : allRequests) {
                Day currDay = SystemDate.getInstance().clone();
                if (prevRequest.overlaps(currDay, date)) {
                    throw new ExBorrowRequestPeriodOverlaps();
                }
            }
        }
        this.borrower = member;
        this.borrowDate = SystemDate.getInstance().clone();
        this.returnDate = date;
        isAvailable = false;
    }
    public Member getBorrower() {
        return borrower;
    }
    public Day getReturnDate() {
        return returnDate;
    }
    public Day getBorrowDate() {
        return borrowDate;
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
        if (!isAvailable) {
            System.out.println("    Current status: " + borrower.toString() + " borrows for " + borrowDate.toString() + " to " + returnDate.toString());
        }  
        if (isAvailable) {
            System.out.println("    Current status: Available");
        }
        if (allRequests.size() > 0) {
            int i = 0;
            System.out.print("    Requested period(s): ");
            for (RequestPeriod prevRequest : allRequests) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(prevRequest.toString());
                i++;
            }
            System.out.println();
            return;
        }   
    }

    public EquipmentSet requestSet(Member requester, Day start, Day end, Equipment equipment) throws ExBorrowRequestPeriodOverlaps {
        if (!isAvailable) {
            Day currDay = SystemDate.getInstance().clone();
            if ((currDay.compareTo(start) >= 0 && currDay.compareTo(end) <= 0) || 
                (borrowDate.compareTo(start) >= 0 && borrowDate.compareTo(end) <= 0) || 
                (returnDate.compareTo(start) >= 0 && returnDate.compareTo(end) <= 0) ||
                (start.compareTo(borrowDate) <= 0 && end.compareTo(returnDate) >= 0)) {
                throw new ExBorrowRequestPeriodOverlaps("The period overlaps with a current period that the member borrows / requests the equipment.");
            }
        }
        if (allRequests.size() > 0) {
            for (RequestPeriod prevRequest : allRequests) {
                if (prevRequest.overlaps(start, end)) {
                    throw new ExBorrowRequestPeriodOverlaps("The period overlaps with a current period that the member borrows / requests the equipment.");
                }
            }
        }
        RequestPeriod newRequest = new RequestPeriod(start, end, requester, this, equipment.getName());
        allRequests.add(newRequest);
        Collections.sort(allRequests);
        return this;
    }

    public ArrayList<RequestPeriod> getRequestsByMember(Member requester) {
        ArrayList<RequestPeriod> requestsByMember = new ArrayList<RequestPeriod>();
        for (RequestPeriod prevRequest : allRequests) {
            if (prevRequest.getRequester().equals(requester)) {
                requestsByMember.add(prevRequest);
            }
        }
        return requestsByMember;
    }

    public void cancelRequest(Member requester, Day start, Day end) {
        for (RequestPeriod prevRequest : allRequests) {
            if (prevRequest.getRequester().equals(requester) && prevRequest.getStart().equals(start) && prevRequest.getEnd().equals(end)) {
                allRequests.remove(prevRequest);
                return;
            }
        }
    }

    public ArrayList<RequestPeriod> getAllRequests() {
        return allRequests;
    }
}
