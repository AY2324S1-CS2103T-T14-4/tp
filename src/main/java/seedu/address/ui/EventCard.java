package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label eventName;
    @FXML
    private Label id;
    @FXML
    private Label dateAndTime;
    @FXML
    private Label loc;
    @FXML
    private Label description;
    @FXML
    private FlowPane materials;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        eventName.setText(event.getEventName().name);
        dateAndTime.setText(event.getDateAndTime().dateTime.toString());
        loc.setText(event.getLocation().location);
        description.setText(event.getDescription().description);
        event.getMaterials().stream()
                .sorted(Comparator.comparing(material -> material.material))
                .forEach(material -> materials.getChildren().add(new Label(material.material)));
    }
}