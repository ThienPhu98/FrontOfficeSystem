package FrontOfficeSystem.Service;

import FrontOfficeSystem.Model.Staff;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static FrontOfficeSystem.Service.SignIn.*;

public class SystemProcessManager extends SystemProcessStaff {

    public SystemProcessManager(Staff staff) {
        super(staff);
    }

    @Override
    public void mainMenu() {
        boolean isOutMainMenuStaff = false;
        do {
            System.out.print("\n============================================================================");
            System.out.print("\n|| Welcome " + staff.getName());
            System.out.print("\n||_______________________________MAIN-MENU________________________________||");
            System.out.print("\n|| 1. Enter character B to Booking                                        ||");
            System.out.print("\n|| 2. Enter character I to Check-in                                       ||");
            System.out.print("\n|| 3. Enter character O to Check-out                                      ||");
            System.out.print("\n|| 4. Enter character R to Room's Status                                  ||");
            System.out.print("\n|| 5. Enter character S to Staff Information                              ||");
            System.out.print("\n|| 6. Enter character G to Booking Status                                 ||");
            System.out.print("\n|| 7. Enter character E to exit & back to Sign-in                         ||");
            System.out.print("\n============================================================================");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);

                final int booking = 66;
                final int checkIn = 73;
                final int checkOut = 79;
                final int roomStatus = 82;
                final int information = 83;
                final int bookingStatus = 71;
                final int exit = 69;

                switch (option) {
                    case booking:
                        booking();
                        break;
                    case checkIn:
                        checkInMenu();
                        break;
                    case checkOut:
                        checkOut();
                        break;
                    case roomStatus:
                        showRoomStatus();
                        break;
                    case information:
                        informationMenu();
                        break;
                    case bookingStatus:
                        showBookingStatus();
                        break;
                    case exit:
                        System.out.print("Thank you and see you again!");
                        isOutMainMenuStaff = true;
                        signIn();
                        break;
                    default:
                        System.out.print("Request undefined! Please follow the instruction!");
                }
            } else {
                System.out.print("Request undefined! Please follow the instruction!");
            }
        } while (!isOutMainMenuStaff);
    }

    @Override
    public void informationMenu() {
        boolean isOutinformationMenu = false;
        while (!isOutinformationMenu) {
            System.out.print("\n============================================================================");
            System.out.print("\n______________________________INFORMATION-MENU______________________________");
            System.out.print("\n|| 1. Enter 'H' to see your history                                       ||");
            System.out.print("\n|| 2. Enter 'P' to change your password                                   ||");
            System.out.print("\n|| 3. Enter 'A' to see all staff history                                  ||");
            System.out.print("\n|| 4. Enter 'C' to change staff information                               ||");
            System.out.print("\n|| 5. Enter 'B' to back to main menu                                      ||");
            System.out.print("\n============================================================================");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);
                final int managerHistory = 72;
                final int changePassword = 80;
                final int staffHistory = 65;
                final int changeStaffInfo = 67;

                switch (option) {
                    case managerHistory:
                        ArrayList<Staff> staffList = outPutStaffFileToList();
                        String staffID = staff.getID();
                        System.out.print("\n________________________________Your-History________________________________");
                        for(Staff staff : staffList) {
                            if (staff.getID().equals(staffID)) {
                                if (staff.getHistory().equals("empty")) {
                                    System.out.print("\nYour history is empty!");
                                } else {
                                    System.out.print("\n" + staff.getHistory());
                                }
                            }
                        }
                        informationMenu();
                        break;
                    case changePassword:
                        boolean isPassWordValid = false;
                        ArrayList<Staff> staffListPass = outPutStaffFileToList();
                        while (!isPassWordValid) {
                            System.out.print("\n______________________________Change-Your-Password__________________________");
                            System.out.print("\nEnter your new password or 'B' to back info menu: ");
                            String password = input.nextLine();
                            if (password.toUpperCase().equals("B")) {
                                informationMenu();
                            }
                            if (password.equals(staff.getPassword())) {
                                System.out.print("\nYour new password is same with your old one!");
                            } else {
                                isPassWordValid = true;
                                for (Staff staff: staffListPass) {
                                    if (staff.getPassword().equals(password)) {
                                        isPassWordValid = false;
                                    }
                                }
                                if (!isPassWordValid) {
                                    System.out.print("Your new password is invalid! please try again!");
                                } else {
                                    Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{7,12}$");
                                    Matcher matcher = pattern.matcher(password);
                                    String staffIDPass = staff.getID();
                                    if (matcher.matches()) {
                                        for (Staff staff : staffListPass) {
                                            if (staff.getID().equals(staffIDPass)) {
                                                staff.setPassword(password);
                                            }
                                        }
                                        writeStaffListToFile(staffListPass);
                                        System.out.print("\n-----------------------------CHANGE-PASSWORD-SUCCESS------------------------");
                                        informationMenu();
                                    } else {
                                        System.out.print("Your new password is invalid! password must be a-z with 0-9 and 7 to 12 character!");
                                        isPassWordValid = false;
                                    }
                                }
                            }
                        }
                        break;
                    case staffHistory:
                        System.out.print("\n________________________________All-Staff-History___________________________");
                        ArrayList<Staff> staffListHistory = outPutStaffFileToList();
                        for (Staff staff: staffListHistory) {
                            System.out.print("\n" + staff.getName() + " {" + staff.getLevel() + "}: ");
                            if (staff.getHistory().equals("empty")) {
                                System.out.print("\nthis history is empty!");
                            } else {
                                System.out.print("\n" + staff.getHistory());
                            }
                        }
                        System.out.print("\n============================================================================");
                        informationMenu();
                        break;
                    case changeStaffInfo:
                        staffInfo();
                        break;
                    case backToThePreviousMenu:
                        isOutinformationMenu = true;
                        mainMenu();
                        break;
                    default:
                        System.out.print("Request undefined! Please follow the instruction!");
                }
            } else {
                System.out.print("Request undefined! Please follow the instruction!");
            }
        }
    }

    public void staffInfo() {
        boolean isOutChangeStaffInfo = false;
        while (!isOutChangeStaffInfo) {
            System.out.print("\n============================================================================");
            System.out.print("\n____________________________STAFF-INFORMATION-MENU__________________________");
            System.out.print("\n|| 1. Enter 'I' to see all staff Information                              ||");
            System.out.print("\n|| 2. Enter 'A' to add new staff                                          ||");
            System.out.print("\n|| 3. Enter 'R' to remove a staff                                         ||");
            System.out.print("\n|| 4. Enter 'C' to change staff information                               ||");
            System.out.print("\n|| 5. Enter 'B' to back to information menu                               ||");
            System.out.print("\n============================================================================");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);
                final int allStaffInfo = 73;
                final int addStaff = 65;
                final int removeStaff = 82;
                final int changeStaffInfo = 67;

                switch (option) {
                    case allStaffInfo:
                        showStaffInfo();
                        break;
                    case addStaff:
                        addStaff();
                        break;
                    case removeStaff:
                        removeStaff();
                        break;
                    case changeStaffInfo:
                        changeStaffInfo();
                        break;
                    case backToThePreviousMenu:
                        isOutChangeStaffInfo = true;
                        informationMenu();
                        break;
                    default:
                        System.out.print("\nRequest undefined! Please follow the instruction!");
                }
            } else {
                System.out.print("\nRequest undefined! Please follow the instruction!");
            }
        }
        informationMenu();
    }

    public void addStaff() {
        ArrayList<Staff> staffList = outPutStaffFileToList();
        System.out.print("\n============================================================================");
        System.out.print("\n_______________________________Add-New-Staff________________________________");
        String staffName = "";
        do {
            System.out.print("\nEnter Name for Staff or press B to back to Staff information menu: ");
            String staffInputName = input.nextLine();
            if (staffInputName.toUpperCase().equals("B")) {
                staffInfo();
            } else if (staffInputName.trim().length() == 0) {
                System.out.print("\nInput name is invalid! Guest's name can't be null or blank");
            } else {
                staffName = toTitleCase(staffInputName);
            }
        } while (staffName.equals(""));

        String password = "";
        boolean isPassWordValid = false;
        while (!isPassWordValid) {
            System.out.print("\nEnter password for staff or 'B' to back to Staff information menu: ");
            password = input.nextLine();
            if (password.toUpperCase().equals("B")) {
                informationMenu();
            } else {
                isPassWordValid = true;
                for (Staff staff: staffList) {
                    if (staff.getPassword().equals(password)) {
                        isPassWordValid = false;
                    }
                }
                if (!isPassWordValid) {
                    System.out.print("\nYour new password is invalid! please try again!");
                } else {
                    Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{7,12}$");
                    Matcher matcher = pattern.matcher(password);
                    if (!matcher.matches()) {
                        System.out.print("\nYour new password is invalid! password must be a-z with 0-9 and 7 to 12 character!");
                        isPassWordValid = false;
                    }
                }
            }
        }

        String history = "empty";

        String level = "";
        boolean isLevelOfStaffValid = false;
        while (!isLevelOfStaffValid) {
            System.out.print("\n____________________________________________________________________________");
            System.out.print("\n|| Level for Staff:                                                       ||");
            System.out.print("\n|| 1.Enter 'S' Level = 'Staff'                                            ||");
            System.out.print("\n|| 2.Enter 'M' Level = 'Manager'                                          ||");
            System.out.print("\n|| 3.Enter 'B' to back to Staff information menu                          ||");
            System.out.print("\n||Enter your option: ");
            String choice = input.nextLine();
            final int Staff = 83;
            final int Manager = 77;
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);
                switch (option) {
                    case Staff:
                        level = "staff";
                        isLevelOfStaffValid = true;
                        break;
                    case Manager:
                        level = "manager";
                        isLevelOfStaffValid = true;
                        break;
                    case backToThePreviousMenu:
                        informationMenu();
                        break;
                    default:
                        System.out.print("\nRequest undefined! Please follow the instruction!");
                }
            } else {
                System.out.print("\nRequest undefined! Please follow the instruction!");
            }
        }

        String staffID = "";
        do {
            int randomNumber = (int) (Math.random() * 99999) + 10000;
            staffID = String.valueOf(randomNumber);
        } while (!isStaffIDValid(staffID));

        Staff newStaff = new Staff(staffName, password, history, level, staffID);
        System.out.print("\n_____________________________Confirm-Staff-Information______________________");
        System.out.print("\n" + newStaff.toString());
        System.out.print("\n============================================================================");
        boolean isConfirmAddStaff = false;
        while (!isConfirmAddStaff) {
            System.out.print("\nEnter 'A' to Accept and 'B' to cancel and back to Staff information menu: ");
            String choice = input.nextLine();
            if (choice.toUpperCase().equals("A")) {
                isConfirmAddStaff = true;
                staffList.add(newStaff);
                writeStaffListToFile(staffList);
                System.out.print("\n______________________________ADD-NEW-STAFF-SUCCESS_________________________");
            } else if (choice.toUpperCase().equals("B")) {
                informationMenu();
            } else {
                System.out.print("\nRequest undefined! Please follow the instruction!");
            }
        }
        informationMenu();
    }

    public boolean isStaffIDValid(String staffID) {
        ArrayList<Staff> staffList = outPutStaffFileToList();
        for (Staff staff : staffList) {
            if (staff.getID().equals(staffID)) {
                return false;
            }
        }
        return true;
    }

    public void showStaffInfo() {
        ArrayList<Staff> staffList = outPutStaffFileToList();
        System.out.print("\n============================================================================");
        System.out.print("\n________________________________All-Staff-Info______________________________");
        for (Staff staff: staffList) {
            System.out.print("\n" + staff.toString());
        }
        System.out.print("\n============================================================================");
    }

    public void removeStaff() {
        ArrayList<Staff> staffList = outPutStaffFileToList();
        showStaffInfo();
        boolean isRemoveStaff = false;
        String staffRemoveID = "";
        while (!isRemoveStaff) {
            System.out.print("\n============================================================================");
            System.out.print("\nEnter staff ID you want to remove or 'B' to back to Staff information menu: ");
            String staffID = input.nextLine();
            if(staffID.toUpperCase().equals("B")) {
                isRemoveStaff = true;
                informationMenu();
            }
            for(Staff staff : staffList) {
                if (staff.getID().equals(staffID)) {
                    isRemoveStaff = true;
                }
            }
            if(isRemoveStaff) {
                staffList.removeIf(staff -> staff.getID().equals(staffID));
                staffRemoveID = staffID;
                System.out.print("\n============================================================================");
                System.out.print("\n_______________________________REMOVE-STAFF-SUCCESS_________________________");
            } else {
                System.out.print("\nstaffID is invalid! Please enter again!");
            }
        }
        writeStaffListToFile(staffList);
        if (staff.getID().equals(staffRemoveID)) {
            signIn();
        } else {
            informationMenu();
        }
    }

    public void changeStaffInfo() {
        ArrayList<Staff> staffList = outPutStaffFileToList();
        showStaffInfo();
        boolean isOutChangeStaffInfo = false;
        while (!isOutChangeStaffInfo) {
            System.out.print("Enter StaffID to change information or 'B' to back to Staff information menu: ");
            String staffID = input.nextLine();
            if (staffID.toUpperCase().equals("B")) {
                isOutChangeStaffInfo = true;
                informationMenu();
            }
            for (Staff staff: staffList) {
                if (staff.getID().equals(staffID)) {
                    staff = changeInformation(staff);
                    isOutChangeStaffInfo = true;
                }
            }
            if (!isOutChangeStaffInfo) {
                System.out.print("\nstaffID is invalid! Please enter again!");
            }
        }
        writeStaffListToFile(staffList);
        System.out.print("\n-----------------------CHANGE-STAFF-INFORMATION-SUCCESS---------------------");
        informationMenu();
    }

    public Staff changeInformation (Staff staff) {
        System.out.print("\n============================================================================");
        System.out.print("\n______________________________Staff-Information_____________________________");
        System.out.print("\n" + staff.toString());
        boolean isOutChangeInformation = false;
        while (!isOutChangeInformation) {
            System.out.print("\n============================================================================");
            System.out.print("\n____________________________CHANGE-INFORMATION-MENU_________________________");
            System.out.print("\n|| 1. Enter 'A' to accept information                                     ||");
            System.out.print("\n|| 2. Enter 'N' to change Name                                            ||");
            System.out.print("\n|| 3. Enter 'P' to change Password                                        ||");
            System.out.print("\n|| 4. Enter 'L' to change Level                                           ||");
            System.out.print("\n|| 5. Enter 'B' to back to Staff information menu                         ||");
            System.out.print("\n____________________________________________________________________________");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            final int accept = 65;
            final int changeName = 78;
            final int changePassword = 80;
            final int changeLevel = 76;
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);
                switch (option) {
                    case accept:
                        isOutChangeInformation = true;
                        break;
                    case changeName:
                        String staffName = "";
                        do {
                            System.out.print("\nEnter new name for Staff or press B to back to change Information menu: ");
                            String staffInputName = input.nextLine();
                            if (staffInputName.toUpperCase().equals("B")) {
                                staff = changeInformation(staff);
                            } else if (staffInputName.trim().length() == 0) {
                                System.out.print("\nInput name is invalid! Guest's name can't be null or blank");
                            } else {
                                staffName = toTitleCase(staffInputName);
                                staff.setName(staffName);
                                System.out.print("\n__________________________CHANGE-STAFF-NAME-SUCCESS_________________________");
                                staff = changeInformation(staff);
                            }
                        } while (staffName.equals(""));
                        isOutChangeInformation = true;
                        break;
                    case changePassword:
                        String password = "";
                        boolean isPassWordValid = false;
                        while (!isPassWordValid) {
                            System.out.print("\nEnter your new password or 'B' to back to change information menu: ");
                            password = input.nextLine();
                            if (password.toUpperCase().equals("B")) {
                                staff = changeInformation(staff);
                            } else {
                                isPassWordValid = true;
                                ArrayList<Staff> staffList = outPutStaffFileToList();
                                for (Staff staffObj: staffList) {
                                    if (staffObj.getPassword().equals(password)) {
                                        isPassWordValid = false;
                                    }
                                }
                                if (!isPassWordValid) {
                                    System.out.print("\nYour new password is invalid! please try again!");
                                } else {
                                    Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{7,12}$");
                                    Matcher matcher = pattern.matcher(password);
                                    if (!matcher.matches()) {
                                        System.out.print("\nYour new password is invalid! password must be a-z with 0-9 and 7 to 12 character!");
                                        isPassWordValid = false;
                                    } else {
                                        staff.setPassword(password);
                                        System.out.print("\n________________________CHANGE-STAFF-PASSWORD-SUCCESS_______________________");
                                        staff = changeInformation(staff);
                                    }
                                }
                            }
                        }
                        isOutChangeInformation = true;
                        break;
                    case changeLevel:
                        String level = "";
                        boolean isLevelOfStaffValid = false;
                        while (!isLevelOfStaffValid) {
                            System.out.print("\n____________________________________________________________________________");
                            System.out.print("\n|| Level for Staff:                                                       ||");
                            System.out.print("\n|| 1.Enter 'S' Level = 'Staff'                                            ||");
                            System.out.print("\n|| 2.Enter 'M' Level = 'Manager'                                          ||");
                            System.out.print("\n|| 3.Enter 'B' to back to change information menu                         ||");
                            System.out.print("\n||Enter your option: ");
                            String choiceLevel = input.nextLine();
                            final int Staff = 83;
                            final int Manager = 77;
                            if (choiceLevel.length() == 1) {
                                int optionLevel = choice.toUpperCase().charAt(0);
                                switch (optionLevel) {
                                    case Staff:
                                        level = "staff";
                                        System.out.print("\n__________________________CHANGE-STAFF-LEVEL-SUCCESS________________________");
                                        staff.setLevel(level);
                                        staff = changeInformation(staff);
                                        isLevelOfStaffValid = true;
                                        break;
                                    case Manager:
                                        level = "manager";
                                        System.out.print("\n__________________________CHANGE-STAFF-LEVEL-SUCCESS________________________");
                                        staff.setLevel(level);
                                        staff = changeInformation(staff);
                                        isLevelOfStaffValid = true;
                                        break;
                                    case backToThePreviousMenu:
                                        staff = changeInformation(staff);
                                        isLevelOfStaffValid = true;
                                        break;
                                    default:
                                        System.out.print("\nRequest undefined! Please follow the instruction!");
                                }
                            } else {
                                System.out.print("\nRequest undefined! Please follow the instruction!");
                            }
                        }
                        isOutChangeInformation = true;
                        break;
                    case backToThePreviousMenu:
                        staffInfo();
                        break;
                    default:
                        System.out.print("\nRequest undefined! Please follow the instruction!");
                }
            } else  {
                System.out.print("\nRequest undefined! Please follow the instruction!");
            }
        }
        return staff;
    }
}
