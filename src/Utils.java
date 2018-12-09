import java.util.Scanner;
import java.io.PrintStream;

public class Utils {

    public static String INT_ERROR_MESSAGE = "That is not an integer.";

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

            if (!Utils.isInt(response)) {
                output.println(INT_ERROR_MESSAGE);
            }
        }
        return Integer.parseInt(response);
    }

    public static String repeatString(String message, int count) {
        String str = "";
        for (int i = 0; i < count; i++) {
            str += message;
        }
        return str;
    }
}