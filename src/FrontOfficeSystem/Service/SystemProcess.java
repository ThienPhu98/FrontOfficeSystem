package FrontOfficeSystem.Service;

import FrontOfficeSystem.Model.*;
import FrontOfficeSystem.Service.Tools.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.*;

public class SystemProcess implements ISystemProcess {
    static Scanner input = new Scanner(System.in);
    static DecimalFormat formatter = new DecimalFormat("###,###,###");
    static final int backToMainMenu = 66;
    static String dataFolder = "H:\\Study & Working\\module2\\CaseStudy\\src\\FrontOfficeSystem\\Data\\";
    static String fileGuest = "guestList.json";
    static String fileRoom = "roomList.json";

    @Override
    public void mainMenu() {
        boolean isOutMainMenuStaff = false;
        do {
            System.out.print("\n===============================================");
            System.out.print("\n||______________MAIN-MENU____________________||");
            System.out.print("\n|| 1. Enter character B to Booking           ||");
            System.out.print("\n|| 2. Enter character I to Check-in          ||");
            System.out.print("\n|| 3. Enter character O to Check-out         ||");
            System.out.print("\n|| 4. Enter character R to Room's Status     ||");
            System.out.print("\n|| 5. Enter character H to History           ||");
            System.out.print("\n|| 6. Enter character G to Booking Status    ||");
            System.out.print("\n|| 7. Enter character E to End process       ||");
            System.out.print("\n===============================================");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);

                final int booking = 66;
                final int checkIn = 73;
                final int checkOut = 79;
                final int roomStatus = 82;
                final int history = 72;
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
                    case history:
                        showHistory();
                        break;
                    case bookingStatus:
                        showBookingStatus();
                        break;
                    case exit:
                        System.out.print("Thank you and see you again!");
                        System.exit(0);
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
        inPutGuestListToFile(guest);
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
            System.out.print("\nEnter your choice: ");
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
                    case backToMainMenu:
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
            System.out.print("\nEnter your choice: ");
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
                        while(!isBookingCodeValid) {
                            System.out.print("\nEnter guest's booking code: ");
                            String inputBookingCode = input.nextLine();
                            if (inputBookingCode.charAt(0) == '#') {
                                System.out.print("\nBooking Code must be number! please do not enter '#'");
                            } else {
                                Pattern pattern = Pattern.compile("\\d*");
                                Matcher matcher = pattern.matcher(inputBookingCode);
                                if (matcher.matches()) {
                                    String bookingCode = "#" + inputBookingCode;
                                    checkInGuestByBookingCode(bookingCode);
                                } else {
                                    System.out.print("\nBooking Code must be number!");
                                }
                            }
                        }
                        break;
                    case backToMainMenu:
                        checkOut();
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
        inPutGuestListToFile(guest);
        checkInGuestByName(guest.getName());
        mainMenu();
    }

