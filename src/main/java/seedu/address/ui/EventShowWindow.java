package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;

/**
 * Pop-up window showing all information of a particular event.
 */
public class EventShowWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(EventShowWindow.class);
    private static final String FXML = "EventShowWindow.fxml";
    private final ObservableList<Event> eventToShowList;

    @FXML
    private ScrollPane eventShowView;
    @FXML
    private VBox eventToShow;
    @FXML
    private Label eventName;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label loc;
    @FXML
    private Label description;
    @FXML
    private Label maxVolunteerSize;
    @FXML
    private VBox materials;

    @FXML
    private Label budget;

    @FXML
    private VBox roles;

    /**
     * Creates an EventShowWindow object.
     * @param root Stage to use as the root of the EventShowWindow.
     * @param eventToShowList List containing the event to show.
     */
    public EventShowWindow(Stage root, ObservableList<Event> eventToShowList) {
        super(FXML, root);
        this.eventToShowList = eventToShowList;
    }

    public EventShowWindow(ObservableList<Event> eventToShow) {
        this(new Stage(), eventToShow);
    }

    /**
     * Shows the event window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing the event window.");

        // Closes event window when user presses on the ESC key
        Scene scene = getRoot().getScene();
        scene.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                logger.info("User pressed ESC key in event show window.");
                getRoot().close();
            }
        });

        getRoot().show();
        getRoot().centerOnScreen();
    }

    void loadContents() {
        // Clear roles and materials from previous eshow commmand call
        roles.getChildren().clear();
        materials.getChildren().clear();

        Event eventToShow = eventToShowList.get(0);
        eventName.setText("Name of event: " + eventToShow.getEventName().eventName);
        startDate.setText("Start date: " + eventToShow.getStartDate().toString());
        endDate.setText("End date: " + eventToShow.getEndDate().toString());
        loc.setText("Location: " + eventToShow.getLocation().location);
        eventToShow.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.roleName))
                .forEach(role -> roles.getChildren().add(new Label("\u2022 " + role.toUiString())));
        budget.setText("Budget: " + eventToShow.getBudget().budget);
        eventToShow.getMaterials().stream()
                .sorted(Comparator.comparing(material -> material.material))
                .forEach(material -> materials.getChildren().add(new Label("\u2022 " + material.toUiString())));
        description.setText("Description: " + eventToShow.getDescription().description);
        maxVolunteerSize.setText("Maximum number of volunteers in event: "
                + eventToShow.getMaxVolunteerSize().toUiString()
        );
    }

    /**
     * Returns true if the event window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the event window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the event window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
