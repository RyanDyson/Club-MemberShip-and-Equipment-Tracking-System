public class ExMemberIdInUse extends Exception{ 
    public ExMemberIdInUse(Member member) {
        super("Member ID already in use: " + member.toString());
    }
}