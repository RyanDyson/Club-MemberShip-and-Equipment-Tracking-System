# Club-Membership-and-Equipment-Tracking-System

This program was written for the assignment of course CS 2312. It takes in a txt file as its input and reads the commands in it.

The full assignment requirements are under the testcases folder

## DISCLAIMER

This program and its publication is meant for educational and personal uses only. If you are a CityU student, I am not responsible for any plagiarism or any academic dishonety that you do. Do not copy for your own sake.

## How To Run

1. Download the files in the repo or clone the repo.
2. Open a terminal and navigate to the project directory.
3. Compile the Java files:
   ```sh
   javac *.java
   ```
4. Run the program:
   ```sh
   java Main
   ```
5. When prompted, input the file pathname containing the commands, for example:
   ```sh
   testcases/1a.txt
   ```

## Commands

The program supports the following commands:

- `startNewDay <date>`: Starts a new day with the given date.
- `register <memberId> <memberName>`: Registers a new member with the given ID and name.
- `create <equipmentCode> <equipmentName>`: Creates a new equipment with the given code and name.
- `arrive <equipmentCode>`: Marks the arrival of an equipment set with the given code.
- `borrow <memberId> <equipmentCode> [<numDays>]`: Borrows an equipment set for the member for the specified number of days (default is 7 days).
- `request <memberId> <equipmentCode> <startDate> <numDays>`: Requests an equipment set for the member for the specified period.
- `listMembers`: Lists all registered members.
- `listEquipment`: Lists all equipment.
- `listEquipmentStatus`: Lists the status of all equipment.
- `listMemberStatus`: Lists the status of all members.
- `undo`: Undoes the last command.
- `redo`: Redoes the last undone command.

## Example

Here is an example of a command file (`testcases/1a.txt`):

```txt
startNewDay 03-Jan-2024
register 001 helena
register 002 jason
listMembers
register 003 kit
register 004 karen
listMembers
```
