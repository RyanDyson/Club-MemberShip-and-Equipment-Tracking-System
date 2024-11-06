public class CmdListEquipment implements Command {
    public void execute(String[] cmdParts) {
        Club.getInstance().listEquipment();
    }
}
