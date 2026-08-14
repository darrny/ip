/**
 * Represents a command that Toot recognises.
 */
public enum CommandType {
    TODO("todo", true),
    DEADLINE("deadline", true),
    EVENT("event", true),
    LIST("list", false),
    MARK("mark", true),
    UNMARK("unmark", true),
    DELETE("delete", true),
    BYE("bye", false);

    private static final String UNKNOWN_COMMAND_MESSAGE = "Toot doesn't know that command. "
            + "Try: todo, deadline, event, list, mark, unmark, delete, or bye. (・・?)";

    private final String keyword;
    private final boolean allowsArguments;

    /**
     * Creates a command type with its user-facing keyword and argument rule.
     *
     * @param keyword Word used to invoke the command.
     * @param allowsArguments Whether text may follow the command keyword.
     */
    CommandType(String keyword, boolean allowsArguments) {
        this.keyword = keyword;
        this.allowsArguments = allowsArguments;
    }

    /**
     * Identifies the type of a trimmed command entered by the user.
     *
     * @param command Full trimmed command entered by the user.
     * @return Matching command type.
     * @throws TootException If the command is empty, unknown, or has arguments when none are allowed.
     */
    public static CommandType from(String command) throws TootException {
        if (command.isEmpty()) {
            throw new TootException("Toot didn't hear a command. "
                    + "Type a command such as 'todo read book'. (・・?)");
        }

        int firstSpaceIndex = command.indexOf(' ');
        String commandKeyword = firstSpaceIndex < 0 ? command : command.substring(0, firstSpaceIndex);
        boolean hasArguments = firstSpaceIndex >= 0 && !command.substring(firstSpaceIndex).trim().isEmpty();

        for (CommandType commandType : values()) {
            if (commandType.keyword.equals(commandKeyword)) {
                if (hasArguments && !commandType.allowsArguments) {
                    throw new TootException(UNKNOWN_COMMAND_MESSAGE);
                }
                return commandType;
            }
        }
        throw new TootException(UNKNOWN_COMMAND_MESSAGE);
    }

    /**
     * Returns the text following this command's keyword.
     *
     * @param command Full command that has already been identified as this type.
     * @return Trimmed command arguments, or an empty string when none were supplied.
     */
    public String getArguments(String command) {
        return command.substring(keyword.length()).trim();
    }

    /**
     * Returns the user-facing keyword for this command.
     *
     * @return Command keyword.
     */
    public String getKeyword() {
        return keyword;
    }
}