    @Override
    public void checkOut() {
        ArrayList<Room> roomList = outPutRoomFileToList();
        boolean isRoomNumberValid = false;
        String roomNumber = "";
        System.out.print("\n==================================================================");
        System.out.print("\n__________________________CHECK-OUT_______________________________");
        do {
            System.out.print("\nEnter guest's room or press 'B' to back to main menu: ");
            String choice = input.nextLine();
            if (choice.toUpperCase().equals("B")) {
                mainMenu();
            } else {
                roomNumber = "#" + choice.trim();
                for (Room room : roomList) {
                    if (room.getNumber().equals(roomNumber)){
                        isRoomNumberValid = true;
                    }
                }
                if (!isRoomNumberValid){
                    System.out.print("\nInvalid Room number! please try again!");
                }
            }
        } while (!isRoomNumberValid);

        System.out.print("\n_______________________CONFIRM-CHECK-OUT__________________________");
        for (Room room : roomList) {
            if (room.getNumber().equals(roomNumber)){
                System.out.print("\n" + room.toString());
            }
        }
        boolean isConfirmAccept= false;
        do {
            System.out.print("\nPress 'Y' to accept check-Out guest or 'B' to back to Check-Out menu");
            String choice = input.nextLine();
            if (choice.toUpperCase().equals("Y")) {
                isConfirmAccept = true;
                checkOutGuestToRoom(roomNumber);
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
        System.out.print("\n==================================================================");
        System.out.print("\n______________________SHOW-ROOM-STATUS____________________________");
        showRoomList();
        System.out.print("\n------------------------------------------------------------------");
        boolean isOutShowRoomStatus = false;
        do {
            System.out.print("\nPress 'B' to back to main menu: ");
            String choice = input.nextLine();
            if(choice.toUpperCase().equals("B")) {
                isOutShowRoomStatus = true;
                mainMenu();
            } else {
                System.out.print("\nRequest undefined! Please follow the instruction!");
            }
        } while (!isOutShowRoomStatus);
    }

    @Override
    public void showHistory() {
        System.out.print("\n==============================================================");
//        thể hiện cái lịch sử của nhân viên đang sử dụng
    }

    @Override
    public void showBookingStatus() {
        System.out.print("\n==================================================================");
        System.out.print("\n______________________SHOW-ROOM-STATUS____________________________");
        ArrayList<Guest> guestList = outPutGuestFileToList();
        for (Guest guest: guestList) {
            System.out.print("\n{" + guest.toString() + "}");
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
                for (Guest guest: guestList) {
                    if (guest.getBookingCode().equals(bookingCode)) {
                        isBookingCodeValid = true;
                        guest = changeBookingInformation(guest);
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

        try (FileWriter file = new FileWriter(dataFolder + fileGuest)) {
            file.write(guestJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("\n------------------Update Information Successful!------------------");
        showBookingStatus();
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
        ArrayList<Guest> guestList = outPutGuestFileToList();
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

    public ArrayList<Guest> outPutGuestFileToList() {
        ArrayList<Guest> guestList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(dataFolder + fileGuest)) {
            Object obj = jsonParser.parse(reader);

            JSONArray guestFile = (JSONArray) obj;
            guestFile.forEach(guest -> writeGuestFileToList((JSONObject) guest, guestList));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
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

    public void inPutGuestListToFile(Guest guest) {
        ArrayList<Guest> guestList = outPutGuestFileToList();
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

        try (FileWriter file = new FileWriter(dataFolder + fileGuest)) {
            file.write(guestJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Room> outPutRoomFileToList() {
        ArrayList<Room> roomList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(dataFolder + fileRoom)) {
            Object obj = jsonParser.parse(reader);

            JSONArray roomFile = (JSONArray) obj;
            roomFile.forEach(room -> writeRoomFileToList((JSONObject) room, roomList));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    public void writeRoomFileToList(JSONObject room, ArrayList<Room> roomList) {
        JSONObject roomFile = (JSONObject) room.get("room");

        if (roomFile != null) {
            Room roomObj = new Room();
            roomObj.setNumber((String) roomFile.get("name"));
            roomObj.setVacant((boolean) roomFile.get("status"));
            roomObj.setGuest((Guest) roomFile.get("guest"));

            roomList.add(roomObj);
        }
    }

    public void checkInGuestToRoom(String roomNumber, Guest guest) {
        ArrayList<Guest> guestList = outPutGuestFileToList();
        ArrayList<Room> roomList = outPutRoomFileToList();

        for (Room room: roomList) {
            if (room.getNumber().equals(roomNumber)) {
                room.setVacant(false);
                room.setGuest(guest);
            }
        }

        String guestName = guest.getName();
        guestList.removeIf(guestObj -> guestObj.getName().equals(guestName));

        JSONArray roomJSONList = new JSONArray();
        for (Room roomObj : roomList) {
            JSONObject roomDetail = new JSONObject();
            roomDetail.put("name", roomObj.getNumber());
            roomDetail.put("status", roomObj.isVacant());
            roomDetail.put("guest", roomObj.getGuest());

            JSONObject roomJSON = new JSONObject();
            roomJSON.put("room", roomDetail);

            roomJSONList.add(roomJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileRoom)) {
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

        try (FileWriter file = new FileWriter(dataFolder + fileGuest)) {
            file.write(guestJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkOutGuestToRoom(String roomNumber) {
        ArrayList<Room> roomList = outPutRoomFileToList();

        for (Room room: roomList) {
            if (room.getNumber().equals(roomNumber)) {
                room.setVacant(true);
                room.setGuest(null);
            }
        }

        JSONArray roomJSONList = new JSONArray();
        for (Room roomObj : roomList) {
            JSONObject roomDetail = new JSONObject();
            roomDetail.put("name", roomObj.getNumber());
            roomDetail.put("status", roomObj.isVacant());
            roomDetail.put("guest", roomObj.getGuest());

            JSONObject roomJSON = new JSONObject();
            roomJSON.put("room", roomDetail);

            roomJSONList.add(roomJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileRoom)) {
            file.write(roomJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkInGuestByName(String name) {
        ArrayList<Guest> guestList = outPutGuestFileToList();
        ArrayList<Room> roomsList = outPutRoomFileToList();

        String guestName = toTitleCase(name);
        boolean isHaveNameOnList = false;
        for (Guest guest : guestList) {
            if (guest.getName().equals(guestName)) {
                boolean isRoomValid = false;
                do {
                    System.out.print("\n-----------------------Guest's Information:---------------------------------");
                    System.out.print("\n" + guest.toString());
                    System.out.print("\n----------------------------------------------------------------------------");
                    System.out.print("\n-----------------------Room's Information:----------------------------------");
                    showRoomList();
                    System.out.print("\n----------------------------------------------------------------------------");
                    System.out.print("\nEnter number of Room for guest: ");
                    String roomNumber = input.nextLine();
                    for (Room room: roomsList) {
                        if (room.getNumber().equals(roomNumber)) {
                            if (room.isVacant()) {
                                checkInGuestToRoom(roomNumber, guest);
                                isRoomValid = true;
                                isHaveNameOnList = true;
                                System.out.print("\n================Check-in Complete!=============");
                                mainMenu();
                            } else {
                                System.out.print("\nRoom is not Vacant!!! Please Enter another room number");
                            }
                        } else {
                            System.out.print("\nRoom number is not valid!!! Please Enter another room number");
                        }
                    }
                } while (!isRoomValid);
            }
        }
        if (!isHaveNameOnList) {
            System.out.print("\nCan't find that name in guest List!");
        }
        checkInWithReservation();
    }

    public void checkInGuestByBookingCode(String bookingCode) {
        ArrayList<Guest> guestList = outPutGuestFileToList();
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
                    System.out.print("\nEnter number of Room for guest");
                    String roomNumber = input.nextLine();
                    for (Room room: roomsList) {
                        if (room.getNumber().equals(roomNumber)) {
                            if (room.isVacant()) {
                                checkInGuestToRoom(roomNumber, guest);
                                isRoomValid = true;
                            } else {
                                System.out.print("\nRoom is not Vacant!!! Please Enter another room number");
                            }
                        } else {
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
        for (Room room: roomsList) {
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
            System.out.print("\nEnter your Option: ");
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
                            } else if ((inputNewMoneyGuarantee.length() < 10)){
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
                    case backToMainMenu:
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
}
