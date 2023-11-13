package seedu.address.logic.commands.volunteercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VOLUNTEERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventName;
import seedu.address.model.skill.Skill;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;

/**
 * Edits the details of an existing volunteer in the address book.
 */
public class VolunteerEditCommand extends Command {

    public static final String COMMAND_WORD = "vedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the volunteer identified "
            + "by the index number used in the displayed volunteer list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_SKILL + "SKILL]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_VOLUNTEER_SUCCESS = "Edited Volunteer: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "This volunteer already exists in the volunteer list.";

    private final Index index;
    private final EditVolunteerDescriptor editVolunteerDescriptor;

    /**
     * @param index of the volunteer in the filtered volunteer list to edit
     * @param editVolunteerDescriptor details to edit the volunteer with
     */
    public VolunteerEditCommand(Index index, EditVolunteerDescriptor editVolunteerDescriptor) {
        requireNonNull(index);
        requireNonNull(editVolunteerDescriptor);

        this.index = index;
        this.editVolunteerDescriptor = new EditVolunteerDescriptor(editVolunteerDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Volunteer> lastShownList = model.getFilteredVolunteerList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        Volunteer volunteerToEdit = lastShownList.get(index.getZeroBased());
        Volunteer editedVolunteer = createEditedVolunteer(volunteerToEdit, editVolunteerDescriptor);

        if (!volunteerToEdit.isSameVolunteer(editedVolunteer) && model.hasVolunteer(editedVolunteer)) {
            throw new CommandException(MESSAGE_DUPLICATE_VOLUNTEER);
        }

        // set volunteer first
        model.setVolunteer(volunteerToEdit, editedVolunteer);
        model.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
        // edit all role quantities to match the new edited volunteer
        model.updateAllEventRoleQuantities();
        model.commitToBothVersionedStorages(model.getEventStorage(), model.getVolunteerStorage());
        return new CommandResult(String.format(MESSAGE_EDIT_VOLUNTEER_SUCCESS, Messages.format(editedVolunteer)));
    }

    /**
     * Creates and returns a {@code Volunteer} with the details of {@code volunteerToEdit}
     * edited with {@code editVolunteerDescriptor}.
     */
    private static Volunteer createEditedVolunteer(Volunteer volunteerToEdit,
                                                   EditVolunteerDescriptor editVolunteerDescriptor) {
        assert volunteerToEdit != null;

        Name updatedName = editVolunteerDescriptor.getName().orElse(volunteerToEdit.getName());
        Phone updatedPhone = editVolunteerDescriptor.getPhone().orElse(volunteerToEdit.getPhone());
        Email updatedEmail = editVolunteerDescriptor.getEmail().orElse(volunteerToEdit.getEmail());
        Set<Skill> updatedSkills = editVolunteerDescriptor.getSkills().orElse(volunteerToEdit.getSkills());
        Set<EventName> updatedAssignedEvents = editVolunteerDescriptor.getAssignedEvents()
                .orElse(volunteerToEdit.getAssignedEvents());

        return new Volunteer(updatedName, updatedPhone, updatedEmail, updatedSkills, updatedAssignedEvents);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerEditCommand)) {
            return false;
        }

        VolunteerEditCommand otherVolunteerEditCommand = (VolunteerEditCommand) other;
        return index.equals(otherVolunteerEditCommand.index)
                && editVolunteerDescriptor.equals(otherVolunteerEditCommand.editVolunteerDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editVolunteerDescriptor", editVolunteerDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the volunteer with. Each non-empty field value will replace the
     * corresponding field value of the volunteer.
     */
    public static class EditVolunteerDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Set<Skill> skills;
        private Set<EventName> assignedEvents;

        public EditVolunteerDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code skills} is used internally.
         */
        public EditVolunteerDescriptor(EditVolunteerDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setSkills(toCopy.skills);
            setAssignedEvents(toCopy.assignedEvents);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, skills, assignedEvents);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /**
         * Sets {@code skills} to this object's {@code skills}.
         * A defensive copy of {@code skills} is used internally.
         */
        public void setSkills(Set<Skill> skills) {
            this.skills = (skills != null) ? new HashSet<>(skills) : null;
        }

        /**
         * Returns an unmodifiable skill set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code skills} is null.
         */
        public Optional<Set<Skill>> getSkills() {
            return (skills != null) ? Optional.of(Collections.unmodifiableSet(skills)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable assigned event set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code assignedEvents} is null.
         */
        public Optional<Set<EventName>> getAssignedEvents() {
            return (assignedEvents != null) ? Optional.of(Collections.unmodifiableSet(assignedEvents))
                    : Optional.empty();
        }

        /**
         * Sets {@code assignedEvents} to this object's {@code assignedEvents}.
         * A defensive copy of {@code assignedEvents} is used internally.
         */
        public void setAssignedEvents(Set<EventName> assignedEvents) {
            this.assignedEvents = (assignedEvents != null) ? new HashSet<>(assignedEvents) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditVolunteerDescriptor)) {
                return false;
            }

            EditVolunteerDescriptor otherEditVolunteerDescriptor = (EditVolunteerDescriptor) other;
            return Objects.equals(name, otherEditVolunteerDescriptor.name)
                    && Objects.equals(phone, otherEditVolunteerDescriptor.phone)
                    && Objects.equals(email, otherEditVolunteerDescriptor.email)
                    && Objects.equals(skills, otherEditVolunteerDescriptor.skills)
                    && Objects.equals(assignedEvents, otherEditVolunteerDescriptor.assignedEvents);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("skills", skills)
                    .add("assigned events", assignedEvents)
                    .toString();
        }
    }
}
