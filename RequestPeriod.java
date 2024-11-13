public class RequestPeriod implements Comparable<RequestPeriod> {
    public Day start;
    public Day end;
    public Member requester;
    public EquipmentSet requestedSet;
    public String name;

    public RequestPeriod() {
        start = null;
        end = null;
        requester = null;
        requestedSet = null;
    }

    public RequestPeriod(Day start, Day end, Member requester, EquipmentSet requestedSet) {
        this.start = start;
        this.end = end;
        this.requester = requester;
        this.requestedSet = requestedSet;
    }

    @Override
    public int compareTo(RequestPeriod another) {
        return this.start.compareTo(another.start);
    }
}
