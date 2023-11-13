package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BudgetTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void isValidBudget() {
        // valid budget
        assertTrue(Budget.isValidBudget("50.00"));

        // empty string
        assertFalse(Budget.isValidBudget(""));

        // alphabet string
        assertFalse(Budget.isValidBudget("abcd"));

        // 1 decimal place
        assertFalse(Budget.isValidBudget("50.0"));

        // no decimal places
        assertFalse(Budget.isValidBudget("50"));
    }

    @Test
    public void equals() {
        Budget budget = new Budget("50.00");

        // same values -> returns true
        assertTrue(budget.equals(new Budget("50.00")));

        // same object -> returns true
        assertTrue(budget.equals(budget));

        // null -> returns false
        assertFalse(budget.equals(null));

        // different values -> returns false
        assertFalse(budget.equals(new Budget("20.00")));
    }
}
