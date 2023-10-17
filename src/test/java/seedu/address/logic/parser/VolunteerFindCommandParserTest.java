package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.volunteercommands.VolunteerFindCommand;
import seedu.address.logic.parser.volunteercommandparsers.VolunteerFindCommandParser;
import seedu.address.model.volunteer.NameContainsKeywordsPredicate;

public class VolunteerFindCommandParserTest {

    private VolunteerFindCommandParser parser = new VolunteerFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                VolunteerFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        VolunteerFindCommand expectedVolunteerFindCommand =
                new VolunteerFindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedVolunteerFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedVolunteerFindCommand);
    }

}
