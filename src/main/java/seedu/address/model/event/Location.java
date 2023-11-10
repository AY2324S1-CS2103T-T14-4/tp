package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's location in the Event list.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_CONSTRAINTS = "Location can take any values, and it should not be blank";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9 !\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_` ]+$";

    public final String location;

    /**
     * Constructs a {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        this.location = location;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return location;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Location)) {
            return false;
        }

        Location otherLocation = (Location) other;
        return location.equals(otherLocation.location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

}
