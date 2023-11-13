package seedu.address.model.skill;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Skill in the volunteer storage.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSkillName(String)}
 */
public class Skill {

    public static final String MESSAGE_CONSTRAINTS = "Skills should be alphanumeric";
    // Skill now accepts multiple words as a single Skill
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String skillName;

    /**
     * Constructs a {@code Skill}.
     *
     * @param skillName A valid skill name.
     */
    public Skill(String skillName) {
        requireNonNull(skillName);
        checkArgument(isValidSkillName(skillName), MESSAGE_CONSTRAINTS);
        this.skillName = skillName;
    }

    /**
     * Returns true if a given string is a valid skill name.
     */
    public static boolean isValidSkillName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Skill)) {
            return false;
        }

        Skill otherSkill = (Skill) other;
        return skillName.equals(otherSkill.skillName);
    }

    @Override
    public int hashCode() {
        return skillName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return skillName;
    }

}
