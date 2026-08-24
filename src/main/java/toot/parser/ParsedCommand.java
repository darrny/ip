package toot.parser;

/**
 * Holds the recognised type and arguments of one user command.
 *
 * @param type Recognised command type.
 * @param arguments Trimmed text following the command keyword.
 */
public record ParsedCommand(CommandType type, String arguments) {
}
