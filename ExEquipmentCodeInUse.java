public class ExEquipmentCodeInUse extends Exception {
    public ExEquipmentCodeInUse(Equipment e) {
        super("Equipment code already in use: "+ e.toString(false));
    }
}
