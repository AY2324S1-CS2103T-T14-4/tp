package seedu.address.logic.parser.eventcommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.eventcommands.EventFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new EventFindCommand object
 */
public class EventFindCommandParser implements Parser<EventFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EventFindCommand
     * and returns a EventFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventFindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!argMultimap.getValue(PREFIX_NAME).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventFindCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getValue(PREFIX_NAME).isPresent() : "At least one PREFIX_NAME should be present";

        Set<String> eventNameKeywords = ParserUtil.parseEventNames(argMultimap.getAllValues(PREFIX_NAME));
        List<String> eventNames = new ArrayList<>(eventNameKeywords);

        return new EventFindCommand(new EventNameContainsKeywordsPredicate(eventNames));
    }
}
