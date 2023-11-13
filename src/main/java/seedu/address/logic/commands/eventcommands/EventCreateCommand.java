package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATERIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_VOLUNTEER_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an event to the EventStorage.
 */
public class EventCreateCommand extends Command {

    public static final String COMMAND_WORD = "ecreate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the event list.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ROLE + "ROLE WITH QUANTITY "
            + PREFIX_START_DATETIME + "START DATE AND TIME "
            + "[" + PREFIX_END_DATETIME + "END DATE AND TIME] "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_MATERIAL + "MATERIAL WITH QUANTITY]... "
            + "[" + PREFIX_BUDGET + "BUDGET]... "
            + "[" + PREFIX_MAX_VOLUNTEER_SIZE + "MAX VOLUNTEER COUNT]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Clean up at Orchard "
            + PREFIX_ROLE + "10 Cleaner "
            + PREFIX_ROLE + "10 Manager "
            + PREFIX_START_DATETIME + "23/10/2023 1500 "
            + PREFIX_END_DATETIME + "23/10/2023 1900 "
            + PREFIX_LOCATION + "Orchard Road "
            + PREFIX_DESCRIPTION + "Cleaning up Orchard Road! "
            + PREFIX_MATERIAL + "10 Trash bag "
            + PREFIX_MATERIAL + "10 Tongs "
            + PREFIX_BUDGET + "50.00 "
            + PREFIX_MAX_VOLUNTEER_SIZE + "5";
    public static final String MESSAGE_SUCCESS = "New EVENT added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event list";

    private final Event toAdd;

    /**
     * Creates an EventCreateCommand to add the specified {@code Event}
     */
    public EventCreateCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        model.commitToBothVersionedStorages(model.getEventStorage(), model.getVolunteerStorage());
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCreateCommand)) {
            return false;
        }

        EventCreateCommand otherEventCreateCommand = (EventCreateCommand) other;
        return toAdd.equals(otherEventCreateCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
