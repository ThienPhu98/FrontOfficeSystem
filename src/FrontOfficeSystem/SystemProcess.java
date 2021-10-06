package FrontOfficeSystem;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SystemProcess {
    static Scanner input = new Scanner(System.in);
    static DecimalFormat formatter = new DecimalFormat("###,###,###");
    static final int backToMainMenu = 66;

    public static void main(String[] args) {
    }

    public static void mainMenuStaff() {
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
                        bookingMenu();
                        break;
                    case checkIn:
                        checkIn();
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

    public static void bookingMenu() {
        System.out.print("\n==============================================================");
        System.out.print("\nEnter Guest's name or press B to back Main menu: ");
        String inputName = input.nextLine();
        String guestName = "";
        if (inputName.toUpperCase().equals("B")) {
            mainMenuStaff();
        } else {
            guestName = toTitleCase(inputName);
        }

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
                mainMenuStaff();
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
                mainMenuStaff();
            } else {
                DateValidator validator = new DateValidatorUsingDateFormat("dd/MM/yyyy");
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
                mainMenuStaff();
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
//        chưa check bookingCode
        do {
            int randomNumber = (int) (Math.random() * 9999999) + 1000000;
            bookingCode = "#" + randomNumber;
        } while (!checkBookingCode(bookingCode));

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
//                        ghi file vào Excel
                        mainMenuStaff();
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
                                mainMenuStaff();
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
                                mainMenuStaff();
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
                                mainMenuStaff();
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
                        mainMenuStaff();
                        break;
                    default:
                        System.out.print("Request undefined! Please follow the instruction!");
                }
            } else {
                System.out.print("Request undefined! Please follow the instruction!");
            }
        } while (!isInfoCorrect);
    }

    public static void checkIn() {
        boolean isOutCheckIn = false;
        do {
            System.out.print("\n==================================================================");
            System.out.print("\n__________________________CHECK-IN________________________________");
            System.out.print("\n||1. Enter 'R' to check-in for guest have reservation           ||");
            System.out.print("\n||1. Enter 'N' to check-in for guest doesn't have reservation   ||");
            System.out.print("\n||1. Enter 'B' to back to main menu                             ||");
            System.out.print("\n------------------------------------------------------------------");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
            if (choice.length() == 1) {
                int option = choice.toUpperCase().charAt(0);

                final int checkInReservation = 82;
                final int checkInNonReservation = 78;

                switch (option) {
                    case checkInReservation:
//                        tìm theo tên hoặc theo booking-code
//                        tạo hàm đưa guest vào phòng
                        break;
                    case checkInNonReservation:
                        bookingMenu();
//                        tạo hàm đưa guest vào phòng
                        break;
                    case backToMainMenu:
                        isOutCheckIn = true;
                        mainMenuStaff();
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

    public static void checkOut() {
        System.out.print("\n==============================================================");
//        tìm theo tên hoặc theo booking-code
//        tạo hàm xóa khách khỏi Room.Excel
    }

    public static void showRoomStatus() {
        System.out.print("\n==============================================================");
//        in ra tên phòng - tình trạng của phòng
    }

    public static void addFee() {
        System.out.print("\n==============================================================");
//        tìm khách theo tên hoặc booking-code
//        tạo hàm cộng thêm giá tiền cho phòng
    }

    public static void showHistory() {
        System.out.print("\n==============================================================");
//        thể hiện cái lịch sử của nhân viên đang sử dụng
    }

    public static void showGuestStatus() {
        System.out.print("\n==============================================================");
//        thể hiện danh sách khách đặt phòng + danh sách khách trong khách sạn
    }

    public static String toTitleCase(String inputString) {
        String[] arr = inputString.split(" ");
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            stringBuffer.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return stringBuffer.toString().trim();
    }

    public static boolean checkBookingCode(String bookingCode) {
        return true;
    }
}
