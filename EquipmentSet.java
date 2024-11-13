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
            Day requestStart = days[0];
            Day requestEnd = days[1];
            if (requestStart == null || requestEnd == null) {
                throw new NullPointerException("requestStart or requestEnd is null");
            }
            if (requestStart.compareTo(returnDate) < 0) {
                return false;
            }
            if (allRequests.size() > 0) {
                for (RequestPeriod prevRequest : allRequests) {
                    if ((requestStart.compareTo(prevRequest.start) >= 0 && requestStart.compareTo(prevRequest.end) <= 0) ||
                        (requestEnd.compareTo(prevRequest.start) >= 0 && requestEnd.compareTo(prevRequest.end) <= 0) ||
                        (requestStart.compareTo(prevRequest.start) <= 0 && requestEnd.compareTo(prevRequest.end) >= 0)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }    

    public void borrowSet(Member member, Day date ) throws ExBorrowRequestPeriodOverlaps {
        Day currDay = SystemDate.getInstance().clone();
        if (allRequests.size() > 0) {
            for (RequestPeriod prevRequest : allRequests) {
                if ((currDay.compareTo(prevRequest.start) >= 0 && currDay.compareTo(prevRequest.end) <= 0) ||
                (date.compareTo(prevRequest.start) >= 0 && date.compareTo(prevRequest.end) <= 0) ||
                (currDay.compareTo(prevRequest.start) <= 0 && date.compareTo(prevRequest.end) >= 0)) {
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
                System.out.print(prevRequest.start.toString() + " to " + prevRequest.end.toString());
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
                if ((start.compareTo(prevRequest.start) >= 0 && start.compareTo(prevRequest.end) <= 0) ||
                    (end.compareTo(prevRequest.start) >= 0 && end.compareTo(prevRequest.end) <= 0) ||
                    (start.compareTo(prevRequest.start) <= 0 && end.compareTo(prevRequest.end) >= 0)) {
                    throw new ExBorrowRequestPeriodOverlaps("The period overlaps with a current period that the member borrows / requests the equipment.");
                }
            }
        }
        RequestPeriod newRequest = new RequestPeriod();
        newRequest.start = start;
        newRequest.end = end;
        newRequest.requester = requester;
        newRequest.requestedSet = this;
        newRequest.name = equipment.getName();
        allRequests.add(newRequest);
        Collections.sort(allRequests);
        return this;
    }

    public ArrayList<RequestPeriod> getRequestsByMember(Member requester) {
        ArrayList<RequestPeriod> requestsByMember = new ArrayList<RequestPeriod>();
        for (RequestPeriod prevRequest : allRequests) {
            if (prevRequest.requester.equals(requester)) {
                requestsByMember.add(prevRequest);
            }
        }
        return requestsByMember;
    }

    public void cancelRequest(Member requester, Day start, Day end) {
        for (RequestPeriod prevRequest : allRequests) {
            if (prevRequest.requester.equals(requester) && prevRequest.start.equals(start) && prevRequest.end.equals(end)) {
                allRequests.remove(prevRequest);
                return;
            }
        }
    }

    public ArrayList<RequestPeriod> getAllRequests() {
        return allRequests;
    }
}
