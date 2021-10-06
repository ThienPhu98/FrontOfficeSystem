package FrontOfficeSystem;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static FrontOfficeSystem.SystemProcess.formatter;

public class Test {
static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        String inputMoneyGuarantee = "";
        boolean isMoneySuitable = false;
        do {
            System.out.print("\nEnter Amount money Guest make guarantee or press B to back Main menu: ");
            inputMoneyGuarantee = input.nextLine();
            if (inputMoneyGuarantee.toUpperCase().equals("B")) {
                isMoneySuitable = true;
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
    }

}
