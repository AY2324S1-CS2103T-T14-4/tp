package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.VolunteerStorage;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class containing a list of {@code Volunteer} objects to be used in tests.
 */
public class TypicalVolunteers {
    public static final Volunteer ALICE = new VolunteerBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withSkills("friends").build();
    public static final Volunteer BENSON = new VolunteerBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withSkills("owesMoney", "friends").build();
    public static final Volunteer CARL = new VolunteerBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").build();
    public static final Volunteer DANIEL = new VolunteerBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withSkills("friends").build();
    public static final Volunteer ELLE = new VolunteerBuilder().withName("Elle Meyer").withPhone("94822242")
            .withEmail("werner@example.com").build();
    public static final Volunteer FIONA = new VolunteerBuilder().withName("Fiona Kunz").withPhone("94824273")
            .withEmail("lydia@example.com").build();
    public static final Volunteer GEORGE = new VolunteerBuilder().withName("George Best").withPhone("94824425")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Volunteer HOON = new VolunteerBuilder().withName("Hoon Meier").withPhone("84824241")
            .withEmail("stefan@example.com").build();
    public static final Volunteer IDA = new VolunteerBuilder().withName("Ida Mueller").withPhone("84821319")
            .withEmail("hans@example.com").build();

    // Manually added - Volunteer's details found in {@code CommandTestUtil}
    public static final Volunteer AMY = new VolunteerBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withSkills(VALID_SKILL_FRIEND).build();
    public static final Volunteer BOB = new VolunteerBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withSkills(VALID_SKILL_HUSBAND, VALID_SKILL_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalVolunteers() {} // prevents instantiation

    /**
     * Returns an {@code VolunteerStorage} with all the typical volunteers.
     */
    public static VolunteerStorage getTypicalVolunteerStorage() {
        VolunteerStorage ab = new VolunteerStorage();
        for (Volunteer volunteer : getTypicalVolunteers()) {
            ab.addVolunteer(volunteer);
        }
        return ab;
    }

    public static List<Volunteer> getTypicalVolunteers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
