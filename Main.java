import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {

	public static void main(String [] args) throws FileNotFoundException, ExUnknownCommand, ExInsufficientArgument {	
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
		Scanner inFile = null;
		
		try {
			inFile = new Scanner(new File(filepathname));
			String cmdLine1 = inFile.nextLine();
			String[] cmdLine1Parts = cmdLine1.split(" ");
			System.out.println("\n> "+cmdLine1);
			SystemDate.createTheInstance(cmdLine1Parts[1]);
			
			while (inFile.hasNext()) {
				String cmdLine = inFile.nextLine().trim();
				
				if (cmdLine.equals("")) continue;  

				System.out.println("\n> "+cmdLine);
				
				String[] cmdParts = cmdLine.split(" "); 
				
				try {
					if (cmdParts[0].equals("register"))
						(new CmdRegister()).execute(cmdParts);
					else if (cmdParts[0].equals("listMembers"))
						(new CmdListMembers()).execute(cmdParts);
					else if (cmdParts[0].equals("startNewDay"))
						(new CmdStartNewDay()).execute(cmdParts);
					else if (cmdParts[0].equals("create"))
						(new CmdCreate()).execute(cmdParts);
					else if (cmdParts[0].equals("arrive")) 
						(new CmdArrive()).execute(cmdParts);
					else if (cmdParts[0].equals("borrow")) 
						(new CmdBorrow()).execute(cmdParts);
						else if (cmdParts[0].equals("undo"))
						RecordedCommand.undoOneCommand();
					else if (cmdParts[0].equals("redo"))
						RecordedCommand.redoOneCommand();
					else if (cmdParts[0].equals("listEquipment"))
						(new CmdListEquipment()).execute(cmdParts);
					else {
						throw new ExUnknownCommand();
					}
				}
				catch (ExEquipmentNotFound e) {
					System.out.println(e.getMessage());
				}
				catch (ExMemberIdInUse e) {
					System.out.println(e.getMessage());
				}
				catch (ExInvalidDateFormat e) {
					System.out.println(e.getMessage());
				}
				catch (ExInsufficientArgument e) {
					System.out.println(e.getMessage());
				}
				catch (ExUnknownCommand e) {
					System.out.println(e.getMessage());
				}
				catch (ExEquipmentCodeInUse e) {
					System.out.println(e.getMessage());
				}
				finally {}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		catch (ExInvalidDateFormat e) {
			System.out.println(e.getMessage());
		} 
		finally {
			if (inFile != null) {
				inFile.close();
			}
			in.close();
		}	
	}
}