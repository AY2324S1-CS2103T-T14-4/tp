package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.volunteer.UniqueVolunteerList;
import seedu.address.model.volunteer.Volunteer;

/**
 * Stores the history of Volunteer.
 */
public class VersionedVolunteerStorage extends VolunteerStorage {
    private ArrayList<UniqueVolunteerList> versionedVolunteers = new ArrayList<>();
    /**
     * Points to the current state of Volunteers.
     */
    private int currentStatePointer;
    /**
     * Upon running the app, initialises the history of Volunteers.
     */
    public VersionedVolunteerStorage(ReadOnlyVolunteerStorage toBeCopied) {
        initState(toBeCopied);
        this.currentStatePointer = 0;
    }

    /**
     * Stores the initial state of Volunteers in versionedVolunteers.
     */
    public void initState(ReadOnlyVolunteerStorage initialState) {
        requireNonNull(initialState);
        UniqueVolunteerList uniqueVolunteerList = generateUniqueVolunteerList(initialState);
        this.versionedVolunteers.add(uniqueVolunteerList);
    }
    /**
     * When commands that change the current state of Volunteers are executed, currentStatePointer is shifted
     * to the newly inserted state of Volunteers.
     */
    public void shiftPointerForward() {
        this.currentStatePointer += 1;
    }

    /**
     * When undo command is executed, currentStatePointer is shifted to point to the previous state of Volunteers.
     */
    public void shiftPointerBackwards() {
        this.currentStatePointer -= 1;
    }

    /**
     * When commands that change the current state of Volunteers are executed, the new state of Volunteers will be
     * saved and added to versionedVolunteers.
     * @param readOnlyVolunteerStorage The new state of Volunteers.
     */
    public void saveNewState(ReadOnlyVolunteerStorage readOnlyVolunteerStorage) {
        UniqueVolunteerList newState = generateUniqueVolunteerList(readOnlyVolunteerStorage);;
        if (versionedVolunteers.size() > currentStatePointer) {
            versionedVolunteers = new ArrayList<>(versionedVolunteers.subList(0, currentStatePointer));
        }
        assert versionedVolunteers.size() == currentStatePointer;
        this.versionedVolunteers.add(currentStatePointer, newState);
    }
    /**
     * Points to the previous state of Volunteers and returns it.
     */
    public List<Volunteer> undo() throws CommandException {
        canUndoVersionedEvents();
        shiftPointerBackwards();
        UniqueVolunteerList newState = this.versionedVolunteers.get(currentStatePointer);
        return newState.asUnmodifiableObservableList();
    }
    private void canUndoVersionedEvents() throws CommandException {
        if (this.currentStatePointer == 0) {
            throw new CommandException("Cannot undo any further");
        }
        assert currentStatePointer > 0;
    }
    /**
     * asd
     * @return asd
     * @throws CommandException asd
     */
    public List<Volunteer> redo() throws CommandException {
        canRedoVersionedEvents();
        shiftPointerForward();
        UniqueVolunteerList newState = this.versionedVolunteers.get(currentStatePointer);
        return newState.asUnmodifiableObservableList();
    }
    private void canRedoVersionedEvents() throws CommandException {
        if (versionedVolunteers.size() == currentStatePointer + 1) {
            throw new CommandException("Unable to redo");
        }
        assert currentStatePointer < versionedVolunteers.size();
    }
    private static UniqueVolunteerList generateUniqueVolunteerList(ReadOnlyVolunteerStorage readOnlyVolunteerStorage) {
        List<Volunteer> volunteers = readOnlyVolunteerStorage.getVolunteerList();
        UniqueVolunteerList uniqueVolunteerList = new UniqueVolunteerList();
        uniqueVolunteerList.setVolunteers(volunteers);
        return uniqueVolunteerList;
    }
}