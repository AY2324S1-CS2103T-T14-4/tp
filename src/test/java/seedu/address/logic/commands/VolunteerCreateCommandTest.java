package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVolunteers.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.volunteercommands.VolunteerCreateCommand;
import seedu.address.model.EventStorage;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyEventStorage;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyVolunteerStorage;
import seedu.address.model.VersionedEventStorage;
import seedu.address.model.VersionedVolunteerStorage;
import seedu.address.model.VolunteerStorage;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.VolunteerBuilder;

public class VolunteerCreateCommandTest {

    @Test
    public void constructor_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new VolunteerCreateCommand(null));
    }

    @Test
    public void execute_volunteerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVolunteerAdded modelStub = new ModelStubAcceptingVolunteerAdded();
        Volunteer validVolunteer = new VolunteerBuilder().build();

        CommandResult commandResult = new VolunteerCreateCommand(validVolunteer).execute(modelStub);

        assertEquals(String.format(VolunteerCreateCommand.MESSAGE_SUCCESS, Messages.format(validVolunteer)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validVolunteer), modelStub.volunteersAdded);
    }

    @Test
    public void execute_duplicateVolunteer_throwsCommandException() {
        Volunteer validVolunteer = new VolunteerBuilder().build();
        VolunteerCreateCommand volunteerCreateCommand = new VolunteerCreateCommand(validVolunteer);
        ModelStub modelStub = new ModelStubWithVolunteer(validVolunteer);

        assertThrows(CommandException.class, VolunteerCreateCommand.MESSAGE_DUPLICATE_VOLUNTEER, ()
                                                    -> volunteerCreateCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Volunteer alice = new VolunteerBuilder().withName("Alice").build();
        Volunteer bob = new VolunteerBuilder().withName("Bob").build();
        VolunteerCreateCommand addAliceCommand = new VolunteerCreateCommand(alice);
        VolunteerCreateCommand addBobCommand = new VolunteerCreateCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        VolunteerCreateCommand addAliceCommandCopy = new VolunteerCreateCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different volunteer -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        VolunteerCreateCommand volunteerCreateCommand = new VolunteerCreateCommand(ALICE);
        String expected = VolunteerCreateCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, volunteerCreateCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getVolunteerStorageFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getEventStorageFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVolunteerStorageFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEventStorageFilePath(Path eventStorageFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addVolunteer(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVolunteerStorage(ReadOnlyVolunteerStorage newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEventStorage(ReadOnlyEventStorage newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyVolunteerStorage getVolunteerStorage() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEventStorage getEventStorage() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteVolunteer(Volunteer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVolunteer(Volunteer target, Volunteer editedVolunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Volunteer> getFilteredVolunteerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredVolunteerList(Predicate<Volunteer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FilteredList<Event> getEventToShowList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEventToShowList(Predicate<Event> eventPredicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoBothStorages() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void commitToBothVersionedStorages(ReadOnlyEventStorage readOnlyEventStorage,
                                                  ReadOnlyVolunteerStorage readOnlyVolunteerStorage) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void redoBothStorages() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public VersionedEventStorage getVersionedEventStorage() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public VersionedVolunteerStorage getVersionedVolunteerStorage() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Event updateEventRoleQuantities(Event event) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateAllEventRoleQuantities() {
            throw new AssertionError("This method should not be called");
        }
    }

    /**
     * A Model stub that contains a single volunteer.
     */
    private class ModelStubWithVolunteer extends ModelStub {
        private final Volunteer volunteer;

        ModelStubWithVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            this.volunteer = volunteer;
        }

        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            return this.volunteer.isSameVolunteer(volunteer);
        }
    }

    /**
     * A Model stub that always accept the volunteer being added.
     */
    private class ModelStubAcceptingVolunteerAdded extends ModelStub {
        final ArrayList<Volunteer> volunteersAdded = new ArrayList<>();

        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            return volunteersAdded.stream().anyMatch(volunteer::isSameVolunteer);
        }

        @Override
        public void addVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            volunteersAdded.add(volunteer);
        }

        @Override
        public ReadOnlyVolunteerStorage getVolunteerStorage() {
            return new VolunteerStorage();
        }

        @Override
        public ReadOnlyEventStorage getEventStorage() {
            return new EventStorage();
        }
        @Override
        public void undoBothStorages() {
            // does nothing
        }
        @Override
        public void commitToBothVersionedStorages(ReadOnlyEventStorage readOnlyEventStorage,
                                                  ReadOnlyVolunteerStorage readOnlyVolunteerStorage) {
            // does nothing
        }
    }

}
