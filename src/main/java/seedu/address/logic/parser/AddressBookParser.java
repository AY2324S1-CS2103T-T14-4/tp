package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.VolunteerAddCommand;
import seedu.address.logic.commands.VolunteerClearCommand;
import seedu.address.logic.commands.VolunteerDeleteCommand;
import seedu.address.logic.commands.VolunteerEditCommand;
import seedu.address.logic.commands.VolunteerFindCommand;
import seedu.address.logic.commands.VolunteerListCommand;
import seedu.address.logic.commands.VolunteerRemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    // A new format can identify single word commands(e.g. help, exit),
    // and two word commands with arguments(e.g. volunteer delete 3)
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
                            "(?<commandWords>\\S+(?:\\s+\\S+)?)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWords = matcher.group("commandWords");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWords + "; Arguments: " + arguments);

        switch (commandWords) {

        case VolunteerAddCommand.COMMAND_WORDS:
            return new AddCommandParser().parse(arguments);

        case VolunteerEditCommand.COMMAND_WORDS:
            return new EditCommandParser().parse(arguments);

        case VolunteerDeleteCommand.COMMAND_WORDS:
            return new DeleteCommandParser().parse(arguments);

        case VolunteerClearCommand.COMMAND_WORDS:
            return new VolunteerClearCommand();

        case VolunteerFindCommand.COMMAND_WORDS:
            return new FindCommandParser().parse(arguments);

        case VolunteerRemarkCommand.COMMAND_WORDS:
            return new RemarkCommandParser().parse(arguments);

        case VolunteerListCommand.COMMAND_WORDS:
            return new VolunteerListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
