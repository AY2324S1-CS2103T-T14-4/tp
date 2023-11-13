package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VOLUNTEERID_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventvolunteercommands.EventAddVolunteerCommand;
import seedu.address.logic.parser.eventvolunteercommandparsers.EventAddVolunteerCommandParser;

public class EventAddVolunteerCommandParserTest {
    private EventAddVolunteerCommandParser parser = new EventAddVolunteerCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        EventAddVolunteerCommand expectedCommand = new EventAddVolunteerCommand(INDEX_FIRST, INDEX_FIRST);
        assertParseSuccess(parser, " eid/1 vid/1", expectedCommand);
    }
    @Test
    public void parse_multiplePrefixes_parseUnsuccessful() {
        // multiple eid prefix
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST + " " + VOLUNTEERID_DESC + INDEX_FIRST + " "
                        + EVENTID_DESC + INDEX_FIRST,
                Messages.getErrorMessageForDuplicatePrefixes(new Prefix[]{new Prefix("eid/")}));

        // multiple vid prefix
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST + " " + VOLUNTEERID_DESC + INDEX_FIRST + " "
                        + VOLUNTEERID_DESC + INDEX_FIRST,
                Messages.getErrorMessageForDuplicatePrefixes(new Prefix[]{new Prefix("vid/")}));

        // multiple eid and vid prefixes
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST + " " + VOLUNTEERID_DESC + INDEX_FIRST + " "
                        + EVENTID_DESC + INDEX_FIRST + " " + VOLUNTEERID_DESC + INDEX_FIRST,
                Messages.getErrorMessageForDuplicatePrefixes(new Prefix[]{new Prefix("eid/"), new Prefix("vid/")}));
    }
    @Test
    public void parse_missingPrefixes_parseUnsuccessful() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddVolunteerCommand.MESSAGE_USAGE);

        // missing eid prefix
        assertParseFailure(parser, VOLUNTEERID_DESC + INDEX_FIRST,
                expectedMessage);

        // missing vid prefix
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST,
                expectedMessage);

        // both eid and vid prefixes missing
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST + VOLUNTEERID_DESC + INDEX_FIRST,
                expectedMessage);
    }
    @Test
    public void parse_missingArguments_parseUnsuccessful() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddVolunteerCommand.MESSAGE_USAGE);

        // missing eid argument
        assertParseFailure(parser, EVENTID_DESC + VOLUNTEERID_DESC + INDEX_FIRST,
                expectedMessage);

        // missing vid argument
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST + VOLUNTEERID_DESC,
                expectedMessage);

        // both eid and vid arguments missing
        assertParseFailure(parser, EVENTID_DESC + VOLUNTEERID_DESC,
                expectedMessage);
    }
    @Test
    public void parse_invalidArguments_parseUnsuccessful() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddVolunteerCommand.MESSAGE_USAGE);

        // invalid eid argument
        assertParseFailure(parser, EVENTID_DESC + INVALID_ROLE_DESC + VOLUNTEERID_DESC + INDEX_FIRST,
                expectedMessage);

        // invalid vid argument
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST + VOLUNTEERID_DESC + INVALID_ROLE_DESC,
                expectedMessage);

        // both eid and vid arguments invalid
        assertParseFailure(parser, EVENTID_DESC + INVALID_ROLE_DESC + VOLUNTEERID_DESC
                        + INVALID_ROLE_DESC,
                expectedMessage);
    }
}
