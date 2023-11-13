package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.UniqueEventList;

/**
 * Wraps all data at the event storage level
 * Duplicates are not allowed (by .isSameEvent comparison)
 */
public class EventStorage implements ReadOnlyEventStorage {

    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        events = new UniqueEventList();
    }

    public EventStorage() {}

    /**
     * Creates an EventStorage using the Events in the {@code toBeCopied}
     */
    public EventStorage(ReadOnlyEventStorage toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the volunteer storage with {@code volunteers}.
     * {@code volunteers} must not contain duplicate volunteers.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code EventStorage} with {@code newData}.
     */
    public void resetData(ReadOnlyEventStorage newData) {
        requireNonNull(newData);

        setEvents(newData.getEventList());
    }

    //// event-level operations
    /**
     * Returns true if an event with the same identity as {@code event} exists in the event list.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the event storage.
     * The event must not already exist in the event storage.
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the event storage.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the event storage.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
    }
    /**
     * Returns the event in the event storage with the {@code eventName}.
     */
    @Override
    public Event getEvent(EventName eventName) {
        requireNonNull(eventName);
        return events.getEvent(eventName);
    }
    /**
     * Removes {@code key} from this {@code EventStorage}.
     * {@code key} must exist in the event storage.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    /**
     * Sorts UniqueEventList in ascending order, by their date and time.
     */
    public void sortEvents() {
        events.sortEvents();
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("events", events)
                .toString();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventStorage)) {
            return false;
        }

        EventStorage otherEventStorage = (EventStorage) other;
        return events.equals(otherEventStorage.events);
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
