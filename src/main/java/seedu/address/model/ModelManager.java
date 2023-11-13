package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.Role;
import seedu.address.model.skill.Skill;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Volunteer;

/**
 * Represents the in-memory model of the eventStorage and volunteerStorage data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VolunteerStorage volunteerStorage;
    private final VersionedVolunteerStorage versionedVolunteerStorage;
    private final EventStorage eventStorage;
    private final VersionedEventStorage versionedEventStorage;
    private final UserPrefs userPrefs;
    private final FilteredList<Volunteer> filteredVolunteers;
    private final FilteredList<Event> filteredEvents;
    private final FilteredList<Event> eventToShowList;

    /**
     * Initializes a ModelManager with the given eventStorage, volunteerStorage and userPrefs.
     */
    public ModelManager(ReadOnlyEventStorage eventStorage, ReadOnlyVolunteerStorage volunteerStorage,
                        ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(volunteerStorage, eventStorage, userPrefs);

        logger.fine("Initializing with volunteer storage: " + volunteerStorage + " and user prefs " + userPrefs);
        logger.fine("Initializing with event storage: " + eventStorage + " and user prefs " + userPrefs);

        this.eventStorage = new EventStorage(eventStorage);
        this.volunteerStorage = new VolunteerStorage(volunteerStorage);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEvents = new FilteredList<>(this.eventStorage.getEventList());
        filteredVolunteers = new FilteredList<>(this.volunteerStorage.getVolunteerList());
        eventToShowList = new FilteredList<>(this.eventStorage.getEventList());

        this.versionedEventStorage = new VersionedEventStorage(eventStorage);
        this.versionedVolunteerStorage = new VersionedVolunteerStorage(volunteerStorage);
    }

    public ModelManager() {
        this(new EventStorage(), new VolunteerStorage(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getVolunteerStorageFilePath() {
        return userPrefs.getVolunteerStorageFilePath();
    }

    @Override
    public Path getEventStorageFilePath() {
        return userPrefs.getEventStorageFilePath();
    }

    @Override
    public void setVolunteerStorageFilePath(Path volunteerStorageFilePath) {
        requireNonNull(volunteerStorageFilePath);
        userPrefs.setVolunteerStorageFilePath(volunteerStorageFilePath);
    }

    @Override
    public void setEventStorageFilePath(Path eventStorageFilePath) {
        requireNonNull(eventStorageFilePath);
        userPrefs.setEventStorageFilePath(eventStorageFilePath);
    }

    //=========== Event and Volunteer versioned history ========================================================
    @Override
    public void undoBothStorages() throws CommandException {
        List<Volunteer> newVolunteerState = versionedVolunteerStorage.undo();
        assert newVolunteerState != null;
        volunteerStorage.setVolunteers(newVolunteerState);
        updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);

        List<Event> newEventState = versionedEventStorage.undo();
        assert newEventState != null;
        eventStorage.setEvents(newEventState);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        logger.info("Size of version history is: "
                + versionedEventStorage.getVersionedEventsSize()
                + ". Current state pointer is: "
                + versionedEventStorage.getCurrentStatePointer());
    }
    @Override
    public void redoBothStorages() throws CommandException {
        List<Volunteer> newVolunteerState = versionedVolunteerStorage.redo();
        assert newVolunteerState != null;
        volunteerStorage.setVolunteers(newVolunteerState);
        updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);

        List<Event> newEventState = versionedEventStorage.redo();
        assert newEventState != null;
        eventStorage.setEvents(newEventState);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        logger.info("Size of version history is: "
                + versionedEventStorage.getVersionedEventsSize()
                + ". Current state pointer is: "
                + versionedEventStorage.getCurrentStatePointer());
    }
    @Override
    public void commitToBothVersionedStorages(ReadOnlyEventStorage readOnlyEventStorage,
                                              ReadOnlyVolunteerStorage readOnlyVolunteerStorage) {
        requireAllNonNull(readOnlyEventStorage, readOnlyVolunteerStorage);
        versionedEventStorage.shiftPointerForward();
        versionedEventStorage.saveNewState(readOnlyEventStorage);
        versionedVolunteerStorage.shiftPointerForward();
        versionedVolunteerStorage.saveNewState(readOnlyVolunteerStorage);

        logger.info("Committed to version history. Size of version history is: "
                + versionedEventStorage.getVersionedEventsSize()
                + ". Current state pointer is: "
                + versionedEventStorage.getCurrentStatePointer());
    }
    @Override
    public VersionedVolunteerStorage getVersionedVolunteerStorage() {
        return versionedVolunteerStorage;
    }
    @Override
    public VersionedEventStorage getVersionedEventStorage() {
        return versionedEventStorage;
    }

    //=========== Event Storage ================================================================================
    @Override
    public void setEventStorage(ReadOnlyEventStorage eventStorage) {
        this.eventStorage.resetData(eventStorage);
    }

    @Override
    public ReadOnlyEventStorage getEventStorage() {
        return eventStorage;
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return eventStorage.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        eventStorage.removeEvent(target);
    }

    @Override
    public void addEvent(Event event) {
        eventStorage.addEvent(event);
        eventStorage.sortEvents();
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        eventStorage.setEvent(target, editedEvent);
    }

    //=========== Volunteer Storage ================================================================================

    @Override
    public void setVolunteerStorage(ReadOnlyVolunteerStorage volunteerStorage) {
        this.volunteerStorage.resetData(volunteerStorage);
    }

    @Override
    public ReadOnlyVolunteerStorage getVolunteerStorage() {
        return volunteerStorage;
    }

    @Override
    public boolean hasVolunteer(Volunteer volunteer) {
        requireNonNull(volunteer);
        return volunteerStorage.hasVolunteer(volunteer);
    }

    @Override
    public void deleteVolunteer(Volunteer target) {
        volunteerStorage.removeVolunteer(target);
    }

    @Override
    public void addVolunteer(Volunteer volunteer) {
        volunteerStorage.addVolunteer(volunteer);
        updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
    }

    @Override
    public void setVolunteer(Volunteer target, Volunteer editedVolunteer) {
        requireAllNonNull(target, editedVolunteer);

        volunteerStorage.setVolunteer(target, editedVolunteer);
    }

    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedEventStorage}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
        eventToShowList.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedEventStorage}
     */
    @Override
    public FilteredList<Event> getEventToShowList() {
        return eventToShowList;
    }

    @Override
    public void updateEventToShowList(Predicate<Event> eventPredicate) {
        requireNonNull(eventPredicate);
        eventToShowList.setPredicate(eventPredicate);
    }

    @Override
    public void updateAllEventRoleQuantities() {
        // eventList is the list of all events
        List<Event> eventList = getEventStorage().getEventList();
        for (Event event : eventList) {
            Event newEvent = updateEventRoleQuantities(event);
            setEvent(event, newEvent);
        }
    }

    @Override
    public Event updateEventRoleQuantities(Event event) {
        Set<Role> roles = event.getRoles();
        // mutable version of roles to be updated
        Set<Role> updatedRoles = new HashSet<>();
        Set<Name> volunteerNames = event.getVolunteerNames();
        // filteredVolunteerList is the list of volunteers in event
        List<Volunteer> filteredVolunteerList = getVolunteerStorage()
                .getVolunteerList()
                .stream()
                .filter(volunteer -> volunteerNames.contains(volunteer.getName()))
                .collect(Collectors.toList());
        for (Role role : roles) {
            // initialise as role with 0 current manpower before counting
            Role updatedRole = new Role(role.roleName, 0, role.requiredQuantity);
            for (Volunteer volunteer : filteredVolunteerList) {
                for (Skill skill : volunteer.getSkills()) {
                    if (role.roleName.equals(skill.skillName)) {
                        updatedRole = updatedRole.addRoleManpower();
                    }
                }
            }
            updatedRoles.add(updatedRole);
        }
        return new Event(
                event.getEventName(),
                updatedRoles,
                event.getStartDate(),
                event.getEndDate(),
                event.getLocation(),
                event.getDescription(),
                event.getMaterials(),
                event.getBudget(),
                event.getAssignedVolunteers(),
                event.getMaxVolunteerSize());
    }

    //=========== Filtered Volunteer Storage Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Volunteer} backed by the internal list of
     * {@code versionedVolunteerStorage}
     */
    @Override
    public ObservableList<Volunteer> getFilteredVolunteerList() {
        return filteredVolunteers;
    }

    @Override
    public void updateFilteredVolunteerList(Predicate<Volunteer> predicate) {
        requireNonNull(predicate);
        filteredVolunteers.setPredicate(predicate);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return volunteerStorage.equals(otherModelManager.volunteerStorage)
                && eventStorage.equals(otherModelManager.eventStorage)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredVolunteers.equals(otherModelManager.filteredVolunteers)
                && filteredEvents.equals(otherModelManager.filteredEvents)
                && eventToShowList.equals(otherModelManager.eventToShowList);
    }
}
