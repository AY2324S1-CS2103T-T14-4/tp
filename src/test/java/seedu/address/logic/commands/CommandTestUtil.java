package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATERIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_VOLUNTEER_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_ID;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.EventEditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.volunteercommands.VolunteerEditCommand;
import seedu.address.model.Model;
import seedu.address.model.VolunteerStorage;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.SkillNameContainsKeywordsPredicate;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    // Event fields
    public static final String VALID_EVENTNAME_CLEANUP = "Clean up";
    public static final String VALID_EVENTNAME_HELPOUT = "Help out";
    public static final String VALID_ROLE_CLEANER = "20 cleaner";
    public static final String VALID_ROLE_BRAIN = "15 brain";
    public static final String VALID_START_DATETIME_CLEANUP = "23/9/2023 1800";
    public static final String VALID_END_DATETIME_CLEANUP = "23/9/2023 2100";
    public static final String VALID_END_DATETIME_CLEANUP_BEFORE_START = "23/9/2023 1700";
    public static final String VALID_START_DATETIME_HELPOUT = "25/10/2023 2000";
    public static final String VALID_END_DATETIME_HELPOUT = "25/10/2023 2200";
    public static final String VALID_LOCATION_CLEANUP = "serangoon";
    public static final String VALID_LOCATION_HELPOUT = "admiralty";
    public static final String VALID_DESCRIPTION_CLEANUP = "clean it up";
    public static final String VALID_DESCRIPTION_HELPOUT = "help out la";
    public static final String VALID_MATERIAL_TRASHBAG = "20 trash bags";
    public static final String VALID_MATERIAL_HANDS = "30 hands";
    public static final String VALID_BUDGET_CLEANUP = "80.00";
    public static final String VALID_BUDGET_HELPOUT = "100.00";
    public static final String VALID_MVS_CLEANUP = "3";
    public static final String VALID_MVS_HELPOUT = "4";
    public static final String EVENTNAME_DESC_CLEANUP = " " + PREFIX_NAME + VALID_EVENTNAME_CLEANUP;
    public static final String EVENTNAME_DESC_HELPOUT = " " + PREFIX_NAME + VALID_EVENTNAME_HELPOUT;
    public static final String ROLE_DESC_CLEANER = " " + PREFIX_ROLE + VALID_ROLE_CLEANER;
    public static final String ROLE_DESC_BRAIN = " " + PREFIX_ROLE + VALID_ROLE_BRAIN;
    public static final String START_DATETIME_DESC_CLEANUP = " " + PREFIX_START_DATETIME + VALID_START_DATETIME_CLEANUP;
    public static final String END_DATETIME_DESC_CLEANUP = " " + PREFIX_END_DATETIME + VALID_END_DATETIME_CLEANUP;
    public static final String START_DATETIME_DESC_HELPOUT = " " + PREFIX_START_DATETIME + VALID_START_DATETIME_HELPOUT;
    public static final String END_DATETIME_DESC_HELPOUT = " " + PREFIX_END_DATETIME + VALID_END_DATETIME_HELPOUT;
    public static final String LOCATION_DESC_CLEANUP = " " + PREFIX_LOCATION + VALID_LOCATION_CLEANUP;
    public static final String LOCATION_DESC_HELPOUT = " " + PREFIX_LOCATION + VALID_LOCATION_HELPOUT;
    public static final String DESCRIPTION_DESC_CLEANUP = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_CLEANUP;
    public static final String DESCRIPTION_DESC_HELPOUT = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_CLEANUP;
    public static final String MATERIAL_DESC_TRASHBAG = " " + PREFIX_MATERIAL + VALID_MATERIAL_TRASHBAG;
    public static final String MATERIAL_DESC_HANDS = " " + PREFIX_MATERIAL + VALID_MATERIAL_HANDS;
    public static final String BUDGET_DESC_CLEANUP = " " + PREFIX_BUDGET + VALID_BUDGET_CLEANUP;
    public static final String BUDGET_DESC_HELPOUT = " " + PREFIX_BUDGET + VALID_BUDGET_HELPOUT;
    public static final String MVS_DESC_CLEANUP = " " + PREFIX_MAX_VOLUNTEER_SIZE + VALID_MVS_CLEANUP;
    public static final String MVS_DESC_HELPOUT = " " + PREFIX_MAX_VOLUNTEER_SIZE + VALID_MVS_HELPOUT;
    public static final String END_DATETIME_DESC_CLEANUP_BEFORE_START =
            " " + PREFIX_END_DATETIME + VALID_END_DATETIME_CLEANUP_BEFORE_START;
    public static final String EVENTID_DESC = " " + PREFIX_EVENT_ID;
    public static final String MATERIAL_DESC = " " + PREFIX_MATERIAL;


    public static final String INVALID_EVENTNAME_DESC = " " + PREFIX_NAME + "Clean&";
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "cleaner&";
    public static final String INVALID_START_DATETIME_DESC = " " + PREFIX_START_DATETIME + "23-9-2023 1800";
    public static final String INVALID_END_DATETIME_DESC = " " + PREFIX_END_DATETIME + "23-9-2023 1800";
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "sembawang[";
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "clean[";
    public static final String INVALID_MATERIAL_DESC = " " + PREFIX_MATERIAL + "20 trash bag&";
    public static final String INVALID_BUDGET_DESC = " " + PREFIX_BUDGET + "50.0";
    public static final String INVALID_MVS_DESC = " " + PREFIX_MAX_VOLUNTEER_SIZE + "-4&";

    // Volunteer fields
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_SKILL_HUSBAND = "husband";
    public static final String VALID_SKILL_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String SKILL_DESC_FRIEND = " " + PREFIX_SKILL + VALID_SKILL_FRIEND;
    public static final String SKILL_DESC_HUSBAND = " " + PREFIX_SKILL + VALID_SKILL_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_SKILL_DESC = " " + PREFIX_SKILL + "hubby*"; // '*' not allowed in skills
    public static final String VOLUNTEERID_DESC = " " + PREFIX_VOLUNTEER_ID;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final VolunteerEditCommand.EditVolunteerDescriptor DESC_AMY;
    public static final VolunteerEditCommand.EditVolunteerDescriptor DESC_BOB;
    public static final EventEditCommand.EditEventDescriptor DESC_CLEANUP;
    public static final EventEditCommand.EditEventDescriptor DESC_HELPOUT;

    static {
        DESC_AMY = new EditVolunteerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withSkills(VALID_SKILL_FRIEND).build();
        DESC_BOB = new EditVolunteerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withSkills(VALID_SKILL_HUSBAND, VALID_SKILL_FRIEND).build();
    }

    static {
        DESC_CLEANUP = new EditEventDescriptorBuilder().withEventName(VALID_EVENTNAME_CLEANUP)
                .withRoles(VALID_ROLE_CLEANER).withStartDate(VALID_START_DATETIME_CLEANUP)
                .withEndDate(VALID_END_DATETIME_CLEANUP).withLocation(VALID_LOCATION_CLEANUP)
                .withDescription(VALID_DESCRIPTION_CLEANUP).withMaterials(VALID_MATERIAL_TRASHBAG)
                .withBudget(VALID_BUDGET_CLEANUP).build();

        DESC_HELPOUT = new EditEventDescriptorBuilder().withEventName(VALID_EVENTNAME_HELPOUT)
                .withRoles(VALID_ROLE_BRAIN).withStartDate(VALID_START_DATETIME_HELPOUT)
                .withEndDate(VALID_END_DATETIME_HELPOUT).withLocation(VALID_LOCATION_HELPOUT)
                .withDescription(VALID_DESCRIPTION_HELPOUT).withMaterials(VALID_MATERIAL_HANDS)
                .withBudget(VALID_BUDGET_HELPOUT).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered volunteer list and selected volunteer in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        VolunteerStorage expectedAddressBook = new VolunteerStorage(actualModel.getVolunteerStorage());
        List<Volunteer> expectedFilteredList = new ArrayList<>(actualModel.getFilteredVolunteerList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getVolunteerStorage());
        assertEquals(expectedFilteredList, actualModel.getFilteredVolunteerList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the volunteer at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showVolunteerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredVolunteerList().size());

        Volunteer volunteer = model.getFilteredVolunteerList().get(targetIndex.getZeroBased());
        final Name volunteerName = volunteer.getName();
        Name preppedName = new Name(volunteerName.fullName.split("\\s+")[0]);
        model.updateFilteredVolunteerList(new SkillNameContainsKeywordsPredicate(Arrays.asList(preppedName)));

        assertEquals(1, model.getFilteredVolunteerList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s iVolunteer.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getEventName().eventName.split("\\s+");

        for (String s : splitName) {
            model.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(s)));
        }

        assertEquals(1, model.getFilteredEventList().size());
    }

}
