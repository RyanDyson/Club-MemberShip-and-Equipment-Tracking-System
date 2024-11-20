public class RequestPeriod implements Comparable<RequestPeriod> {
    private Day start;
    private Day end;
    private Member requester;
    private EquipmentSet requestedSet;
    private String name;

    public RequestPeriod() {
        start = null;
        end = null;
        requester = null;
        requestedSet = null;
    }

    public RequestPeriod(Day start, Day end, Member requester, EquipmentSet requestedSet, String aName) {
        this.start = start;
        this.end = end;
        this.requester = requester;
        this.requestedSet = requestedSet;
        this.name = aName;
    }

    @Override
    public int compareTo(RequestPeriod another) {
        return this.start.compareTo(another.start);
    }

    @Override
    public String toString() {
        return start.toString() + " to " + end.toString();
    }

    public Member getRequester() {
        return requester;
    }

    public Day getStart() {
        return start;
    }

    public Day getEnd() {
        return end;
    }

    public boolean overlaps(Day start, Day end) {
        return (
            (start.compareTo(this.start) >= 0 && start.compareTo(this.end) <= 0) || 
            (end.compareTo(this.start) >= 0 && end.compareTo(this.end) <= 0) ||
            (start.compareTo(this.start) <= 0 && end.compareTo(this.end) >= 0)
        );
    }

    public String memberReqeuststatus() {
        return "- requests " + requestedSet.toString() + " (" + name + ") for " + start.toString() + " to " + end.toString();
    }
}
