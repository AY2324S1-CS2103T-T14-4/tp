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
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.eventcommands.EventAddMaterialCommand;
import seedu.address.logic.commands.eventcommands.EventCreateCommand;
import seedu.address.logic.commands.eventcommands.EventDeleteCommand;
import seedu.address.logic.commands.eventcommands.EventEditCommand;
import seedu.address.logic.commands.eventcommands.EventFindCommand;
import seedu.address.logic.commands.eventcommands.EventListCommand;
import seedu.address.logic.commands.eventcommands.EventShowCommand;
import seedu.address.logic.commands.eventvolunteercommands.EventAddVolunteerCommand;
import seedu.address.logic.commands.eventvolunteercommands.EventListVolunteerCommand;
import seedu.address.logic.commands.eventvolunteercommands.EventRemoveVolunteerCommand;
import seedu.address.logic.commands.eventvolunteercommands.VolunteerListEventCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerCreateCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerDeleteCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerEditCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerFindCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerListCommand;
import seedu.address.logic.parser.eventcommandparsers.EventAddMaterialCommandParser;
import seedu.address.logic.parser.eventcommandparsers.EventCreateCommandParser;
import seedu.address.logic.parser.eventcommandparsers.EventDeleteCommandParser;
import seedu.address.logic.parser.eventcommandparsers.EventEditCommandParser;
import seedu.address.logic.parser.eventcommandparsers.EventFindCommandParser;
import seedu.address.logic.parser.eventcommandparsers.EventShowCommandParser;
import seedu.address.logic.parser.eventvolunteercommandparsers.EventAddVolunteerCommandParser;
import seedu.address.logic.parser.eventvolunteercommandparsers.EventListVolunteerCommandParser;
import seedu.address.logic.parser.eventvolunteercommandparsers.EventRemoveVolunteerCommandParser;
import seedu.address.logic.parser.eventvolunteercommandparsers.VolunteerListEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.volunteercommandparsers.VolunteerCreateCommandParser;
import seedu.address.logic.parser.volunteercommandparsers.VolunteerDeleteCommandParser;
import seedu.address.logic.parser.volunteercommandparsers.VolunteerEditCommandParser;
import seedu.address.logic.parser.volunteercommandparsers.VolunteerFindCommandParser;

/**
 * Parses user input.
 */
public class IVolunteerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(IVolunteerParser.class);

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

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {
        case EventCreateCommand.COMMAND_WORD:
            return new EventCreateCommandParser().parse(arguments);

        case EventDeleteCommand.COMMAND_WORD:
            return new EventDeleteCommandParser().parse(arguments);

        case EventShowCommand.COMMAND_WORD:
            return new EventShowCommandParser().parse(arguments);

        case EventListCommand.COMMAND_WORD:
            return new EventListCommand();

        case EventFindCommand.COMMAND_WORD:
            return new EventFindCommandParser().parse(arguments);

        case EventEditCommand.COMMAND_WORD:
            return new EventEditCommandParser().parse(arguments);

        case VolunteerCreateCommand.COMMAND_WORD:
            return new VolunteerCreateCommandParser().parse(arguments);

        case VolunteerEditCommand.COMMAND_WORD:
            return new VolunteerEditCommandParser().parse(arguments);

        case VolunteerDeleteCommand.COMMAND_WORD:
            return new VolunteerDeleteCommandParser().parse(arguments);

        case VolunteerFindCommand.COMMAND_WORD:
            return new VolunteerFindCommandParser().parse(arguments);

        case VolunteerListCommand.COMMAND_WORD:
            return new VolunteerListCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case EventAddVolunteerCommand.COMMAND_WORD:
            return new EventAddVolunteerCommandParser().parse(arguments);

        case EventListVolunteerCommand.COMMAND_WORD:
            return new EventListVolunteerCommandParser().parse(arguments);

        case EventRemoveVolunteerCommand.COMMAND_WORD:
            return new EventRemoveVolunteerCommandParser().parse(arguments);

        case VolunteerListEventCommand.COMMAND_WORD:
            return new VolunteerListEventCommandParser().parse(arguments);

        case EventAddMaterialCommand.COMMAND_WORD:
            return new EventAddMaterialCommandParser().parse(arguments);

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
