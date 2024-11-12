public class ExEquipmentNotFound extends Exception{
    public ExEquipmentNotFound() {
        super("Equipment record not found.");
    }
    public ExEquipmentNotFound(String message) {
        super(message);
    }
}
