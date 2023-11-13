package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EventFindCommandTest;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.VolunteerFindCommandTest;
import seedu.address.logic.commands.eventcommands.EventAddMaterialCommand;
import seedu.address.logic.commands.eventcommands.EventCreateCommand;
import seedu.address.logic.commands.eventcommands.EventDeleteCommand;
import seedu.address.logic.commands.eventcommands.EventEditCommand;
import seedu.address.logic.commands.eventcommands.EventFindCommand;
import seedu.address.logic.commands.eventcommands.EventListCommand;
import seedu.address.logic.commands.eventcommands.EventShowCommand;
import seedu.address.logic.commands.eventvolunteercommands.EventAddVolunteerCommand;
import seedu.address.logic.commands.eventvolunteercommands.EventListVolunteerCommand;
import seedu.address.logic.commands.eventvolunteercommands.EventRemoveVolunteerCommand;
import seedu.address.logic.commands.eventvolunteercommands.VolunteerListEventCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerCreateCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerDeleteCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerEditCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerEditCommand.EditVolunteerDescriptor;
import seedu.address.logic.commands.volunteercommands.VolunteerFindCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.volunteer.SkillNameContainsKeywordsPredicate;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;
import seedu.address.testutil.VolunteerBuilder;
import seedu.address.testutil.VolunteerUtil;

public class IVolunteerParserTest {

    private final IVolunteerParser parser = new IVolunteerParser();

    @Test
    public void parseCommand_eventCreate() throws Exception {
        Event event = new EventBuilder().withRoles("50 chef").build();
        String events = EventUtil.getEventCreateCommand(event);
        EventCreateCommand command = (EventCreateCommand) parser.parseCommand(EventUtil
                .getEventCreateCommand(event));
        assertEquals(new EventCreateCommand(event), command);
    }
    @Test
    public void parseCommand_volunteerCreate() throws Exception {
        Volunteer volunteer = new VolunteerBuilder().build();
        VolunteerCreateCommand command = (VolunteerCreateCommand) parser.parseCommand(VolunteerUtil
                                            .getAddCommand(volunteer));
        assertEquals(new VolunteerCreateCommand(volunteer), command);
    }

    @Test
    public void parseCommand_volunteerDelete() throws Exception {

        VolunteerDeleteCommand command = (VolunteerDeleteCommand) parser.parseCommand(
                VolunteerDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new VolunteerDeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_eventDelete() throws Exception {

        EventDeleteCommand command = (EventDeleteCommand) parser.parseCommand(
                EventDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new EventDeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_volunteerEdit() throws Exception {
        Volunteer volunteer = new VolunteerBuilder().build();
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(volunteer).build();
        VolunteerEditCommand command = (VolunteerEditCommand) parser
                .parseCommand(VolunteerEditCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + VolunteerUtil.getEditVolunteerDescriptorDetails(descriptor)
                );
        assertEquals(new VolunteerEditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_eventEdit() throws Exception {
        Event event = new EventBuilder().build();
        EventEditCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EventEditCommand command = (EventEditCommand) parser
                .parseCommand(EventEditCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + EventUtil.getEditEventDescriptorDetails(descriptor)
                );
        assertEquals(new EventEditCommand(INDEX_FIRST, descriptor), command);
    }
    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_volunteerFind() throws Exception {
        String input = " n/foo n/bar n/baz";
        SkillNameContainsKeywordsPredicate predicate = VolunteerFindCommandTest.preparePredicate(input);
        VolunteerFindCommand command = (VolunteerFindCommand) parser.parseCommand(
                VolunteerFindCommand.COMMAND_WORD + input);
        assertEquals(new VolunteerFindCommand(predicate), command);
    }
    @Test
    public void parseCommand_eventFind() throws Exception {
        String input = " n/foo n/bar n/baz";
        EventNameContainsKeywordsPredicate predicate = EventFindCommandTest.preparePredicate(input);
        EventFindCommand command = (EventFindCommand) parser.parseCommand(
                EventFindCommand.COMMAND_WORD + input);
        assertEquals(new EventFindCommand(predicate), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_eventShow() throws Exception {
        EventShowCommand command = (EventShowCommand) parser.parseCommand(
                EventShowCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new EventShowCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_volunteerList() throws Exception {
        assertTrue(parser.parseCommand(VolunteerListCommand.COMMAND_WORD) instanceof VolunteerListCommand);
        assertTrue(parser.parseCommand(
                VolunteerListCommand.COMMAND_WORD + " 3") instanceof VolunteerListCommand);
    }

    @Test
    public void parseCommand_eventList() throws Exception {
        assertTrue(parser.parseCommand(EventListCommand.COMMAND_WORD) instanceof EventListCommand);
        assertTrue(parser.parseCommand(
                EventListCommand.COMMAND_WORD + " 3") instanceof EventListCommand
        );
    }

    @Test
    public void parseCommand_volunteerListEvent() throws Exception {
        VolunteerListEventCommand command = (VolunteerListEventCommand) parser.parseCommand(
                VolunteerListEventCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new VolunteerListEventCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(
                UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(
                RedoCommand.COMMAND_WORD + " 3") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_eventAddVolunteer() throws Exception {
        Command parsedCommand = parser.parseCommand(
                EventAddVolunteerCommand.COMMAND_WORD + " eid/1 vid/1");
        assertTrue(parsedCommand instanceof EventAddVolunteerCommand);
        EventAddVolunteerCommand command = (EventAddVolunteerCommand) parsedCommand;
        assertEquals(new EventAddVolunteerCommand(INDEX_FIRST, INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_eventListVolunteer() throws Exception {
        EventListVolunteerCommand command = (EventListVolunteerCommand) parser.parseCommand(
                EventListVolunteerCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new EventListVolunteerCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_eventRemoveVolunteer() throws Exception {
        Command parsedCommand = parser.parseCommand(
                EventRemoveVolunteerCommand.COMMAND_WORD + " eid/1 vid/1");
        assertTrue(parsedCommand instanceof EventRemoveVolunteerCommand);
        EventRemoveVolunteerCommand command = (EventRemoveVolunteerCommand) parsedCommand;
        assertEquals(new EventRemoveVolunteerCommand(INDEX_FIRST, INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_eventAddMaterial() throws Exception {
        Command parsedCommand = parser.parseCommand(
                EventAddMaterialCommand.COMMAND_WORD + " eid/1 m/20 potatoes");
        assertTrue(parsedCommand instanceof EventAddMaterialCommand);
        EventAddMaterialCommand command = (EventAddMaterialCommand) parsedCommand;
        assertEquals(new EventAddMaterialCommand(INDEX_FIRST, 20, "potatoes"), command);
    }


    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
                                    -> parser.parseCommand("unknownCommand"));
    }
}
