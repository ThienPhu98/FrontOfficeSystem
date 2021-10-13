package FrontOfficeSystem;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class demo {
    private static final String ACCOUNT_REGEX = "^[a-zA-Z0-9]{7,12}$";
    public static Scanner scanner = new Scanner(System.in);

    public static boolean validDate(String regex) {
        Pattern pattern = Pattern.compile(ACCOUNT_REGEX);
        Matcher matcher = pattern.matcher(regex);
        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.print("\n__________________________CHECK-IN________________________________");
        System.out.print("\n_________________LOOKING-FOR-RESERVATION__________________________");
        System.out.print("\n__________________________CHECK-OUT_______________________________");
        System.out.print("\n_______________________CHECK-OUT-COMPLETED________________________");
        System.out.print("\n_______________Your-Information-Menu________________");
        System.out.print("\n________________________BOOKING-STATUS____________________________");
        System.out.print("\n------------------Update Information Successful!------------------");
        System.out.print("\n==============================================================");
        System.out.print("\n-----------------------Guest's Information:---------------------------------");
        System.out.print("Enter some password: ");
        String password = scanner.nextLine();
        System.out.print("\n" + validDate(password));
    }


}
