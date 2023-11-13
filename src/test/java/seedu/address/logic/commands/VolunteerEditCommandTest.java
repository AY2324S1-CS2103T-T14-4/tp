package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVolunteerAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.volunteercommands.VolunteerEditCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerEditCommand.EditVolunteerDescriptor;
import seedu.address.logic.commands.volunteercommands.VolunteerListCommand;
import seedu.address.model.EventStorage;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VolunteerStorage;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;
import seedu.address.testutil.VolunteerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class VolunteerEditCommandTest {

    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Volunteer editedVolunteer = new VolunteerBuilder().build();
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(editedVolunteer).build();
        VolunteerEditCommand volunteerEditCommand = new VolunteerEditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(VolunteerEditCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS,
                                                                        Messages.format(editedVolunteer));

        Model expectedModel = new ModelManager(new EventStorage(model.getEventStorage()),
                                                new VolunteerStorage(model.getVolunteerStorage()), new UserPrefs());
        expectedModel.setVolunteer(model.getFilteredVolunteerList().get(0), editedVolunteer);

        assertCommandSuccess(volunteerEditCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        VolunteerEditCommand volunteerEditCommand = new VolunteerEditCommand(INDEX_FIRST,
                                                                        new EditVolunteerDescriptor());
        Volunteer editedVolunteer = model.getFilteredVolunteerList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(VolunteerEditCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS,
                                                                    Messages.format(editedVolunteer));

        Model expectedModel = new ModelManager(new EventStorage(model.getEventStorage()),
                                                new VolunteerStorage(model.getVolunteerStorage()), new UserPrefs());

        assertCommandSuccess(volunteerEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showVolunteerAtIndex(model, INDEX_FIRST);

        Volunteer volunteerInFilteredList = model.getFilteredVolunteerList().get(INDEX_FIRST.getZeroBased());
        Volunteer editedVolunteer = new VolunteerBuilder(volunteerInFilteredList).withName(VALID_NAME_BOB).build();
        VolunteerEditCommand volunteerEditCommand = new VolunteerEditCommand(INDEX_FIRST,
                new EditVolunteerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(VolunteerEditCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS,
                                                                    Messages.format(editedVolunteer));

        Model expectedModel = new ModelManager(new EventStorage(model.getEventStorage()),
                                                new VolunteerStorage(model.getVolunteerStorage()), new UserPrefs());
        expectedModel.setVolunteer(model.getFilteredVolunteerList().get(0), editedVolunteer);

        assertCommandSuccess(volunteerEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateVolunteerUnfilteredList_failure() {
        Volunteer firstVolunteer = model.getFilteredVolunteerList().get(INDEX_FIRST.getZeroBased());
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(firstVolunteer).build();
        VolunteerEditCommand volunteerEditCommand = new VolunteerEditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(volunteerEditCommand, model, VolunteerEditCommand.MESSAGE_DUPLICATE_VOLUNTEER);
    }

    @Test
    public void execute_duplicateVolunteerFilteredList_failure() {
        showVolunteerAtIndex(model, INDEX_FIRST);

        // edit volunteer in filtered list into a duplicate in volunteer storage
        Volunteer volunteerInList = model.getVolunteerStorage().getVolunteerList()
                                    .get(INDEX_SECOND.getZeroBased());
        VolunteerEditCommand volunteerEditCommand = new VolunteerEditCommand(INDEX_FIRST,
                new EditVolunteerDescriptorBuilder(volunteerInList).build());

        assertCommandFailure(volunteerEditCommand, model, VolunteerEditCommand.MESSAGE_DUPLICATE_VOLUNTEER);
    }

    @Test
    public void execute_invalidVolunteerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        VolunteerEditCommand volunteerEditCommand = new VolunteerEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(volunteerEditCommand, model, Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of volunteer storage
     */
    @Test
    public void execute_invalidVolunteerIndexFilteredList_failure() {
        showVolunteerAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of volunteer storage
        assertTrue(outOfBoundIndex.getZeroBased() < model.getVolunteerStorage().getVolunteerList().size());

        VolunteerEditCommand volunteerEditCommand = new VolunteerEditCommand(outOfBoundIndex,
                new EditVolunteerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(volunteerEditCommand, model, Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final VolunteerEditCommand standardCommand = new VolunteerEditCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditVolunteerDescriptor copyDescriptor = new EditVolunteerDescriptor(DESC_AMY);
        VolunteerEditCommand commandWithSameValues = new VolunteerEditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new VolunteerListCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new VolunteerEditCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new VolunteerEditCommand(INDEX_FIRST, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditVolunteerDescriptor editVolunteerDescriptor = new EditVolunteerDescriptor();
        VolunteerEditCommand volunteerEditCommand = new VolunteerEditCommand(index, editVolunteerDescriptor);
        String expected = VolunteerEditCommand.class.getCanonicalName()
                + "{index=" + index + ", editVolunteerDescriptor=" + editVolunteerDescriptor + "}";
        assertEquals(expected, volunteerEditCommand.toString());
    }

}
