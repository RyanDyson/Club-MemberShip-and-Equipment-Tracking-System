public class CmdListMembers implements Command{
    private Club myClub = Club.getInstance();

    @Override
    public void execute(String[] cmdParts) {
        myClub.listClubMembers();
    }
}
