package FrontOfficeSystem.Service;

import FrontOfficeSystem.Model.*;
import FrontOfficeSystem.Service.Tools.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.*;

import static FrontOfficeSystem.Service.SignIn.*;

public class SystemProcessStaff implements ISystemProcess {
    static Staff staff;
    static Scanner input = new Scanner(System.in);
    static DecimalFormat formatter = new DecimalFormat("###,###,###");
    static final int backToThePreviousMenu = 66;
    static String dataFolder = "H:\\Study & Working\\module2\\CaseStudy\\src\\FrontOfficeSystem\\Data\\";
    static String fileBookingGuestList = "BookingList.json";
    static String fileRoomList = "RoomList.json";
    static String fileInHouseGuestList = "InHouseGuestList.json";

    public SystemProcessStaff(Staff staff) {
        this.staff = staff;
    }

    @Override
    public void mainMenu() {
        boolean isOutMainMenuStaff = false;
        do {
            System.out.print("\n====================================================");
            System.out.print("\n||___________________MAIN-MENU____________________||");
            System.out.print("\n|| 1. Enter character B to Booking                ||");
            System.out.print("\n|| 2. Enter character I to Check-in               ||");
            System.out.print("\n|| 3. Enter character O to Check-out              ||");
            System.out.print("\n|| 4. Enter character R to Room's Status          ||");
            System.out.print("\n|| 5. Enter character Y to Your Information       ||");
            System.out.print("\n|| 6. Enter character G to Booking Status         ||");
            System.out.print("\n|| 7. Enter character E to exit & back to Sign-in ||");
            System.out.print("\n====================================================");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);

                final int booking = 66;
                final int checkIn = 73;
                final int checkOut = 79;
                final int roomStatus = 82;
                final int information = 89;
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
    public void booking() {
        Guest guest = takeGuestInformation();
        inPutBookingListToFile(guest);
        String history = "Get booking for guest with Booking Code: " + guest.getBookingCode();
        addStaffHistory(history);
        System.out.print("\n-------------Booking successful!---------------");
        mainMenu();
    }

    @Override
    public void checkInMenu() {
        boolean isOutCheckIn = false;
        do {
            System.out.print("\n==================================================================");
            System.out.print("\n__________________________CHECK-IN________________________________");
            System.out.print("\n||1. Enter 'R' to check-in for guest have reservation           ||");
            System.out.print("\n||2. Enter 'N' to check-in for guest doesn't have reservation   ||");
            System.out.print("\n||3. Enter 'B' to back to main menu                             ||");
            System.out.print("\n------------------------------------------------------------------");
            System.out.print("\n||Enter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);

                final int checkInReservation = 82;
                final int checkInNonReservation = 78;

                switch (option) {
                    case checkInReservation:
                        checkInWithReservation();
                        break;
                    case checkInNonReservation:
                        checkInWithoutReservation();
                        break;
                    case backToThePreviousMenu:
                        isOutCheckIn = true;
                        mainMenu();
                        break;
                    default:
                        System.out.print("Request undefined! Please follow the instruction!");
                }
                isOutCheckIn = true;
            } else {
                System.out.print("Request undefined! Please follow the instruction!");
            }
        } while (!isOutCheckIn);
    }

    public void checkInWithReservation() {
        boolean isOutCheckInSystem = false;
        do {
            System.out.print("\n==================================================================");
            System.out.print("\n_________________LOOKING-FOR-RESERVATION__________________________");
            System.out.print("\n||1. Enter 'N' to find reservation by name                      ||");
            System.out.print("\n||2. Enter 'C' to find reservation by booking code              ||");
            System.out.print("\n||3. Enter 'B' to back to Check-in menu                         ||");
            System.out.print("\n------------------------------------------------------------------");
            System.out.print("\n||Enter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);
                final int byName = 78;
                final int byBookingCode = 67;

                switch (option) {
                    case byName:
                        isOutCheckInSystem = true;
                        boolean isNameValid = false;
                        while (!isNameValid) {
                            System.out.print("\nEnter guest's name: ");
                            String name = input.nextLine();
                            if (name.trim().length() == 0) {
                                System.out.print("\nGuest's name can't be null or blank");
                            } else {
                                checkInGuestByName(name);
                            }
                        }
                        break;
                    case byBookingCode:
                        isOutCheckInSystem = true;
                        boolean isBookingCodeValid = false;
                        while (!isBookingCodeValid) {
                            System.out.print("\nEnter guest's booking code: ");
                            String inputBookingCode = input.nextLine();
                            if (inputBookingCode.charAt(0) == '#') {
                                System.out.print("\nBooking Code must be number! please do not enter '#'");
                            } else {
                                Pattern pattern = Pattern.compile("\\d*");
                                Matcher matcher = pattern.matcher(inputBookingCode);
                                if (matcher.matches()) {
                                    String bookingCode = "#" + inputBookingCode;
                                    isBookingCodeValid = true;
                                    checkInGuestByBookingCode(bookingCode);
                                } else {
                                    System.out.print("\nBooking Code must be number!");
                                }
                            }
                        }
                        break;
                    case backToThePreviousMenu:
                        mainMenu();
                        break;
                    default:
                        System.out.print("Request undefined! Please follow the instruction!");
                }
            } else {
                System.out.print("Request undefined! Please follow the instruction!");
            }
        } while (!isOutCheckInSystem);
    }

    public void checkInWithoutReservation() {
        Guest guest = takeGuestInformation();
        inPutBookingListToFile(guest);
        checkInGuestByBookingCode(guest.getBookingCode());
        mainMenu();
    }

    @Override
    public void checkOut() {
        ArrayList<Room> roomList = outPutRoomFileToList();
        ArrayList<Guest> inHouseList = outPutInHouseFileToList();
        boolean isRoomNumberValid = false;
        String roomNumber = "";
        System.out.print("\n==================================================================");
        System.out.print("\n__________________________CHECK-OUT_______________________________");
        do {
            System.out.print("\n||Enter guest's room or press 'B' to back to main menu: ");
            String choice = input.nextLine();
            if (choice.toUpperCase().equals("B")) {
                mainMenu();
            } else {
                roomNumber = choice.trim();
                boolean isRoomVacant = false;
                for (Room room : roomList) {
                    if (room.getNumber().equals(roomNumber)) {
                        if (room.isVacant()) {
                            System.out.print("This room is Empty!");
                            isRoomVacant = true;
                        } else {
                            isRoomNumberValid = true;
                        }
                    }
                }
                if (!isRoomVacant && !isRoomNumberValid) {
                    System.out.print("\nInvalid Room number! please try again!");
                }
            }
        } while (!isRoomNumberValid);

        System.out.print("\n_______________________CONFIRM-CHECK-OUT__________________________");
        System.out.print("\n||Room" + roomNumber + ": ");
        String guestBookingCode = "";
        for (Room room : roomList) {
            if (room.getNumber().equals(roomNumber)) {
                for (Guest guest : inHouseList) {
                    if (guest.getBookingCode().equals(room.getGuestID())) {
                        System.out.print(guest.toString());
                        guestBookingCode = guest.getBookingCode();
                    }
                }
            }
        }
        System.out.print("\n------------------------------------------------------------------");
        boolean isConfirmAccept = false;
        do {
            System.out.print("\nPress 'Y' to accept check-Out guest or 'B' to back to Check-Out menu: ");
            String choice = input.nextLine();
            if (choice.toUpperCase().equals("Y")) {
                isConfirmAccept = true;
                checkOutGuestToRoom(roomNumber);
                String history = "Check-out guest with booking-code: " + guestBookingCode;
                addStaffHistory(history);
                System.out.print("\n_______________________CHECK-OUT-COMPLETED________________________");
                mainMenu();
            } else if (choice.toUpperCase().equals("B")) {
                isConfirmAccept = true;
                checkOut();
            } else {
                System.out.print("\nRequest undefined! Please follow the instruction!");
            }
        } while (!isConfirmAccept);
    }

    @Override
    public void showRoomStatus() {
        ArrayList<Room> roomList = outPutRoomFileToList();
        ArrayList<Guest> inHouseList = outPutInHouseFileToList();
        System.out.print("\n==================================================================");
        System.out.print("\n______________________SHOW-ROOM-STATUS____________________________");
        for (Room room : roomList) {
            System.out.print("\n||{" + room.getNumber() + ":");
            if (room.getGuestID() != null) {
                for (Guest guest : inHouseList) {
                    if (room.getGuestID().equals(guest.getBookingCode())) {
                        System.out.print(guest.toString());
                    }
                }
            } else {
                System.out.print("Empty");
            }
            System.out.print("}");
        }
        System.out.print("\n------------------------------------------------------------------");
        boolean isOutShowRoomStatus = false;
        do {
            System.out.print("\nPress 'B' to back to main menu: ");
            String choice = input.nextLine();
            if (choice.toUpperCase().equals("B")) {
                isOutShowRoomStatus = true;
                mainMenu();
            } else {
                System.out.print("\nRequest undefined! Please follow the instruction!");
            }
        } while (!isOutShowRoomStatus);
    }

    @Override
    public void informationMenu() {
        boolean isOutinformationMenu = false;
        while (!isOutinformationMenu) {
            System.out.print("\n====================================================");
            System.out.print("\n_______________Your-Information-Menu________________");
            System.out.print("\n||1. Enter 'H' to see your history                ||");
            System.out.print("\n||2. Enter 'P' to change your password            ||");
            System.out.print("\n||3. Enter 'B' to back to main menu               ||");
            System.out.print("\n====================================================");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);
                final int history = 72;
                final int changePassword = 80;
                switch (option) {
                    case history:
                        ArrayList<Staff> staffList = outPutStaffFileToList();
                        String staffID = staff.getID();
                        System.out.print("\n_____________________Your-History___________________");
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
                        ArrayList<Staff> staffListPass = outPutStaffFileToList();
                        boolean isPassWordValid = false;
                        while (!isPassWordValid) {
                            System.out.print("\n_________________Change-Your-Password_______________");
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
                                    System.out.print("\nYour new password is invalid! please try again!");
                                } else {
                                    Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{7,12}$");
                                    Matcher matcher = pattern.matcher(password);
                                    if (matcher.matches()) {
                                        String staffIDPass = staff.getID();
                                        for (Staff staff : staffListPass) {
                                            if (staff.getID().equals(staffIDPass)) {
                                                staff.setPassword(password);
                                            }
                                        }
                                        writeStaffListToFile(staffListPass);
                                        informationMenu();
                                    } else {
                                        System.out.print("\nYour new password is invalid! password must be a-z with 0-9 and 7 to 12 character!");
                                        isPassWordValid = false;
                                    }
                                }
                            }
                        }
                        break;
                    case backToThePreviousMenu:
                        isOutinformationMenu = true;
                        mainMenu();
                        break;
                    default:
                        System.out.print("\nRequest undefined! Please follow the instruction!");
                }
            } else {
                System.out.print("\nRequest undefined! Please follow the instruction!");
            }
        }
    }

    @Override
    public void showBookingStatus() {
        System.out.print("\n==================================================================");
        System.out.print("\n________________________BOOKING-STATUS____________________________");
        ArrayList<Guest> guestList = outPutBookingFileToList();
        if (guestList.size() != 0) {
            for (Guest guest : guestList) {
                System.out.print("\n||{" + guest.toString() + "}");
            }
            System.out.print("\n------------------------------------------------------------------");
            boolean isBookingCodeValid = false;
            do {
                System.out.print("\n Enter 'Booking-Code' to change information of 'B' to back to main menu: ");
                String choice = input.nextLine();
                if (choice.toUpperCase().equals("B")) {
                    isBookingCodeValid = true;
                    mainMenu();
                } else if (choice.charAt(0) == '#') {
                    System.out.print("\nBooking code must be number! please do not enter '#'!");
                } else {
                    String bookingCode = "#" + choice;
                    for (Guest guest : guestList) {
                        if (guest.getBookingCode().equals(bookingCode)) {
                            isBookingCodeValid = true;
                            guest = changeBookingInformation(guest);
                            String history = "Change booking information with booking code:" + guest.getBookingCode();
                            addStaffHistory(history);
                            System.out.print("\n________________CHANGE-INFORMATION-COMPLETED______________________");
                        }
                    }
                    if (!isBookingCodeValid) {
                        System.out.print("\nBooking Code is invalid! please enter again!");
                    }
                }
            } while (!isBookingCodeValid);

            JSONArray guestJSONList = new JSONArray();
            for (Guest guestObject : guestList) {
                JSONObject guestDetail = new JSONObject();
                guestDetail.put("name", guestObject.getName());
                guestDetail.put("phoneNumber", guestObject.getPhoneNumber());
                guestDetail.put("dayArrival", guestObject.getDayArrival());
                guestDetail.put("guaranteeFee", guestObject.getGuaranteeFee());
                guestDetail.put("methodPayment", guestObject.getMethodPayment());
                guestDetail.put("bookingCode", guestObject.getBookingCode());

                JSONObject guestJSON = new JSONObject();
                guestJSON.put("guest", guestDetail);

                guestJSONList.add(guestJSON);
            }

            try (FileWriter file = new FileWriter(dataFolder + fileBookingGuestList)) {
                file.write(guestJSONList.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.print("\n------------------Update Information Successful!------------------");
            showBookingStatus();
        } else {
            System.out.print("\nNothing to show, It isn't have reservation yet!");
            System.out.print("\n------------------------------------------------------------------");
            mainMenu();
        }
    }

    public Guest takeGuestInformation() {
        System.out.print("\n==============================================================");
        String guestName = "";
        do {
            System.out.print("\nEnter Guest's name or press B to back Main menu: ");
            String inputName = input.nextLine();
            if (inputName.toUpperCase().equals("B")) {
                mainMenu();
            } else if (inputName.trim().length() == 0) {
                System.out.print("\nInput name is invalid! Guest's name can't be null or blank");
            } else {
                guestName = toTitleCase(inputName);
            }
        } while (guestName.equals(""));

        String inputPhoneNumber = "";
        boolean checkPhoneNumber = false;
        do {
            System.out.print("\nEnter Guest's phone number or press B to back Main menu: ");
            inputPhoneNumber = input.nextLine();
            if (inputPhoneNumber.length() == 11) {
                Pattern pattern = Pattern.compile("^[0]\\d{10}");
                Matcher matcher = pattern.matcher(inputPhoneNumber);
                if (matcher.matches()) {
                    checkPhoneNumber = true;
                } else {
                    System.out.print("\nInput phone number is invalid!");
                    System.out.print("\nPhone number must be number with length equal 11 and start with '0'");
                }
            } else if (inputPhoneNumber.toUpperCase().equals("B")) {
                checkPhoneNumber = true;
                mainMenu();
            } else {
                System.out.print("\nInput phone number is invalid!");
                System.out.print("\nPhone number must be number with length equal 11 and start with '0'");
            }
        } while (!checkPhoneNumber);

        String inputDayArrival = "";
        boolean isDayArrivalSuitable = false;
        do {
            System.out.print("\nEnter Day Guest Arrive or press B to back Main menu: ");
            inputDayArrival = input.nextLine();
            if (inputDayArrival.toUpperCase().equals("B")) {
                isDayArrivalSuitable = true;
                mainMenu();
            } else {
                DateValidatorUsingDateFormat validator = new DateValidatorUsingDateFormat("dd/MM/yyyy");
                if (validator.isValid(inputDayArrival)) {
                    isDayArrivalSuitable = true;
                } else {
                    System.out.print("\nInput arrival day is invalid!, day must be follow dd/mm/yyyy");
                }
            }
        } while (!isDayArrivalSuitable);

        String inputMoneyGuarantee = "";
        boolean isMoneySuitable = false;
        do {
            System.out.print("\nEnter Amount money Guest make guarantee or press B to back Main menu: ");
            inputMoneyGuarantee = input.nextLine();
            if (inputMoneyGuarantee.toUpperCase().equals("B")) {
                isMoneySuitable = true;
                mainMenu();
            } else if (inputMoneyGuarantee.length() < 10) {
                Pattern pattern = Pattern.compile("\\d*");
                Matcher matcher = pattern.matcher(inputMoneyGuarantee);
                if (matcher.matches()) {
                    int moneyGuarantee = Integer.parseInt(inputMoneyGuarantee);
                    System.out.print("\nConfirm Amount money Input: " + formatter.format(moneyGuarantee) + " VND");
                    System.out.print("\nPress Y to continue or any key to retype: ");
                    String choice = input.nextLine();
                    if (choice.toUpperCase().equals("Y")) {
                        inputMoneyGuarantee = String.valueOf(formatter.format(moneyGuarantee));
                        isMoneySuitable = true;
                    }
                } else {
                    System.out.print("\nInput money is invalid! Amount must be from '0' to less than 100 million");
                }
            } else {
                System.out.print("\nInput money is invalid! Amount must be from '0' to less than 100 million");
            }
        } while (!isMoneySuitable);

        System.out.print("\nOur Hotel accept 2 type of method payment: cash, credit-card");
        System.out.print("\nEnter Amount money Guest make guarantee or press B to back Main menu: ");
        String methodPayment = input.nextLine();

        String bookingCode = "";
        do {
            int randomNumber = (int) (Math.random() * 99999) + 10000;
            bookingCode = "#" + randomNumber;
        } while (!isBookingCodeSuitable(bookingCode));

        Guest reservation = new Guest(guestName, inputPhoneNumber, inputDayArrival, inputMoneyGuarantee, methodPayment, bookingCode);
        Guest guest = changeBookingInformation(reservation);
        return guest;
    }

    public String toTitleCase(String inputString) {
        String[] arr = inputString.split(" ");
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            stringBuffer.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return stringBuffer.toString().trim();
    }

    public boolean isBookingCodeSuitable(String bookingCode) {
        ArrayList<Guest> guestList = outPutBookingFileToList();
        if (guestList == null) {
            return true;
        } else {
            for (Guest guest : guestList) {
                if (guest.getBookingCode().equals(bookingCode)) {
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Guest> outPutBookingFileToList() {
        ArrayList<Guest> guestList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(dataFolder + fileBookingGuestList)) {
            Object obj = jsonParser.parse(reader);

            JSONArray guestFile = (JSONArray) obj;
            guestFile.forEach(guest -> writeGuestFileToList((JSONObject) guest, guestList));

        } catch (IOException | ParseException e) {
            return guestList;
        }
        return guestList;
    }

    public void writeGuestFileToList(JSONObject guest, ArrayList<Guest> guestList) {
        JSONObject guestFile = (JSONObject) guest.get("guest");
        if (guestFile != null) {
            Guest guestObj = new Guest();
            guestObj.setName((String) guestFile.get("name"));
            guestObj.setPhoneNumber((String) guestFile.get("phoneNumber"));
            guestObj.setDayArrival((String) guestFile.get("dayArrival"));
            guestObj.setGuaranteeFee((String) guestFile.get("guaranteeFee"));
            guestObj.setBookingCode((String) guestFile.get("bookingCode"));
            guestObj.setMethodPayment((String) guestFile.get("methodPayment"));

            guestList.add(guestObj);
        }
    }

    public void inPutBookingListToFile(Guest guest) {
        ArrayList<Guest> guestList = outPutBookingFileToList();
        CompareGuestByDate compareByDate = new CompareGuestByDate();
        Collections.sort(guestList, compareByDate);
        guestList.add(guest);

        JSONArray guestJSONList = new JSONArray();
        for (Guest guestObject : guestList) {
            JSONObject guestDetail = new JSONObject();
            guestDetail.put("name", guestObject.getName());
            guestDetail.put("phoneNumber", guestObject.getPhoneNumber());
            guestDetail.put("dayArrival", guestObject.getDayArrival());
            guestDetail.put("guaranteeFee", guestObject.getGuaranteeFee());
            guestDetail.put("methodPayment", guestObject.getMethodPayment());
            guestDetail.put("bookingCode", guestObject.getBookingCode());

            JSONObject guestJSON = new JSONObject();
            guestJSON.put("guest", guestDetail);

            guestJSONList.add(guestJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileBookingGuestList)) {
            file.write(guestJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Room> outPutRoomFileToList() {
        ArrayList<Room> roomList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(dataFolder + fileRoomList)) {
            Object obj = jsonParser.parse(reader);

            JSONArray roomFile = (JSONArray) obj;
            roomFile.forEach(room -> writeRoomFileToList((JSONObject) room, roomList));

        } catch (IOException | ParseException e) {
            return roomList;
        }
        return roomList;
    }

    public void writeRoomFileToList(JSONObject room, ArrayList<Room> roomList) {
        JSONObject roomFile = (JSONObject) room.get("room");

        if (roomFile != null) {
            Room roomObj = new Room();
            roomObj.setNumber((String) roomFile.get("number"));
            roomObj.setVacant((boolean) roomFile.get("vacant"));
            roomObj.setGuestID((String) roomFile.get("guestID"));

            roomList.add(roomObj);
        }
    }

    public ArrayList<Guest> outPutInHouseFileToList() {
        ArrayList<Guest> inHouseList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(dataFolder + fileInHouseGuestList)) {
            Object obj = jsonParser.parse(reader);

            JSONArray guestFile = (JSONArray) obj;
            guestFile.forEach(guest -> writeGuestFileToList((JSONObject) guest, inHouseList));

        } catch (IOException | ParseException e) {
            return inHouseList;
        }
        return inHouseList;
    }

    public void writeInHouseListToFile(Guest guest) {
        ArrayList<Guest> guestList = outPutInHouseFileToList();
        guestList.add(guest);

        CompareGuestByDate compareByDate = new CompareGuestByDate();
        Collections.sort(guestList, compareByDate);

        JSONArray guestJSONList = new JSONArray();
        for (Guest guestObject : guestList) {
            JSONObject guestDetail = new JSONObject();
            guestDetail.put("name", guestObject.getName());
            guestDetail.put("phoneNumber", guestObject.getPhoneNumber());
            guestDetail.put("dayArrival", guestObject.getDayArrival());
            guestDetail.put("guaranteeFee", guestObject.getGuaranteeFee());
            guestDetail.put("methodPayment", guestObject.getMethodPayment());
            guestDetail.put("bookingCode", guestObject.getBookingCode());

            JSONObject guestJSON = new JSONObject();
            guestJSON.put("guest", guestDetail);

            guestJSONList.add(guestJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileInHouseGuestList)) {
            file.write(guestJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkInGuestToRoom(String roomNumber, Guest guest) {
        ArrayList<Guest> guestList = outPutBookingFileToList();
        ArrayList<Room> roomList = outPutRoomFileToList();
        writeInHouseListToFile(guest);

        for (Room room : roomList) {
            if (room.getNumber().equals(roomNumber)) {
                room.setVacant(false);
                room.setGuestID(guest.getBookingCode());
            }
        }

        String Bookingcode = guest.getBookingCode();
        guestList.removeIf(guestObj -> guestObj.getBookingCode().equals(Bookingcode));

        JSONArray roomJSONList = new JSONArray();
        for (Room roomObj : roomList) {
            JSONObject roomDetail = new JSONObject();
            roomDetail.put("number", roomObj.getNumber());
            roomDetail.put("vacant", roomObj.isVacant());
            roomDetail.put("guestID", roomObj.getGuestID());

            JSONObject roomJSON = new JSONObject();
            roomJSON.put("room", roomDetail);

            roomJSONList.add(roomJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileRoomList)) {
            file.write(roomJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray guestJSONList = new JSONArray();
        for (Guest guestObject : guestList) {
            JSONObject guestDetail = new JSONObject();
            guestDetail.put("name", guestObject.getName());
            guestDetail.put("phoneNumber", guestObject.getPhoneNumber());
            guestDetail.put("dayArrival", guestObject.getDayArrival());
            guestDetail.put("guaranteeFee", guestObject.getGuaranteeFee());
            guestDetail.put("methodPayment", guestObject.getMethodPayment());
            guestDetail.put("bookingCode", guestObject.getBookingCode());

            JSONObject guestJSON = new JSONObject();
            guestJSON.put("guest", guestDetail);

            guestJSONList.add(guestJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileBookingGuestList)) {
            file.write(guestJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkOutGuestToRoom(String roomNumber) {
        ArrayList<Room> roomList = outPutRoomFileToList();
        ArrayList<Guest> inHouseList = outPutInHouseFileToList();

        for (Room room : roomList) {
            if (room.getNumber().equals(roomNumber)) {
                for (Guest guest : inHouseList) {
                    if (room.getGuestID().equals(guest.getBookingCode())) {
                        inHouseList.remove(guest);
                    }
                }
                room.setVacant(true);
                room.setGuestID(null);
            }
        }

        JSONArray roomJSONList = new JSONArray();
        for (Room roomObj : roomList) {
            JSONObject roomDetail = new JSONObject();
            roomDetail.put("number", roomObj.getNumber());
            roomDetail.put("vacant", roomObj.isVacant());
            roomDetail.put("guestID", roomObj.getGuestID());

            JSONObject roomJSON = new JSONObject();
            roomJSON.put("room", roomDetail);

            roomJSONList.add(roomJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileRoomList)) {
            file.write(roomJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray guestJSONList = new JSONArray();
        for (Guest guestObject : inHouseList) {
            JSONObject guestDetail = new JSONObject();
            guestDetail.put("name", guestObject.getName());
            guestDetail.put("phoneNumber", guestObject.getPhoneNumber());
            guestDetail.put("dayArrival", guestObject.getDayArrival());
            guestDetail.put("guaranteeFee", guestObject.getGuaranteeFee());
            guestDetail.put("methodPayment", guestObject.getMethodPayment());
            guestDetail.put("bookingCode", guestObject.getBookingCode());

            JSONObject guestJSON = new JSONObject();
            guestJSON.put("guest", guestDetail);

            guestJSONList.add(guestJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileInHouseGuestList)) {
            file.write(guestJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkInGuestByName(String name) {
        ArrayList<Guest> guestList = outPutBookingFileToList();

        String guestName = toTitleCase(name);
        boolean isHaveNameOnList = false;
        for (Guest guest : guestList) {
            if (guest.getName().equals(guestName)) {
                System.out.print("\n-----------------------Guest's Information:---------------------------------");
                System.out.print("\n" + guest.toString());
                isHaveNameOnList = true;
            }
        }
        if (isHaveNameOnList) {
            boolean isBookingCodeValid = false;
            while (!isBookingCodeValid) {
                System.out.print("\nEnter guest's booking code or 'B' to back to Check-in menu: ");
                String inputBookingCode = input.nextLine();
                if (inputBookingCode.toUpperCase().equals("B")) {
                    checkInMenu();
                } else {
                    if (inputBookingCode.charAt(0) == '#') {
                        System.out.print("\nBooking Code must be number! please do not enter '#'");
                    } else {
                        Pattern pattern = Pattern.compile("\\d*");
                        Matcher matcher = pattern.matcher(inputBookingCode);
                        if (matcher.matches()) {
                            String bookingCode = "#" + inputBookingCode;
                            isBookingCodeValid = true;
                            checkInGuestByBookingCode(bookingCode);
                        } else {
                            System.out.print("\nBooking Code must be number!");
                        }
                    }
                }
            }
        } else {
            System.out.print("\nCan't find that name in guest List!");
            checkInWithReservation();
        }
    }

    public void checkInGuestByBookingCode(String bookingCode) {
        ArrayList<Guest> guestList = outPutBookingFileToList();
        ArrayList<Room> roomsList = outPutRoomFileToList();

        for (Guest guest : guestList) {
            if (guest.getBookingCode().equals(bookingCode)) {
                boolean isRoomValid = false;
                do {
                    System.out.print("\n-----------------------Guest's Information:---------------------------------");
                    System.out.print("\n" + guest.toString());
                    System.out.print("\n----------------------------------------------------------------------------");
                    System.out.print("\n-----------------------Room's Information:----------------------------------");
                    showRoomList();
                    System.out.print("\n----------------------------------------------------------------------------");
                    System.out.print("\nEnter number of Room for guest or 'B' to back to Check-in menu: ");
                    String roomNumber = input.nextLine();
                    if (roomNumber.toUpperCase().equals("B")) {
                        checkInMenu();
                    } else {
                        for (Room room : roomsList) {
                            if (room.getNumber().equals(roomNumber)) {
                                if (room.isVacant()) {
                                    checkInGuestToRoom(roomNumber, guest);
                                    String history = "Check-in guest with booking code: " + guest.getBookingCode();
                                    addStaffHistory(history);
                                    System.out.print("\n-----------------------Check-in Completed---------------------------------");
                                    isRoomValid = true;
                                }
                            }
                        }
                        if (!isRoomValid) {
                            System.out.print("\nRoom number is not valid!!! Please Enter another room number");
                        }
                    }
                } while (!isRoomValid);
                mainMenu();
            }
        }
        System.out.print("\nCan't find that booking code in guest List!");
        checkInWithReservation();
    }

    public void showRoomList() {
        ArrayList<Room> roomsList = outPutRoomFileToList();
        for (Room room : roomsList) {
            System.out.print("\n" + room.toString());
        }
    }

    public Guest changeBookingInformation(Guest guest) {
        boolean isInfoCorrect = false;
        do {
            final int accept = 65;
            final int changeName = 78;
            final int changePhoneNumber = 80;
            final int changeDayArrival = 68;
            final int changeMoneyGuarantee = 71;
            final int changeMethodPayment = 77;
            System.out.print("\n---------------Confirm information of Guest---------------");
            System.out.print(guest.toString());
            System.out.print("\n----------------------------------------------------------");
            System.out.print("\n|| 1.Press 'A' to accept                                 ||");
            System.out.print("\n|| 2.Press 'N' to change Guest's Name                    ||");
            System.out.print("\n|| 3.Press 'P' to change Guest's Phone Number            ||");
            System.out.print("\n|| 4.Press 'D' to change Day guest Arrival               ||");
            System.out.print("\n|| 5.Press 'G' to change MoneyGuarantee                  ||");
            System.out.print("\n|| 6.Press 'M' to change MethodPayment                   ||");
            System.out.print("\n|| 7.Press 'B' to cancel and back to main menu           ||");
            System.out.print("\n----------------------------------------------------------");
            System.out.print("\n||Enter your Option: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);
                switch (option) {
                    case accept:
                        isInfoCorrect = true;
                        break;
                    case changeName:
                        String newName = "";
                        do {
                            System.out.print("\nEnter new guest's name of reservation or press 'B' to back Change menu: ");
                            String inputName = input.nextLine();
                            if (inputName.toUpperCase().equals("B")) {
                                guest = changeBookingInformation(guest);
                            } else if (inputName.trim().length() == 0) {
                                System.out.print("\nInput name is invalid! Guest's name can't be null or blank");
                            } else {
                                newName = toTitleCase(inputName);
                                guest.setName(newName);
                                guest = changeBookingInformation(guest);
                            }
                        } while (newName.equals(""));
                        isInfoCorrect = true;
                        break;
                    case changePhoneNumber:
                        boolean checkNewPhoneNumber = false;
                        do {
                            System.out.print("\nEnter new Guest's phone number or press 'B' to back Change menu: ");
                            String inputNewPhoneNumber = input.nextLine();
                            if (inputNewPhoneNumber.length() == 11) {
                                Pattern pattern = Pattern.compile("^[0]\\d{10}");
                                Matcher matcher = pattern.matcher(inputNewPhoneNumber);
                                if (matcher.matches()) {
                                    guest.setPhoneNumber(inputNewPhoneNumber);
                                    guest = changeBookingInformation(guest);
                                    checkNewPhoneNumber = true;
                                } else {
                                    System.out.print("\nInput phone number is invalid!");
                                    System.out.print("\nPhone number must be number with length equal 11 and start with '0'");
                                }
                            } else if (inputNewPhoneNumber.toUpperCase().equals("B")) {
                                checkNewPhoneNumber = true;
                                guest = changeBookingInformation(guest);
                            } else {
                                System.out.print("\nInput phone number is invalid!");
                                System.out.print("\nPhone number must be number with length equal 11 and start with '0'");
                            }
                        } while (!checkNewPhoneNumber);
                        isInfoCorrect = true;
                        break;
                    case changeDayArrival:
                        boolean isNewDayArrivalSuitable = false;
                        do {
                            System.out.print("\nEnter new Day Guest Arrive or press 'B' to back Change menu: ");
                            String inputNewDayArrival = input.nextLine();
                            if (inputNewDayArrival.toUpperCase().equals("B")) {
                                isNewDayArrivalSuitable = true;
                                guest = changeBookingInformation(guest);
                            } else {
                                DateValidator validator = new DateValidatorUsingDateFormat("dd/MM/yyyy");
                                if (validator.isValid(inputNewDayArrival)) {
                                    guest.setDayArrival(inputNewDayArrival);
                                    isNewDayArrivalSuitable = true;
                                    guest = changeBookingInformation(guest);
                                } else {
                                    System.out.print("\nInput arrival day is invalid!, day must be follow dd/mm/yyyy");
                                }
                            }
                        } while (!isNewDayArrivalSuitable);
                        isInfoCorrect = true;
                        break;
                    case changeMoneyGuarantee:
                        boolean isNewMoneySuitable = false;
                        do {
                            System.out.print("\nEnter Amount money Guest make guarantee or press 'B' to back Change menu: ");
                            String inputNewMoneyGuarantee = input.nextLine();
                            if (inputNewMoneyGuarantee.toUpperCase().equals("B")) {
                                isNewMoneySuitable = true;
                                guest = changeBookingInformation(guest);
                            } else if ((inputNewMoneyGuarantee.length() < 10)) {
                                Pattern pattern = Pattern.compile("\\d*");
                                Matcher matcher = pattern.matcher(inputNewMoneyGuarantee);
                                if (matcher.matches()) {
                                    int newMoneyGuarantee = Integer.parseInt(inputNewMoneyGuarantee);
                                    System.out.print("\nConfirm Amount money Input: " + formatter.format(newMoneyGuarantee) + " VND");
                                    System.out.print("\nPress Y to continue or any key to retype: ");
                                    String newChoice = input.nextLine();
                                    if (newChoice.toUpperCase().equals("Y")) {
                                        inputNewMoneyGuarantee = String.valueOf(formatter.format(newMoneyGuarantee));
                                        isNewMoneySuitable = true;
                                        guest.setGuaranteeFee(inputNewMoneyGuarantee);
                                        guest = changeBookingInformation(guest);
                                    }
                                } else {
                                    System.out.print("\nInput money is invalid! Amount must be from '0' to less than 100 million");
                                }
                            } else {
                                System.out.print("\nInput money is invalid! Amount must be from '0' to less than 100 million");
                            }
                        } while (!isNewMoneySuitable);
                        isInfoCorrect = true;
                        break;
                    case changeMethodPayment:
                        System.out.print("\nOur Hotel accept 2 type of method payment: cash, credit-card");
                        System.out.print("\nEnter new methodPayment for reservation or press 'B' to back Change menu: ");
                        String newMethodPayment = input.nextLine();
                        if (newMethodPayment.toUpperCase().equals("B")) {
                            guest = changeBookingInformation(guest);
                        } else {
                            guest.setMethodPayment(newMethodPayment);
                            guest = changeBookingInformation(guest);
                        }
                        isInfoCorrect = true;
                        break;
                    case backToThePreviousMenu:
                        isInfoCorrect = true;
                        mainMenu();
                        break;
                    default:
                        System.out.print("Request undefined! Please follow the instruction!");
                }
            } else {
                System.out.print("Request undefined! Please follow the instruction!");
            }
        } while (!isInfoCorrect);
        return guest;
    }

    public void addStaffHistory(String history) {
        String staffID = staff.getID();
        ArrayList<Staff> staffList = outPutStaffFileToList();
        for (Staff staff: staffList) {
            if (staff.getID().equals(staffID)) {
                if (staff.getHistory().equals("empty")){
                    staff.setHistory(history);
                } else {
                    String historyAdd = staff.getHistory();
                    historyAdd +="\n" + history;
                    staff.setHistory(historyAdd);
                }
            }
        }
        writeStaffListToFile(staffList);
    }

}
