package FrontOfficeSystem.Service;

import FrontOfficeSystem.Model.Guest;
import FrontOfficeSystem.Model.Room;
import FrontOfficeSystem.Service.Tools.DateValidator;
import FrontOfficeSystem.Service.Tools.DateValidatorUsingDateFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SystemProcess implements ISystemProcess {
    static Scanner input = new Scanner(System.in);
    static DecimalFormat formatter = new DecimalFormat("###,###,###");
    static final int backToMainMenu = 66;
    static String dataFolder = "H:\\Study & Working\\module2\\CaseStudy\\src\\FrontOfficeSystem\\Data\\";
    static String fileGuest = "guestList.json";
    static String fileRoom = "roomList.json";

    @Override
    public void mainMenu() {
        boolean isOutMainMenuStaff = true;
        do {
            isOutMainMenuStaff = true;
            System.out.print("\n===============================================");
            System.out.print("\n||______________MAIN-MENU____________________||");
            System.out.print("\n|| 1. Enter character B to Booking           ||");
            System.out.print("\n|| 2. Enter character I to Check-in          ||");
            System.out.print("\n|| 3. Enter character O to Check-out         ||");
            System.out.print("\n|| 4. Enter character R to Room's Status     ||");
            System.out.print("\n|| 5. Enter character C to Charge Fee        ||");
            System.out.print("\n|| 6. Enter character H to History           ||");
            System.out.print("\n|| 7. Enter character G to Guest's Status    ||");
            System.out.print("\n|| 8. Enter character E to End process       ||");
            System.out.print("\n===============================================");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);

                final int booking = 66;
                final int checkIn = 73;
                final int checkOut = 79;
                final int roomStatus = 82;
                final int chargeFee = 67;
                final int history = 72;
                final int guestStatus = 71;
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
                    case chargeFee:
                        addFee();
                        break;
                    case history:
                        showHistory();
                        break;
                    case guestStatus:
                        showGuestStatus();
                        break;
                    case exit:
                        System.out.print("Thank you and see you again!");
                        System.exit(0);
                        break;
                    default:
                        System.out.print("Request undefined! Please follow the instruction!");
                        isOutMainMenuStaff = false;
                }
            } else {
                System.out.print("Request undefined! Please follow the instruction!");
                isOutMainMenuStaff = false;
            }
        } while (!isOutMainMenuStaff);
    }

    @Override
    public void booking() {
        Guest guest = takeGuestInformation();
        inPutGuestListToFile(guest);
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
                        System.out.print("\nEnter guest's name: ");
                        String name = input.nextLine();
                        checkInGuestByName(name);
                        break;
                    case byBookingCode:
                        isOutCheckInSystem = true;
                        System.out.print("\nEnter guest's booking code: ");
                        String inputBookingCode = input.nextLine();
                        String bookingCode = "#" + inputBookingCode;
                        checkInGuestByBookingCode(bookingCode);
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
        System.out.print("\n==============================================================");
//        tìm theo tên hoặc theo booking-code
//        tạo hàm xóa khách khỏi Room.Excel
    }

    @Override
    public void showRoomStatus() {
        System.out.print("\n==============================================================");
//        in ra tên phòng - tình trạng của phòng
    }

    @Override
    public void addFee() {
        System.out.print("\n==============================================================");
//        tìm khách theo tên hoặc booking-code
//        tạo hàm cộng thêm giá tiền cho phòng
    }

    @Override
    public void showHistory() {
        System.out.print("\n==============================================================");
//        thể hiện cái lịch sử của nhân viên đang sử dụng
    }

    @Override
    public void showGuestStatus() {
        System.out.print("\n==============================================================");
//        thể hiện danh sách khách đặt phòng + danh sách khách trong khách sạn
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
                System.out.print("\nInput phone number is invalid! Guest's name can't be null or blank");
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
            } else if (inputMoneyGuarantee.length() < 10 && inputMoneyGuarantee.length() > 0) {
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
                    System.out.print("\nInput money is invalid! Money be number and less than 100 million");
                }
            } else {
                System.out.print("\nInput money is invalid! Money be number and less than 100 million");
            }
        } while (!isMoneySuitable);

        System.out.print("\nOur Hotel accept 2 type of method payment: cash, credit-card");
        System.out.print("\nEnter Amount money Guest make guarantee or press B to back Main menu: ");
        String methodPayment = input.nextLine();

        String bookingCode = "";
        do {
            int randomNumber = (int) (Math.random() * 9999999) + 1000000;
            bookingCode = "#" + randomNumber;
        } while (!isBookingCodeSuitable(bookingCode));

        Guest reservation = new Guest(guestName, inputPhoneNumber, inputDayArrival, inputMoneyGuarantee, methodPayment, bookingCode);
        boolean isInfoCorrect = false;
        do {
            final int accept = 65;
            final int changeName = 78;
            final int changePhoneNumber = 80;
            final int changeDayArrival = 68;
            final int changeMoneyGuarantee = 71;
            final int changeMethodPayment = 77;
            System.out.print("\n---------------Confirm information of Guest---------------");
            System.out.print(reservation.toString());
            System.out.print("\n----------------------------------------------------------");
            System.out.print("\n|| 1.Press 'A' to accept                                 ||");
            System.out.print("\n|| 2.Press 'N' to change Guest's Name                    ||");
            System.out.print("\n|| 3.Press 'P' to change Guest's Phone Number            ||");
            System.out.print("\n|| 4.Press 'D' to change Day guest Arrival               ||");
            System.out.print("\n|| 5.Press 'G' to change MoneyGuarantee                  ||");
            System.out.print("\n|| 6.Press 'M' to change MethodPayment                   ||");
            System.out.print("\n|| 6.Press 'B' to cancel and back to main menu           ||");
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
                        System.out.print("\nEnter new guest's name of reservation");
                        String newName = input.nextLine();
                        reservation.setName(newName);
                        break;
                    case changePhoneNumber:
                        boolean checkNewPhoneNumber = false;
                        do {
                            System.out.print("\nEnter new Guest's phone number or press B to back Main menu: ");
                            String inputNewPhoneNumber = input.nextLine();
                            if (inputNewPhoneNumber.length() == 11) {
                                Pattern pattern = Pattern.compile("^[0]\\d{10}");
                                Matcher matcher = pattern.matcher(inputNewPhoneNumber);
                                if (matcher.matches()) {
                                    reservation.setPhoneNumber(inputNewPhoneNumber);
                                    checkNewPhoneNumber = true;
                                } else {
                                    System.out.print("\nInput phone number is invalid!");
                                    System.out.print("\nPhone number must be number with length equal 11 and start with '0'");
                                }
                            } else if (inputNewPhoneNumber.toUpperCase().equals("B")) {
                                checkNewPhoneNumber = true;
                                mainMenu();
                            } else {
                                System.out.print("\nInput phone number is invalid!");
                                System.out.print("\nPhone number must be number with length equal 11 and start with '0'");
                            }
                        } while (!checkNewPhoneNumber);
                        break;
                    case changeDayArrival:
                        boolean isNewDayArrivalSuitable = false;
                        do {
                            System.out.print("\nEnter new Day Guest Arrive or press B to back Main menu: ");
                            String inputNewDayArrival = input.nextLine();
                            if (inputDayArrival.toUpperCase().equals("B")) {
                                isNewDayArrivalSuitable = true;
                                mainMenu();
                            } else {
                                DateValidator validator = new DateValidatorUsingDateFormat("dd/MM/yyyy");
                                if (validator.isValid(inputNewDayArrival)) {
                                    reservation.setDayArrival(inputNewDayArrival);
                                    isNewDayArrivalSuitable = true;
                                } else {
                                    System.out.print("\nInput arrival day is invalid!, day must be follow dd/mm/yyyy");
                                }
                            }
                        } while (!isNewDayArrivalSuitable);
                        break;
                    case changeMoneyGuarantee:
                        boolean isNewMoneySuitable = false;
                        do {
                            System.out.print("\nEnter Amount money Guest make guarantee or press B to back Main menu: ");
                            String inputNewMoneyGuarantee = input.nextLine();
                            if (inputNewMoneyGuarantee.toUpperCase().equals("B")) {
                                isNewMoneySuitable = true;
                                mainMenu();
                            } else {
                                Pattern pattern = Pattern.compile("\\d*");
                                Matcher matcher = pattern.matcher(inputNewMoneyGuarantee);
                                if (matcher.matches()) {
                                    int newMoneyGuarantee = Integer.parseInt(inputNewMoneyGuarantee);
                                    System.out.print("Confirm Amount money Input: " + formatter.format(newMoneyGuarantee) + " VND");
                                    System.out.print("Press Y to continue or any key to retype: ");
                                    String newChoice = input.nextLine();
                                    if (newChoice.toUpperCase().equals("Y")) {
                                        inputNewMoneyGuarantee = String.valueOf(formatter.format(newMoneyGuarantee));
                                        isNewMoneySuitable = true;
                                        reservation.setGuaranteeFee(inputNewMoneyGuarantee);
                                    }
                                } else {
                                    System.out.print("\nInput money is invalid! Money be number and less than 1 billion");
                                }
                            }
                        } while (!isNewMoneySuitable);
                        break;
                    case changeMethodPayment:
                        System.out.print("\nOur Hotel accept 2 type of method payment: cash, credit-card");
                        System.out.print("\nEnter new methodPayment for reservation: ");
                        String newMethodPayment = input.nextLine();
                        reservation.setMethodPayment(newMethodPayment);
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
        return reservation;
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
                    return true;
                }
            }
        }
        return false;
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

        Guest guestObj = new Guest();
        guestObj.setName((String) guestFile.get("name"));
        guestObj.setPhoneNumber((String) guestFile.get("phoneNumber"));
        guestObj.setDayArrival((String) guestFile.get("dayArrival"));
        guestObj.setGuaranteeFee((String) guestFile.get("guaranteeFee"));
        guestObj.setBookingCode((String) guestFile.get("bookingCode"));
        guestObj.setMethodPayment((String) guestFile.get("methodPayment"));

        guestList.add(guestObj);
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

        Room roomObj = new Room();
        roomObj.setNumber((String) roomFile.get("name"));
        roomObj.setVacant((boolean) roomFile.get("status"));
        roomObj.setGuest((Guest) roomFile.get("guest"));

        roomList.add(roomObj);
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

        for (Guest guestObj: guestList) {
            if(guest.getName().equals(guestObj.getName())){
                guestList.remove(guestObj);
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

        for (Guest guest : guestList) {
            if (guest.getName().equals(name)) {
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
        System.out.print("\nCan't find that name in guest List!");
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

}
