package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_VOLUNTEERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalVolunteers.CARL;
import static seedu.address.testutil.TypicalVolunteers.ELLE;
import static seedu.address.testutil.TypicalVolunteers.FIONA;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.volunteercommands.VolunteerFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.skill.Skill;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.SkillNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class VolunteerFindCommandTest {
    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(),
                                                    new UserPrefs());

    @Test
    public void equals() {
        SkillNameContainsKeywordsPredicate firstPredicate =
                new SkillNameContainsKeywordsPredicate(Collections.singletonList(new Name("first")),
                        new ArrayList<>());
        SkillNameContainsKeywordsPredicate secondPredicate =
                new SkillNameContainsKeywordsPredicate(Collections.singletonList(new Name("second")),
                        new ArrayList<>());

        VolunteerFindCommand findFirstCommand = new VolunteerFindCommand(firstPredicate);
        VolunteerFindCommand findSecondCommand = new VolunteerFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        VolunteerFindCommand findFirstCommandCopy = new VolunteerFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different volunteer -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_unknownVolunteer_noVolunteerFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_VOLUNTEERS_LISTED_OVERVIEW, 0);
        SkillNameContainsKeywordsPredicate predicate = preparePredicate(" n/rando");
        VolunteerFindCommand command = new VolunteerFindCommand(predicate);
        expectedModel.updateFilteredVolunteerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredVolunteerList());
    }

    @Test
    public void execute_multipleKeywords_multipleVolunteersFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_VOLUNTEERS_LISTED_OVERVIEW, 3);
        SkillNameContainsKeywordsPredicate predicate = preparePredicate(" n/Kurz n/Elle n/Kunz");
        VolunteerFindCommand command = new VolunteerFindCommand(predicate);
        expectedModel.updateFilteredVolunteerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredVolunteerList());
    }

    @Test
    public void toStringMethod() {
        SkillNameContainsKeywordsPredicate predicate =
                new SkillNameContainsKeywordsPredicate(Arrays.asList(new Name("keyword")), new ArrayList<>());
        VolunteerFindCommand volunteerFindCommand = new VolunteerFindCommand(predicate);
        String expected = VolunteerFindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, volunteerFindCommand.toString());
    }

    @Test
    public void preparePredicateTest() throws ParseException {
        SkillNameContainsKeywordsPredicate predicate = preparePredicate(" n/Alice n/Bob");
        assertEquals(SkillNameContainsKeywordsPredicate.class.getCanonicalName()
                + "{names=[Alice, Bob], skills=[]}", predicate.toString());
    }


    /**
     * Parses {@code userInput} into a {@code SkillNameContainsKeywordsPredicate}.
     */
    public static SkillNameContainsKeywordsPredicate preparePredicate(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SKILL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_SKILL)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerFindCommand.MESSAGE_USAGE));
        }

        Set<Name> nameKeywords = ParserUtil.parseVolunteerNames(argMultimap.getAllValues(PREFIX_NAME));
        List<Name> names = new ArrayList<>(nameKeywords);
        Set<Skill> skillKeywords = ParserUtil.parseSkills(argMultimap.getAllValues(PREFIX_SKILL));
        List<Skill> skills = new ArrayList<>(skillKeywords);

        return new SkillNameContainsKeywordsPredicate(names, skills);
    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
