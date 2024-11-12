public class ExEquipmentSetAlreadyBorrowed extends Exception {
    public ExEquipmentSetAlreadyBorrowed() {
        super("There is no available set of this equipment for the command.");
    }
    public ExEquipmentSetAlreadyBorrowed(String message) {
        super(message);
    }
}
