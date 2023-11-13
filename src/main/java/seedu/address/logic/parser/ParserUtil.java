package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Budget;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Material;
import seedu.address.model.event.MaxVolunteerSize;
import seedu.address.model.event.Role;
import seedu.address.model.skill.Skill;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static EventName parseEventName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!EventName.isValidEventName(trimmedName)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(trimmedName);
    }

    /**
     * Parses {@code Collection<String> evnet names} into a {@code Set<String>}.
     * @throws ParseException if any of the strings in the Collection is invalid.
     */
    public static Set<String> parseEventNames(Collection<String> eventNames) throws ParseException {
        requireNonNull(eventNames);
        final Set<String> nameSet = new LinkedHashSet<>();
        for (String name : eventNames) {
            nameSet.add(parseEventNameToString(name));
        }
        return nameSet;
    }

    /**
     * Parses a {@code String name} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static String parseEventNameToString(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!EventName.isValidEventName(trimmedName)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return trimmedName;
    }

    /**
     * Parses a {@code String role} into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>}.
     */
    public static Set<Role> parseRoles(Collection<String> roles) throws ParseException {
        requireNonNull(roles);
        final Set<Role> roleSet = new HashSet<>();
        for (String roleName : roles) {
            roleSet.add(parseRole(roleName));
        }
        return roleSet;
    }

    /**
     * Parses a {@code String dateAndTime} into a {@code DateTime}.
     * @param dateAndTime The string to be parsed.
     * @return A DateTime object representing the date and time
     * @throws ParseException if the given {@code dateAndTime} is not valid.
     */
    public static DateTime parseDateAndTime(String dateAndTime) throws ParseException {
        requireNonNull(dateAndTime);
        String trimmedDateAndTime = dateAndTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateAndTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        return new DateTime(trimmedDateAndTime);
    }

    /**
     * Parses a {@code location} into a {@code Location}.
     * @param location The string to be parsed.
     * @return A Location object representing the location.
     * @throws ParseException if the {@code location} is not valid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses the {@code description} into a {@code Description}.
     * @param description The string to be parsed.
     * @return A Description object representing the description
     * @throws ParseException if the {@code description} is not valid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String material} into a {@code Material}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code material} is invalid.
     */
    public static Material parseMaterial(String material) throws ParseException {
        requireNonNull(material);
        String trimmedMaterial = material.trim();
        if (!Material.isValidMaterial(trimmedMaterial)) {
            throw new ParseException(Material.MESSAGE_CONSTRAINTS);
        }

        return new Material(trimmedMaterial);
    }

    /**
     * Gets the name from a {@code String material}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code material} is invalid.
     */
    public static String parseMaterialName(String material) throws ParseException {
        requireNonNull(material);
        String trimmedMaterial = material.trim();
        if (!Material.isValidMaterial(trimmedMaterial)) {
            throw new ParseException(Material.MESSAGE_CONSTRAINTS);
        }

        return Material.nameFromString(trimmedMaterial);
    }

    /**
     * Gets the quantity from a {@code String material}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code material} is invalid.
     */
    public static int parseMaterialQuantity(String material) throws ParseException {
        requireNonNull(material);
        String trimmedMaterial = material.trim();
        if (!Material.isValidMaterial(trimmedMaterial)) {
            throw new ParseException(Material.MESSAGE_CONSTRAINTS);
        }

        return Material.quantityFromString(trimmedMaterial);
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Skill>}.
     */
    public static Set<Material> parseMaterials(Collection<String> materials) throws ParseException {
        requireNonNull(materials);
        final Set<Material> materialSet = new HashSet<>();
        for (String material : materials) {
            materialSet.add(parseMaterial(material));
        }
        return materialSet;
    }

    /**
     * Parses a {@code String budget} into a {@code Budget}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code budget} is invalid.
     */
    public static Budget parseBudget(String budget) throws ParseException {
        requireNonNull(budget);
        String trimmedBudget = budget.trim();
        if (!Budget.isValidBudget(trimmedBudget)) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS);
        }
        return new Budget(trimmedBudget);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseVolunteerName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String mvs} into a {@code MaxVolunteerSize}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code mvs} is invalid.
     */
    public static MaxVolunteerSize parseMaxVolunteerSize(String mvs) throws ParseException {
        requireNonNull(mvs);
        String trimmedMvs = mvs.trim();
        if (!MaxVolunteerSize.isValidMaxVolunteerSize(trimmedMvs)) {
            throw new ParseException(MaxVolunteerSize.MESSAGE_CONSTRAINTS);
        }
        return new MaxVolunteerSize(trimmedMvs);
    }

    /**
     * Parses {@code Collection<String> names} into a {@code Set<Name>}.
     */
    public static Set<Name> parseVolunteerNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        final Set<Name> nameSet = new LinkedHashSet<>();
        // for some reason a normal HashSet will automatically reorder the elements that you add into it,
        // presumably by lexicographic order, so must use LinkedHashSet instead
        for (String name : names) {
            nameSet.add(parseVolunteerName(name));
        }
        return nameSet;
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String skill} into a {@code Skill}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code skill} is invalid.
     */
    public static Skill parseSkill(String skill) throws ParseException {
        requireNonNull(skill);
        String trimmedSkill = skill.trim();
        if (!Skill.isValidSkillName(trimmedSkill)) {
            throw new ParseException(Skill.MESSAGE_CONSTRAINTS);
        }
        return new Skill(trimmedSkill);
    }

    /**
     * Parses {@code Collection<String> skills} into a {@code Set<Skill>}.
     * @throws ParseException if any of the skills in the Collection is invalid.
     */
    public static Set<Skill> parseSkills(Collection<String> skills) throws ParseException {
        requireNonNull(skills);
        final Set<Skill> skillSet = new LinkedHashSet<>();
        for (String skillName : skills) {
            skillSet.add(parseSkill(skillName));
        }
        return skillSet;
    }
}
