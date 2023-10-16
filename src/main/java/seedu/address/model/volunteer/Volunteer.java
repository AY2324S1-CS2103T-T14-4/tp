package seedu.address.model.volunteer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
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

    /**
     * Every field must be present and not null.
     */
    public Volunteer(Name name, Phone phone, Email email, Set<Skill> skills) {
        requireAllNonNull(name, phone, email, skills);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.skills.addAll(skills);
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
     * Returns true if both volunteers have the same name.
     * This defines a weaker notion of equality between two volunteers.
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