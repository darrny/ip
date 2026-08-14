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
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(horizontalLine);

            if (command.equals("bye")) {
                System.out.println("Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ");
                System.out.println(horizontalLine);
                break;
            }

            if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (command.equals("mark") || command.startsWith("mark ")) {
                String taskNumberText = command.substring("mark".length()).trim();

                try {
                    int taskIndex = Integer.parseInt(taskNumberText) - 1;
                    if (taskIndex < 0 || taskIndex >= taskCount) {
                        System.out.println("Toot can't find that task! Try a number from the list. (｡•́︿•̀｡)");
                    } else {
                        tasks[taskIndex].markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks[taskIndex]);
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("Toot needs a task number after 'mark'. (｡•́︿•̀｡)");
                }
            } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                String taskNumberText = command.substring("unmark".length()).trim();

                try {
                    int taskIndex = Integer.parseInt(taskNumberText) - 1;
                    if (taskIndex < 0 || taskIndex >= taskCount) {
                        System.out.println("Toot can't find that task! Try a number from the list. (｡•́︿•̀｡)");
                    } else {
                        tasks[taskIndex].markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks[taskIndex]);
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("Toot needs a task number after 'unmark'. (｡•́︿•̀｡)");
                }
            } else {
                Task newTask = parseTask(command);
                tasks[taskCount] = newTask;
                taskCount++;
                System.out.println("Toot addeded:");
                System.out.println("  " + newTask);
                String taskWord = taskCount == 1 ? "task" : "tasks";
                System.out.println("Toot has " + taskCount + " " + taskWord + " in the list now! (｡•̀ᴗ-)✧");
            }

            System.out.println(horizontalLine + "\n");
        }
    }

    /**
     * Creates the task requested by an add-task command.
     * Untyped input remains a todo for compatibility with earlier levels.
     *
     * @param command Full command entered by the user.
     * @return Task represented by the command.
     */
    private static Task parseTask(String command) {
        if (command.startsWith("event ")) {
            String arguments = command.substring("event ".length());
            int fromIndex = arguments.indexOf(" /from ");
            int toIndex = arguments.indexOf(" /to ", fromIndex + " /from ".length());
            String description = arguments.substring(0, fromIndex);
            String from = arguments.substring(fromIndex + " /from ".length(), toIndex);
            String to = arguments.substring(toIndex + " /to ".length());
            return new Event(description, from, to);
        }
        if (command.startsWith("deadline ")) {
            String arguments = command.substring("deadline ".length());
            int byIndex = arguments.indexOf(" /by ");
            String description = arguments.substring(0, byIndex);
            String by = arguments.substring(byIndex + " /by ".length());
            return new Deadline(description, by);
        }
        if (command.startsWith("todo ")) {
            String description = command.substring("todo ".length());
            return new Todo(description);
        }
        return new Todo(command);
    }
}
