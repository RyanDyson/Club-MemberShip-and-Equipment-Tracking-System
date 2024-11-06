public class ExEquipmentNotFound extends Exception{
    public ExEquipmentNotFound() {
        super("Equipment not found.");
    }
    public ExEquipmentNotFound(String message) {
        super(message);
    }
}
