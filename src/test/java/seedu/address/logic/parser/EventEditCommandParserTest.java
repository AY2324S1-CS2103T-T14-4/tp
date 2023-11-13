package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_DESC_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DESC_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.EVENTNAME_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.EVENTNAME_DESC_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUDGET_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENTNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MATERIAL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.MATERIAL_DESC_HANDS;
import static seedu.address.logic.commands.CommandTestUtil.MATERIAL_DESC_TRASHBAG;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BRAIN;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_CLEANER;
import static seedu.address.logic.commands.CommandTestUtil.START_DATETIME_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.START_DATETIME_DESC_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENTNAME_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATERIAL_TRASHBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_CLEANER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_CLEANUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATERIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventcommands.EventEditCommand;
import seedu.address.logic.commands.eventcommands.EventEditCommand.EditEventDescriptor;
import seedu.address.logic.parser.eventcommandparsers.EventEditCommandParser;
import seedu.address.model.event.Budget;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EventEditCommandParserTest {

    private static final String ROLE_EMPTY = " " + PREFIX_ROLE;
    private static final String MATERIAL_EMPTY = " " + PREFIX_MATERIAL;
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventEditCommand.MESSAGE_USAGE);

    private EventEditCommandParser parser = new EventEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, INVALID_EVENTNAME_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EventEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EVENTNAME_DESC_CLEANUP, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENTNAME_DESC_CLEANUP, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name
        assertParseFailure(parser, "1" + INVALID_EVENTNAME_DESC, EventName.MESSAGE_CONSTRAINTS);
        // invalid role
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS);
        // invalid start date
        assertParseFailure(parser, "1" + INVALID_START_DATETIME_DESC, DateTime.MESSAGE_CONSTRAINTS);
        // invalid end date
        assertParseFailure(parser, "1" + INVALID_END_DATETIME_DESC, DateTime.MESSAGE_CONSTRAINTS);
        // invalid material
        assertParseFailure(parser, "1" + INVALID_MATERIAL_DESC, Material.MESSAGE_CONSTRAINTS);
        // invalid budget
        assertParseFailure(parser, "1" + INVALID_BUDGET_DESC, Budget.MESSAGE_CONSTRAINTS);

        // invalid start date followed by valid budget
        assertParseFailure(parser, "1" + INVALID_START_DATETIME_DESC + BUDGET_DESC_CLEANUP,
                            DateTime.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_ROLE} alone will reset the roles of the {@code event} being edited,
        // parsing it together with a valid role results in error
        assertParseFailure(parser, "1" + ROLE_DESC_CLEANER + ROLE_DESC_BRAIN + ROLE_EMPTY,
                Role.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ROLE_DESC_CLEANER + ROLE_EMPTY + ROLE_DESC_BRAIN,
                Role.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ROLE_EMPTY + ROLE_DESC_CLEANER + ROLE_DESC_BRAIN,
                Role.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_MATERIAL} alone will reset the materials of the {@code event} being edited,
        // parsing it together with a valid material results in error
        assertParseFailure(parser, "1" + MATERIAL_DESC_TRASHBAG + MATERIAL_DESC_HANDS + MATERIAL_EMPTY,
                Material.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + MATERIAL_DESC_HANDS + MATERIAL_EMPTY + MATERIAL_DESC_TRASHBAG,
                Material.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + MATERIAL_EMPTY + MATERIAL_DESC_TRASHBAG + MATERIAL_DESC_HANDS,
                Material.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_EVENTNAME_DESC
                    + INVALID_START_DATETIME_DESC + LOCATION_DESC_CLEANUP, EventName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER
                            + START_DATETIME_DESC_CLEANUP + END_DATETIME_DESC_CLEANUP + LOCATION_DESC_CLEANUP
                            + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENTNAME_CLEANUP)
                .withStartDate(VALID_START_DATETIME_CLEANUP).withEndDate(VALID_END_DATETIME_CLEANUP)
                .withRoles(VALID_ROLE_CLEANER).withLocation(VALID_LOCATION_CLEANUP)
                .withMaterials(VALID_MATERIAL_TRASHBAG).withDescription(VALID_DESCRIPTION_CLEANUP)
                .withBudget(VALID_BUDGET_CLEANUP).build();
        EventEditCommand expectedCommand = new EventEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + LOCATION_DESC_HELPOUT + BUDGET_DESC_CLEANUP;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withLocation(VALID_LOCATION_HELPOUT)
                .withBudget(VALID_BUDGET_CLEANUP).build();
        EventEditCommand expectedCommand = new EventEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + EVENTNAME_DESC_CLEANUP;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENTNAME_CLEANUP)
                                            .build();
        EventEditCommand expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // roles
        userInput = targetIndex.getOneBased() + ROLE_DESC_CLEANER;
        descriptor = new EditEventDescriptorBuilder().withRoles(VALID_ROLE_CLEANER).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date
        userInput = targetIndex.getOneBased() + START_DATETIME_DESC_CLEANUP;
        descriptor = new EditEventDescriptorBuilder().withStartDate(VALID_START_DATETIME_CLEANUP).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end date
        userInput = targetIndex.getOneBased() + END_DATETIME_DESC_CLEANUP;
        descriptor = new EditEventDescriptorBuilder().withEndDate(VALID_END_DATETIME_CLEANUP).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_CLEANUP;
        descriptor = new EditEventDescriptorBuilder().withLocation(VALID_LOCATION_CLEANUP).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_CLEANUP;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_CLEANUP).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // material
        userInput = targetIndex.getOneBased() + MATERIAL_DESC_TRASHBAG;
        descriptor = new EditEventDescriptorBuilder().withMaterials(VALID_MATERIAL_TRASHBAG).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // budget
        userInput = targetIndex.getOneBased() + BUDGET_DESC_CLEANUP;
        descriptor = new EditEventDescriptorBuilder().withBudget(VALID_BUDGET_CLEANUP).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonSkillValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + LOCATION_DESC_CLEANUP + LOCATION_DESC_HELPOUT;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + BUDGET_DESC_HELPOUT + INVALID_BUDGET_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BUDGET));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + START_DATETIME_DESC_CLEANUP + EVENTNAME_DESC_CLEANUP
                + DESCRIPTION_DESC_CLEANUP + START_DATETIME_DESC_CLEANUP + EVENTNAME_DESC_CLEANUP
                + DESCRIPTION_DESC_CLEANUP + EVENTNAME_DESC_HELPOUT + START_DATETIME_DESC_HELPOUT
                + END_DATETIME_DESC_HELPOUT;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_DATETIME, PREFIX_NAME, PREFIX_DESCRIPTION));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_BUDGET_DESC + INVALID_START_DATETIME_DESC
                + INVALID_BUDGET_DESC + INVALID_START_DATETIME_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BUDGET, PREFIX_START_DATETIME));
    }

    @Test
    public void parse_resetRoles_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + ROLE_EMPTY;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withRoles().build();
        EventEditCommand expectedCommand = new EventEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetMaterials_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + MATERIAL_EMPTY;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withMaterials().build();
        EventEditCommand expectedCommand = new EventEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
