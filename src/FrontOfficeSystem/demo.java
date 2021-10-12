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
        System.out.print("Enter some password: ");
        String password = scanner.nextLine();
        System.out.print("\n" + validDate(password));
    }
}
