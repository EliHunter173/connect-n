import java.util.Scanner;
import java.io.PrintStream;

public class Utils {

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int getInt(String message, Scanner input, PrintStream output) {
        String response = ""; // This is a temporary garbage can for any user response
        while (!Utils.isInt(response)) {
            output.print(message);
            response = input.next();
        }
        return Integer.parseInt(response);
    }

}
