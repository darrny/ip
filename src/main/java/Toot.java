import java.util.Scanner;

/**
 * Runs the Toot chatbot application.
 */
public class Toot {
    public static void main(String[] args) {
        String banner = " _____           _\n"
                + "|_   _|__   ___ | |_\n"
                + "  | |/ _ \\ / _ \\| __|\n"
                + "  | | (_) | (_) | |_\n"
                + "  |_|\\___/ \\___/ \\__|\n";
        String horizontalLine = "⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆";

        System.out.println(horizontalLine);
        System.out.print(banner + "\n");
        System.out.println("Hewwo!! I'm Toot, ur teeny-tiny computey baby! ૮₍ ˶•⤙•˶ ₎ა");
        System.out.println("Gib me a command... Toot do a BIG twy!! (•̀ᴗ•́)و");
        System.out.println(horizontalLine + "\n");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(horizontalLine);

            if (command.equals("bye")) {
                System.out.println("Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ");
                System.out.println(horizontalLine);
                break;
            }

            System.out.println("Toot hearded: \"" + command + "\"  (｡•̀ᴗ-)✧");
            System.out.println(horizontalLine + "\n");
        }
    }
}
