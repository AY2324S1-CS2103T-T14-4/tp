package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalid_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // valid description
        assertTrue(Description.isValidDescription("cleaning"));

        // empty string
        assertFalse(Description.isValidDescription(""));
    }

    @Test
    public void equals() {
        Description description = new Description("cleaning");

        // same values -> returns true
        assertTrue(description.equals(new Description("cleaning")));

        // same object -> returns true
        assertTrue(description.equals(description));

        // null -> returns false
        assertFalse(description.equals(null));

        // different values -> returns false
        assertFalse(description.equals(new Description("packing")));
    }
}
