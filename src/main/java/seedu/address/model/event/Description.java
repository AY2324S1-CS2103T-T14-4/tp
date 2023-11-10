package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's description.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Description can take any values, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9 !\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_` ]+$";

    public final String description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDescription = (Description) other;
        return description.equals(otherDescription.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}
