public class SystemDate extends Day {
    public static SystemDate instance;
    
    private SystemDate(String sDay) throws ExInvalidDateFormat {
        super(sDay);
    }

    public static SystemDate getInstance() {
        return instance;
    }

    public static void createTheInstance(String sDay) throws ExInvalidDateFormat {        
        if (instance == null) {
            instance = new SystemDate(sDay);
            return;
        }
        System.out.println("Cannot create one more system date instance. ");
    }

    public void set(String sDay) throws ExInvalidDateFormat {
        super.set(sDay);
    }
}
