package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalVolunteers.CARL;
import static seedu.address.testutil.TypicalVolunteers.ELLE;
import static seedu.address.testutil.TypicalVolunteers.FIONA;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.volunteerCommands.VolunteerFindCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.volunteer.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class VolunteerFindCommandTest {
    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(),
                                                    new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        VolunteerFindCommand findFirstCommand = new VolunteerFindCommand(firstPredicate);
        VolunteerFindCommand findSecondCommand = new VolunteerFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        VolunteerFindCommand findFirstCommandCopy = new VolunteerFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        VolunteerFindCommand command = new VolunteerFindCommand(predicate);
        expectedModel.updateFilteredVolunteerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredVolunteerList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        VolunteerFindCommand command = new VolunteerFindCommand(predicate);
        expectedModel.updateFilteredVolunteerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredVolunteerList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        VolunteerFindCommand volunteerFindCommand = new VolunteerFindCommand(predicate);
        String expected = VolunteerFindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, volunteerFindCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
