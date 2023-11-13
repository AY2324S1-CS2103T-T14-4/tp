package seedu.address.logic.parser.volunteercommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.volunteercommands.VolunteerFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.skill.Skill;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.SkillNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class VolunteerFindCommandParser implements Parser<VolunteerFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VolunteerFindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SKILL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_SKILL) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerFindCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getValue(PREFIX_NAME).isPresent() || argMultimap.getValue(PREFIX_SKILL).isPresent()
                : "At least one PREFIX_NAME or PREFIX_SKILL should be present";

        Set<Name> nameKeywords = ParserUtil.parseVolunteerNames(argMultimap.getAllValues(PREFIX_NAME));
        List<Name> names = new ArrayList<>(nameKeywords);
        Set<Skill> skillKeywords = ParserUtil.parseSkills(argMultimap.getAllValues(PREFIX_SKILL));
        List<Skill> skills = new ArrayList<>(skillKeywords);

        return new VolunteerFindCommand(new SkillNameContainsKeywordsPredicate(names, skills));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
