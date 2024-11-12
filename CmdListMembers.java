public class CmdListMembers implements Command{

    @Override
    public void execute(String[] cmdParts) {
        Club myClub = Club.getInstance();
        myClub.listClubMembers();
    }
}
