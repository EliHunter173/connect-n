/**
 * Handles the creation of ANSI escape sequences for color.
 * @author Eli W. Hunter
 */
public class ANSIColor {

    /** The ANSI starting escape sequence. */
    public static final String ESCAPE_SEQUENCE = "\033[";
    /** The ANSI escape sequence ending sequence for graphical sequences. */
    public static final String GRAPHICS_END = "m";
    /** The ANSI delimiter for a list of codes. */
    public static final String DELIMITER = ";";

    /** The lowest valid ANSI color code. */
    public static final int LOWEST_VALID_CODE = 0;
    /** The highest valid ANSI color code. */
    public static final int HIGHEST_VALID_CODE = 7;

    // The ANSI color code works by having a single octal digit
    // representing the color and then offsetting that value by a certain
    // amount depending on whether it is foreground or background.
    /** How much the ANSI color code should be offset if it is a foreground
     *  color. */
    public static final int FOREGROUND_OFFSET = 30;
    /** How much the ANSI color code should be offset if it is a background
     *  color. */
    public static final int BACKGROUND_OFFSET = 40;

    /** The ANSI code representing the color black. */
    public static final int BLACK =   0;
    /** The ANSI code representing the color red. */
    public static final int RED =     1;
    /** The ANSI code representing the color green. */
    public static final int GREEN =   2;
    /** The ANSI code representing the color yellow. */
    public static final int YELLOW =  3;
    /** The ANSI code representing the color blue. */
    public static final int BLUE =    4;
    /** The ANSI code representing the color magenta. */
    public static final int MAGENTA = 5;
    /** The ANSI code representing the color cyan. */
    public static final int CYAN =    6;
    /** The ANSI code representing the color white. */
    public static final int WHITE =   7;

    /** The ANSI code representing normal text. */
    public static final int NORMAL = 0;
    /** The ANSI code representing bold text. */
    public static final int BOLD = 1;
    /** The ANSI code representing underlined text. */
    public static final int UNDERLINE = 4;
    /** The ANSI code representing blinking text. */
    public static final int BLINK = 5;
    /** The ANSI code representing concealed text. */
    public static final int CONCEAL = 8;

    /** The string that stores the ANSI sequence for the given color object. */
    private String ansiSequence;

    /**
     * Creates an ANSI color with the given ANSI color code for the foreground
     * and background color, along with any number of extra graphical codes.
     * @param foregroundColorCode The ANSI color code for the foreground color.
     * @param backgroundColorCode The ANSI color code for the background color.
     * @param extraColorCodes Any number of extra, valid ANSI graphical codes.
     */
    public ANSIColor(int foregroundColorCode, int backgroundColorCode, int ... extraCodes) {
        if ((foregroundColorCode < LOWEST_VALID_CODE || foregroundColorCode > HIGHEST_VALID_CODE) ||
            (backgroundColorCode < LOWEST_VALID_CODE || backgroundColorCode > HIGHEST_VALID_CODE)) {
            throw new IllegalArgumentException("Color ID is not a recognized ANSI color ID");
        }

        // Make the foreground and background color codes correspond to their
        // proper foreground/background setting.
        foregroundColorCode += FOREGROUND_OFFSET;
        backgroundColorCode += BACKGROUND_OFFSET;

        // Parse the extra color codes
        String parsedExtraCodes = "";
        if (extraCodes.length == 0) {
            // If no special code is given, specify normal text
            parsedExtraCodes += DELIMITER + NORMAL;
        } else {
            for (int code : extraCodes) {
                parsedExtraCodes += DELIMITER + code;
            }
        }

        this.ansiSequence = ESCAPE_SEQUENCE +
                            foregroundColorCode + DELIMITER + backgroundColorCode +
                            parsedExtraCodes +
                            GRAPHICS_END;
    }

    /**
     * Returns the ANSI sequence for the color object.
     * @return The ANSI sequence for the color object.
     */
    public String toString() {
        return this.ansiSequence;
    }

}
