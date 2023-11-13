package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
        assertThrows(NullPointerException.class, () -> new Role(null, 3));
        assertThrows(NullPointerException.class, () -> new Role(null, 1, 2));
    }

    @Test
    public void constructor_invalid_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRoleName() {
        // valid role
        assertTrue(Role.isValidRoleName("chef"));

        // empty string
        assertFalse(Role.isValidRoleName(""));

        // null name
        assertThrows(NullPointerException.class, () -> Role.isValidRoleName(null));

        // invalid name
        assertFalse(Role.isValidRoleName("")); // empty string
        assertFalse(Role.isValidRoleName(" ")); // spaces only
        assertFalse(Role.isValidRoleName("^")); // only non-alphanumeric characters
        assertFalse(Role.isValidRoleName("food yummy*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Role.isValidRoleName("volunteers")); // alphabets only
        assertTrue(Role.isValidRoleName("2349")); // numbers only
        assertTrue(Role.isValidRoleName("potato farmers 30")); // alphanumeric characters
        assertTrue(Role.isValidRoleName("A Lot Of People")); // with capital letters
        assertTrue(Role.isValidRoleName("50 kilogram bag of flour carriers")); // long names
    }

    @Test
    public void isValidQuantity() {
        // invalid numbers (negative numbers)
        assertFalse(Role.isValidQuantity(-3));
        assertFalse(Role.isValidQuantity(Integer.MIN_VALUE));

        // valid numbers   (0 and positive numbers)
        assertTrue(Role.isValidQuantity(0)); // zero
        assertTrue(Role.isValidQuantity(1)); // positive numbers
        assertTrue(Role.isValidQuantity(Integer.MAX_VALUE)); // positive numbers
    }

    @Test
    public void isValidRole() {
        // null role with quantity
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid roles
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("^")); // only non-alphanumeric characters
        assertFalse(Role.isValidRole("food yummy*")); // contains non-alphanumeric characters

        // roles without quantity
        assertFalse(Role.isValidRole("cleaners")); // alphabets only
        assertFalse(Role.isValidRole("2349")); // numbers only
        assertFalse(Role.isValidRole("carriers of 1kg potatoes")); // alphanumeric characters
        assertFalse(Role.isValidRole("Lot Of People")); // with capital letters
        assertFalse(Role.isValidRole("Two-kilogram bags of flour carriers")); // long names

        // invalid roles with quantity
        assertFalse(Role.isValidRole("42 ")); // spaces only
        assertFalse(Role.isValidRole("53 ^")); // only non-alphanumeric characters
        assertFalse(Role.isValidRole("12 food yummy*")); // contains non-alphanumeric characters

        // invalid numbers (negative numbers)
        assertFalse(Role.isValidRole("-23 cleaners")); // alphabets only
        assertFalse(Role.isValidRole("-50 2349")); // numbers only
        assertFalse(Role.isValidRole("-34 carriers of 1kg potatoes")); // alphanumeric characters
        assertFalse(Role.isValidRole("-1 Lot Of People")); // with capital letters
        assertFalse(Role.isValidRole("-20 2 kilogram bags of flour carriers")); // long names

        // valid roles
        assertTrue(Role.isValidRole("23 cleaners")); // alphabets only
        assertTrue(Role.isValidRole("50 2349")); // numbers only
        assertTrue(Role.isValidRole("34 carriers of 1kg potatoes")); // alphanumeric characters
        assertTrue(Role.isValidRole("1 Lot Of People")); // with capital letters
        assertTrue(Role.isValidRole("20 2 kilogram bags of flour carriers")); // long names

    }

    @Test
    public void nameFromString_nullRoleName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Role.nameFromString(null));
    }

    @Test
    public void nameFromString_invalidFormat_throwsIllegalArgumentException() {
        // no number for quantity
        assertThrows(IllegalArgumentException.class, () -> Role.nameFromString("john"));

        // negative numbers
        assertThrows(IllegalArgumentException.class, () -> Role.nameFromString("-2 bakers"));

        // non-whole numbers
        assertThrows(IllegalArgumentException.class, () -> Role.nameFromString("3.5 bakers"));
        assertThrows(IllegalArgumentException.class, () -> Role.nameFromString("-1.5 bakers"));

        // no role name
        assertThrows(IllegalArgumentException.class, () -> Role.nameFromString("51"));

        // invalid role name
        assertThrows(IllegalArgumentException.class, () -> Role.nameFromString("3 bakers&"));
    }

    @Test
    public void nameFromString_validFormat_success() {
        assertTrue(Role.nameFromString("2 bakers").equals("bakers"));
        assertTrue(Role.nameFromString("0 cake makers").equals("cake makers"));
    }

    @Test
    public void quantityFromString_nullRoleName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Role.quantityFromString(null));
    }

    @Test
    public void quantityFromString_invalidFormat_throwsIllegalArgumentException() {
        // no number for quantity
        assertThrows(IllegalArgumentException.class, () -> Role.quantityFromString("john"));

        // negative numbers
        assertThrows(IllegalArgumentException.class, () -> Role.quantityFromString("-2 bakers"));

        // non-whole numbers
        assertThrows(IllegalArgumentException.class, () -> Role.quantityFromString("3.5 bakers"));
        assertThrows(IllegalArgumentException.class, () -> Role.quantityFromString("-1.5 bakers"));

        // no role name
        assertThrows(IllegalArgumentException.class, () -> Role.quantityFromString("51"));

        // invalid role name
        assertThrows(IllegalArgumentException.class, () -> Role.quantityFromString("3 bakers&"));
    }

    @Test
    public void quantityFromString_validFormat_success() {
        assertTrue(Role.quantityFromString("2 bakers") == 2);
        assertTrue(Role.quantityFromString("0 cake makers") == 0);
    }

    @Test
    public void addRoleManpower() {
        Role role = new Role("50 bakers");
        Role newRole = role.addRoleManpower();
        Role expectedRole = new Role("bakers", 1, 50);

        assertTrue(newRole.equals(expectedRole));
    }

    @Test
    public void decreaseRoleManpower() {
        // initialise role with 3 bakers
        Role role = new Role("bakers", 3, 50);
        Role newRole = role.decreaseRoleManpower();
        Role expectedRole = new Role("bakers", 2, 50);

        assertTrue(newRole.equals(expectedRole));
    }

    @Test
    public void hasEnoughManpower() {
        Role roleNoPeople = new Role("50 bakers");
        Role roleSomePeople = new Role("bakers", 20, 50);
        Role roleEqualPeople = new Role("bakers", 50, 50);
        Role roleMorePeople = new Role("bakers", 70, 50);

        assertFalse(roleNoPeople.hasEnoughManpower());
        assertFalse(roleSomePeople.hasEnoughManpower());
        assertTrue(roleEqualPeople.hasEnoughManpower());
        assertTrue(roleMorePeople.hasEnoughManpower());
    }

    @Test
    public void toUiString() {
        Role roleNoPeople = new Role("50 bakers");
        Role roleSomePeople = new Role("bakers", 20, 50);
        Role roleEqualPeople = new Role("bakers", 50, 50);
        Role roleMorePeople = new Role("bakers", 70, 50);

        assertTrue(roleNoPeople.toUiString().equals("0 / 50 bakers"));
        assertTrue(roleSomePeople.toUiString().equals("20 / 50 bakers"));
        assertTrue(roleEqualPeople.toUiString().equals("50 / 50 bakers"));
        assertTrue(roleMorePeople.toUiString().equals("70 / 50 bakers"));
    }

    @Test
    public void fromUiString() {
        Role roleNoPeople = new Role("50 bakers");
        Role roleSomePeople = new Role("bakers", 20, 50);
        Role roleEqualPeople = new Role("bakers", 50, 50);
        Role roleMorePeople = new Role("bakers", 70, 50);

        assertTrue(roleNoPeople.equals(Role.fromUiString(roleNoPeople.toUiString())));
        assertTrue(roleNoPeople.equals(Role.fromUiString("50 bakers")));
        assertTrue(roleSomePeople.equals(Role.fromUiString(roleSomePeople.toUiString())));
        assertTrue(roleEqualPeople.equals(Role.fromUiString(roleEqualPeople.toUiString())));
        assertTrue(roleMorePeople.equals(Role.fromUiString(roleMorePeople.toUiString())));
    }

    @Test
    public void equals() {
        // same name and quantity - return true
        Role role1a = new Role("50 bakers");
        Role role1b = new Role("bakers", 50);
        Role role1c = new Role("bakers", 0, 50);

        assertTrue(role1a.equals(role1a)); // equals itself
        assertTrue(role1a.equals(role1b));
        assertTrue(role1b.equals(role1c));
        assertTrue(role1a.equals(role1c));

        // same name and different current quantity - return false
        Role role2a = new Role("50 bakers");
        Role role2b = new Role("bakers", 50);
        Role role2c = new Role("bakers", 10, 50);
        Role role2d = new Role("bakers", 40, 50);
        assertFalse(role2a.equals(role2c));
        assertFalse(role2b.equals(role2c));
        assertFalse(role2c.equals(role2d));

        // different name and same current and required quantity - return false
        Role role3a = new Role("50 bakers");
        Role role3b = new Role("50 decorators");
        Role role3c = new Role("bakers", 50);
        Role role3d = new Role("decorators", 50);
        assertFalse(role3a.equals(role3b));
        assertFalse(role3c.equals(role3d));

        // additional checks
        assertTrue(role3a.equals(role3c));
        assertTrue(role3b.equals(role3d));

        // same name and different required quantity - return false
        Role role4a = new Role("50 bakers");
        Role role4b = new Role("bakers", 13);
        Role role4c = new Role("bakers", 40, 60);
        Role role4d = new Role("bakers", 40, 50);
        assertFalse(role4a.equals(role4b));
        assertFalse(role4c.equals(role4d));

        // different typed object and null
        Role role5a = new Role("50 bakers");
        String role5b = "50 bakers";
        assertFalse(role5a.equals(role5b));
        assertFalse(role5a.equals(null));
    }
}
