package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;

/**
 * Stores the history of Events.
 */
public class VersionedEventStorage extends EventStorage {
    private final ArrayList<UniqueEventList> versionedEvents = new ArrayList<>();
    private int currentStatePointer;
    private final Logger logger = LogsCenter.getLogger(VersionedEventStorage.class);
    /**
     * Upon running the app, initialises the history of Events.
     */
    public VersionedEventStorage(ReadOnlyEventStorage toBeCopied) {
        initState(toBeCopied);
        currentStatePointer = 0;
    }

    /**
     * Stores the initial state of Events in versionedEvents.
     */
    public void initState(ReadOnlyEventStorage initialState) {
        requireNonNull(initialState);
        UniqueEventList uniqueEventList = generateUniqueEventList(initialState);
        versionedEvents.add(uniqueEventList);
        logger.info("Initialising versioned events history");
    }

    /**
     * Shift the currentStatePointer to the next state of Events.
     */
    public void shiftPointerForward() {
        currentStatePointer += 1;
    }

    /**
     * Shift the currentStatePointer to point to the previous state of Events.
     */
    public void shiftPointerBackwards() {
        currentStatePointer -= 1;
    }

    public int getCurrentStatePointer() {
        return currentStatePointer;
    }

    public int getVersionedEventsSize() {
        return versionedEvents.size();
    }

    /**
     * When commands that change the current state of Events are executed, the new state of Events will be
     * saved and added to versionedEvents.
     * @param readOnlyEventStorage The new state of Events
     */
    public void saveNewState(ReadOnlyEventStorage readOnlyEventStorage) {
        requireNonNull(readOnlyEventStorage);
        UniqueEventList newState = generateUniqueEventList(readOnlyEventStorage);
        if (versionedEvents.size() > currentStatePointer) {
            trimVersionedEvents();
        }
        assert versionedEvents.size() == currentStatePointer;
        versionedEvents.add(currentStatePointer, newState);
    }
    private void trimVersionedEvents() {
        int size = versionedEvents.size();
        for (int i = size - 1; i >= currentStatePointer; i--) {
            // changed implementation to remove from largest index to smallest, if not will result in
            // InvocationTargetException in certain cases
            versionedEvents.remove(i);
        }
    }
    /**
     * Points to the previous state of Events and returns it.
     * @throws CommandException if currentStatePointer is pointing to the initial state of Events.
     */
    public List<Event> undo() throws CommandException {
        canUndoVersionedEvents();
        shiftPointerBackwards();
        assert currentStatePointer >= 0;
        return getCurrentEventState();
    }
    private void canUndoVersionedEvents() throws CommandException {
        if (currentStatePointer == 0) {
            logger.info("Tried to undo when current state pointer points at initial version of volunteers");
            throw new CommandException("Cannot undo any further");
        }
        assert currentStatePointer > 0;
    }
    /**
     * Points to the next state of Events and returns it.
     * @throws CommandException if currentStatePointer is pointing to the latest state of Events.
     */
    public List<Event> redo() throws CommandException {
        canRedoVersionedEvents();
        shiftPointerForward();
        assert currentStatePointer < versionedEvents.size();
        return getCurrentEventState();
    }
    private List<Event> getCurrentEventState() {
        assert versionedEvents.size() > currentStatePointer;
        UniqueEventList newState = versionedEvents.get(currentStatePointer);
        return newState.asUnmodifiableObservableList();
    }
    private void canRedoVersionedEvents() throws CommandException {
        if (versionedEvents.size() == currentStatePointer + 1) {
            logger.info("Tried to redo when current state pointer points at latest version of volunteers");
            throw new CommandException("Unable to redo");
        }
        assert currentStatePointer < versionedEvents.size();
    }
    private static UniqueEventList generateUniqueEventList(ReadOnlyEventStorage readOnlyEventStorage) {
        requireNonNull(readOnlyEventStorage);
        List<Event> events = readOnlyEventStorage.getEventList();
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.setEvents(events);
        return uniqueEventList;
    }
}
