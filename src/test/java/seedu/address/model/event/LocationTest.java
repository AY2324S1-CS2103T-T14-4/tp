package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalid_throwsIllegalArgumentException() {
        String invalidLocation = "";
        assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }

    @Test
    public void isValidLocation() {
        // valid location
        assertTrue(Location.isValidLocation("hougang"));

        // empty string
        assertFalse(Location.isValidLocation(""));
    }

    @Test
    public void equals() {
        Location location = new Location("hougang");

        // same values -> returns true
        assertTrue(location.equals(new Location("hougang")));

        // same object -> returns true
        assertTrue(location.equals(location));

        // null -> returns false
        assertFalse(location.equals(null));

        // different values -> returns false
        assertFalse(location.equals(new Location("sembawang")));
    }
}
