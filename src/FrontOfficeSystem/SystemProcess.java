package FrontOfficeSystem;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SystemProcess {

    public static void main(String[] args) {

    }

    public static void mainMenu() {
        Scanner input = new Scanner(System.in);
        boolean isOutMainMenu = false;
        while (!isOutMainMenu) {
            System.out.print("\nMenu:");
            System.out.print("\n1. Enter character B to Booking");
            System.out.print("\n2. Enter character I to Check-in");
            System.out.print("\n3. Enter character O to Check-out");
            System.out.print("\n4. Enter character C to Charge Fee");
            System.out.print("\n4. Enter character H to see your history");
            System.out.print("\n4. Enter character  to ");
            System.out.print("\n5. Enter character E to end process");
            System.out.print("\n==========================================================================");
            System.out.print("\nEnter your choice: ");
            String choice = input.nextLine();
        }
        System.out.print("");
    }
}
