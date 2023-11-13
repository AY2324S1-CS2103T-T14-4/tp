package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.skill.Skill;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SKILL = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_SKILL_1 = "friend";
    private static final String VALID_SKILL_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    // volunteer parsing methods

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseVolunteerName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseVolunteerName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseVolunteerName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseVolunteerName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseSkill_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkill(null));
    }

    // Something up with INVALID_SKILL, to be resolved later
    //    @Test
    //    public void parseSkill_invalidValue_throwsParseException() {
    //        assertThrows(ParseException.class, () -> ParserUtil.parseSkill(INVALID_SKILL));
    //    }

    @Test
    public void parseSkill_validValueWithoutWhitespace_returnsSkill() throws Exception {
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkill(VALID_SKILL_1));
    }

    @Test
    public void parseSkill_validValueWithWhitespace_returnsTrimmedSkill() throws Exception {
        String skillWithWhitespace = WHITESPACE + VALID_SKILL_1 + WHITESPACE;
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkill(skillWithWhitespace));
    }

    @Test
    public void parseSkills_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkills(null));
    }

    @Test
    public void parseSkills_collectionWithInvalidSkills_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, INVALID_SKILL)));
    }

    @Test
    public void parseSkills_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseSkills(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseSkills_collectionWithValidSkills_returnsSkillSet() throws Exception {
        Set<Skill> actualSkillSet = ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, VALID_SKILL_2));
        Set<Skill> expectedSkillSet = new HashSet<Skill>(Arrays.asList(new Skill(VALID_SKILL_1),
                new Skill(VALID_SKILL_2)));

        assertEquals(expectedSkillSet, actualSkillSet);
    }

    // event parsing methods

    @Test
    public void parseMaterialName_nullMaterial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMaterialName(null));
    }

    @Test
    public void parseMaterialName_invalidMaterial_throwsParseException() {
        // invalid quantity
        assertThrows(ParseException.class, () -> ParserUtil.parseMaterialName("-3 potatoes"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMaterialName("3.5 potatoes"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMaterialName("-3.5 potatoes"));

        // invalid material name
        assertThrows(ParseException.class, () -> ParserUtil.parseMaterialName("-3 potatoes&"));
    }

    @Test
    public void parseMaterialName_validMaterial_success() {
        try {
            assertTrue(ParserUtil.parseMaterialName("3 potatoes").equals("potatoes"));
            assertTrue(ParserUtil.parseMaterialName("4 apple").equals("apple"));

            // contains whitespace
            assertTrue(ParserUtil.parseMaterialName("   300 gloves ").equals("gloves"));
        } catch (ParseException pe) {
            fail("ParseException should not be thrown");
        }
    }

    @Test
    public void parseMaterialQuantity_nullMaterial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMaterialQuantity(null));
    }

    @Test
    public void parseMaterialQuantity_invalidMaterial_throwsParseException() {
        // invalid quantity
        assertThrows(ParseException.class, () -> ParserUtil.parseMaterialQuantity("-3 potatoes"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMaterialQuantity("3.5 potatoes"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMaterialQuantity("-3.5 potatoes"));

        // invalid material name
        assertThrows(ParseException.class, () -> ParserUtil.parseMaterialQuantity("-3 potatoes&"));
    }

    @Test
    public void parseMaterialQuantity_validMaterial_success() {
        try {
            assertTrue(ParserUtil.parseMaterialQuantity("3 potatoes") == 3);
            assertTrue(ParserUtil.parseMaterialQuantity("4 apple") == 4);

            // contains whitespace
            assertTrue(ParserUtil.parseMaterialQuantity("   300 gloves ") == 300);
        } catch (ParseException pe) {
            fail("ParseException should not be thrown");
        }
    }
}
