package seedu.address.model.volunteer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.skill.Skill;

/**
 * Represents a Volnuteer in the VolunteerStorage.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Volunteer {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Set<Skill> skills = new HashSet<>();
    private final Set<EventName> assignedEvents = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Volunteer(Name name, Phone phone, Email email, Set<Skill> skills, Set<EventName> assignedEvents) {
        requireAllNonNull(name, phone, email, skills);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.skills.addAll(skills);
        this.assignedEvents.addAll(assignedEvents);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable skill set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Skill> getSkills() {
        return Collections.unmodifiableSet(skills);
    }
    /**
     * Returns an immutable assigned events set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<EventName> getAssignedEvents() {
        return Collections.unmodifiableSet(assignedEvents);
    }
    /**
     * Returns an immutable skill set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public boolean hasEvent(Event event) {
        return assignedEvents.contains(event.getEventName());
    }

    /**
     * Adds an event to the {@code assignedEvents}.
     * @param event The event to be added.
     * @return The volunteer after the addition of the new event.
     */
    public Volunteer addEvent(Event event) {
        Set<EventName> newEvents = new HashSet<>(assignedEvents);
        newEvents.add(event.getEventName());

        return new Volunteer(name, phone, email, skills, newEvents);
    }
    /**
     * Removes an event from the {@code assignedEvents}.
     * @param event The event to be removed.
     * @return The volunteer after the removal of the event.
     */
    public Volunteer removeEvent(Event event) {
        Set<EventName> newEvents = new HashSet<>(assignedEvents);
        newEvents.remove(event.getEventName());

        return new Volunteer(name, phone, email, skills, newEvents);
    }
    /**
     * Returns true if both volunteers have the same phone number or the same email.
     */
    public boolean isSameVolunteer(Volunteer otherVolunteer) {
        if (otherVolunteer == this) {
            return true;
        }

        return otherVolunteer != null
                && otherVolunteer.getName().equals(getName());
    }

    /**
     * Returns true if both volunteers have the same identity and data fields.
     * This defines a stronger notion of equality between two volunteers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Volunteer)) {
            return false;
        }

        Volunteer otherVolunteer = (Volunteer) other;
        return name.equals(otherVolunteer.name)
                && phone.equals(otherVolunteer.phone)
                && email.equals(otherVolunteer.email)
                && skills.equals(otherVolunteer.skills);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, skills);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("skills", skills)
                .toString();
    }

}
