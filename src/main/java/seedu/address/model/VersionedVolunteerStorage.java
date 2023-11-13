package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.volunteer.UniqueVolunteerList;
import seedu.address.model.volunteer.Volunteer;

/**
 * Stores the history of Volunteers.
 */
public class VersionedVolunteerStorage extends VolunteerStorage {
    private final ArrayList<UniqueVolunteerList> versionedVolunteers = new ArrayList<>();
    private int currentStatePointer;
    private final Logger logger = LogsCenter.getLogger(VersionedVolunteerStorage.class);
    /**
     * Upon running the app, initialises the history of Volunteers.
     */
    public VersionedVolunteerStorage(ReadOnlyVolunteerStorage toBeCopied) {
        initState(toBeCopied);
        currentStatePointer = 0;
    }

    /**
     * Stores the initial state of Volunteers in versionedVolunteers.
     */
    public void initState(ReadOnlyVolunteerStorage initialState) {
        requireNonNull(initialState);
        UniqueVolunteerList uniqueVolunteerList = generateUniqueVolunteerList(initialState);
        versionedVolunteers.add(uniqueVolunteerList);
        logger.info("Initialising versioned volunteers history");
    }

    /**
     * Shift the currentStatePointer to the next state of Volunteers.
     */
    public void shiftPointerForward() {
        currentStatePointer += 1;
    }

    /**
     * Shift the currentStatePointer to point to the previous state of Volunteers.
     */
    public void shiftPointerBackwards() {
        currentStatePointer -= 1;
    }

    public int getCurrentStatePointer() {
        return currentStatePointer;
    }

    public int getVersionedVolunteersSize() {
        return versionedVolunteers.size();
    }

    /**
     * When commands that change the current state of Volunteers are executed, the new state of Volunteers will be
     * saved and added to versionedVolunteers.
     * @param readOnlyVolunteerStorage The new state of Volunteers.
     */
    public void saveNewState(ReadOnlyVolunteerStorage readOnlyVolunteerStorage) {
        UniqueVolunteerList newState = generateUniqueVolunteerList(readOnlyVolunteerStorage);
        requireNonNull(readOnlyVolunteerStorage);
        if (versionedVolunteers.size() > currentStatePointer) {
            trimVersionedVolunteers();
        }
        assert versionedVolunteers.size() == currentStatePointer;
        versionedVolunteers.add(currentStatePointer, newState);
    }
    private void trimVersionedVolunteers() {
        int size = versionedVolunteers.size();
        for (int i = size - 1; i >= currentStatePointer; i--) {
            // changed implementation to remove from largest index to smallest, if not will result in
            // InvocationTargetException in certain cases
            versionedVolunteers.remove(i);
        }
    }
    /**
     * Points to the previous state of Volunteers and returns it.
     * @throws CommandException if currentStatePointer is pointing to the initial state of Volunteers.
     */
    public List<Volunteer> undo() throws CommandException {
        canUndoVersionedVolunteers();
        shiftPointerBackwards();
        assert currentStatePointer >= 0;
        return getCurrentVolunteerState();
    }
    private void canUndoVersionedVolunteers() throws CommandException {
        if (currentStatePointer == 0) {
            logger.info("Tried to undo when current state pointer points at initial version of volunteers");
            throw new CommandException("Cannot undo any further");
        }
        assert currentStatePointer > 0;
    }
    /**
     * Points to the next state of Volunteers and returns it.
     * @throws CommandException if currentStatePointer is pointing to the latest state of Volunteers.
     */
    public List<Volunteer> redo() throws CommandException {
        canRedoVersionedVolunteers();
        shiftPointerForward();
        assert currentStatePointer < versionedVolunteers.size();
        return getCurrentVolunteerState();
    }
    private List<Volunteer> getCurrentVolunteerState() {
        assert versionedVolunteers.size() > currentStatePointer;
        UniqueVolunteerList newState = versionedVolunteers.get(currentStatePointer);
        return newState.asUnmodifiableObservableList();
    }
    private void canRedoVersionedVolunteers() throws CommandException {
        if (versionedVolunteers.size() == currentStatePointer + 1) {
            logger.info("Tried to redo when current state pointer points at latest version of volunteers");
            throw new CommandException("Unable to redo");
        }
        assert currentStatePointer < versionedVolunteers.size();
    }
    private static UniqueVolunteerList generateUniqueVolunteerList(ReadOnlyVolunteerStorage readOnlyVolunteerStorage) {
        requireNonNull(readOnlyVolunteerStorage);
        List<Volunteer> volunteers = readOnlyVolunteerStorage.getVolunteerList();
        UniqueVolunteerList uniqueVolunteerList = new UniqueVolunteerList();
        uniqueVolunteerList.setVolunteers(volunteers);
        return uniqueVolunteerList;
    }
}
