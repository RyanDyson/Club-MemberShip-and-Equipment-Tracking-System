public class ExInsufficientArgument extends Exception{
    public ExInsufficientArgument(){
        super("Insufficient command arguments.");
    }
    public ExInsufficientArgument(String message){
        super(message);
    }
}
