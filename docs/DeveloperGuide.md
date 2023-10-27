---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# iVolunteer Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)
* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a volunteer).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Create Event Feature

#### Implementation
To encapsulate the aspects of an event, a new `Event` class is created. The Event class has fields representing the various aspects of an Event, namely `eventName`, `roles`, `startDate`, `endDate`, `location`, `description`, `materials` and `budget`. New classes were also created to facilitate the logic of these fields, which are `EventName`, `Role`, `Location`, `Description`, `Material` and `Budget`.

To store the created events, a new class `EventStorage` is created, which effectively acts as the list storing all created events. A reference to the EventStorage is kept in the ModelManager to facilitate logical operations.

After creating an event, the new EventStorage is saved by converting it to a `JsonSerializableEventStorage` and writing it to the JSON file, `eventStorage.json`.

Given below is an example usage scenario and how the create event mechanism behaves at each step.

Step 1:
The user launches the application and enters the command to create a new event.
`ecreate n/Clean up the beach r/cleaner sd/23/10/23 1900 l/East Coast Park dsc/Pick up all litter on the beach m/10 trash bags b/50.00`. After parsing all the arguments, a new `Event` object is created along with its respective fields.
<puml src="diagrams/EventClassDiagram.puml" alt="EventClassDiagram" />

Step 2:
The created `Event` object is passed to `EventCreateCommand` and executed. During its execution, the application checks the `EventStorage` in the `ModelManager` if an event with the same `eventName` already exists. If not, the new `Event` is added to the `EventStorage`.
<puml src="diagrams/EventCreateSequenceDiagram.puml" alt="EventCreateSequenceDiagram" />

Step 3:
When the `EventCreateCommand` finishes executing, the updated `EventStorage` is written into `eventStorage.json` file.
<puml src="diagrams/EventStorageClassDiagram.puml" alt="EventStorageClassDiagram" />

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

### Design Considerations
**Aspect: How the individual fields of an Event are stored:**

* **Alternative 1 (current choice):** Create new classes to represent the fields.
    * Pros:
        * Improves the overall structure and readability of the code.
    * Cons:
        * Results in more code. 
        * More memory usage as objects have to be instantiated for each field.

* **Alternative 2:** Store each field as the relevant substring inputted by the user.

    * Pros:
        * Less code required.
        * Less memory usage as objects do not have to be instantiated for each field.
    * Cons:
        * No encapsulation for fields as all fields are treated simply as a string.
        * The Event class will be cluttered with methods as methods specific to each field are compiled into a single class.

**Aspect: How created Events are saved:**

* **Alternative 1 (current choice):** Save the entire updated `EventStorage`.
    * Pros:
      * Easier to implement
    * Cons:
      * More time is taken to execute the command as the entire `EventStorage` must be saved, compared to just appending the new `Event`.

* **Alternative 2:** Append the `Event` at the end of the JSON file.
    * Pros:
      * The command can be executed quickly as the new `Event` only has to be appended, as compared to saving the entire updated `EventStorage`.
    * Cons:
      * A new method must be created to save data after executing other commands. E.g. The save() method after an edit event command would be different from a create event command as the `Event` would have to be located first in the JSON file and then updated.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th volunteer in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new volunteer. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the volunteer was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how the undo operation works:

<puml src="diagrams/UndoSequenceDiagram.puml" alt="UndoSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the volunteer being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Reading an individual event feature

#### Proposed Implementation

When the app is initialized, the MainApp will first initialize all key components of the app. Specifically in this case:

First, eventToShowList in ModelManager will be initialized, with the list of all events currently in the EventStorage.

Then, when MainWindow is being initialized, the EventShowWindow will also be initialized.

EventShowWindow consists of ListView displaying the eventToShowList in Model, and its contents are obtained by calling Logic.getEventToShowList(), which will call Model.getEventToShowList().

In summary, when the app is initialized, EventShowWindow will be initialized with its contents being the list of all events obtained from the EventStorage. However, user will not see it as without receiving its command, EventShowWindow will not be shown.

### \[In progress\] Delete the event from a list of event

### Implementation

To facilitate the event delete command, the class EventDeleteCommand is created. The class extends from the interface
Command. When the command is then parsed and executed.

Given below is an example usage scenario and how the mechanism of the event delete behaves at each step.

Step 1. When the user input the event delete command, it will be parsed by IVolunteerParser. Then the keyword of the command is noticed and EventDeleteCommandParser is called. 

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="DeleteSequenceDiagram" />

Step 2. When the command is parsed, the index of the event in the event list is then determined by parsing from String to an integer.

Step 3. The command is then executed and with the index and the event is deleted from the filtered list.

Step 4. Then the event storage will also be updated accordingly by the filtered list.

### \[In progress\] Tracking amount of roles and materials

#### Implementation

The mechanism to track the amount of roles and materials within the `Event` class is handled by the `Quantity` interface's 
`currentQuantity` and `requiredQuantity` fields. Both fields are positive integers. `Role` and `Material` classes thereby
implement this `Quantity` interface. In addition, for each class, operations to track, access and update the amount of
each role/material have been added into the `Quantity` interface as follows:

- `Quantity#isValidQuantity(int test)` — Checks if `test` is a valid quantity.
- `Quantity#addToQuantity(int addedQuantity)` — Adds `addedQuantity` to the current quantity.
- `Quantity#hasEnough()` — Checks if the current quantity ≥ the required quantity.
- `Quantity#toUiString()` — Returns the string representation to be shown to users in the UI.

To reflect the `currentQuantity` and `requiredQuantity` fields as required in the `Quantity` interface in `EventStorage`,
every `Role` and `Material` instance will be expressed as a JSON object with the following format:

**Material:**

```json
{
  "material" : "potatoes",
  "currentQuantity" : "20",
  "requiredQuantity" : "75"
}
```

**Role:**

```json
{
  "role" : "chef",
  "currentQuantity" : "1",
  "requiredQuantity" : "4"
}
```

This class diagram shows the interface relationship between `Role`, `Material` and `Quantity`:

<puml src="diagrams/QuantityClassDiagram.puml" width="400" />

#### Process

This is the process in which a user might track the amount of roles and materials as needed:

Step 1. The user runs the command `ecreate n/cook for people r/5 chef m/50 potato ...`. Here, a new `Event` object will be created with
a `Set<Role>` that contains a `Role` object that corresponds to `5 chef` and `Set<Material>` that corresponds to `50 potato`.
In the constructor to `Role` and `Material` respectively, the `requiredAmount` for the `:Role` object is **5** and the 
`requiredAmount` for the `:Material` object is **50**, while the `currentAmount` are both set to **0**.

**Tracking roles**

Step 2. The user creates a new volunteer using the command `vcreate n/John s/chef...`. This creates a new `Volunteer` object
with name `John` that contains a skill with name `chef`.

Step 3. Assuming both the `Event` and `Volunteer` objects are at the top of their respective lists, the user runs the
command `eaddv eid/1 vid/1` to add the volunteer named `John` to the event named `cook for people`. Given that the `cook for people`
event has a role `chef` and the volunteer `John` has a skill `chef`, iVolunteer will notice and **increment** the current quantity
within the `Role` object using `Quantity#addToQuantity()`.

Step 4. As a result, the `Role` object will be updated as follows (in the JSON-serialised format):
```json
{
  "role" : "chef",
  "currentQuantity" : "1",
  "requiredQuantity" : "5"
}
```

**Tracking materials**

Step 5. Assuming the `Event` object is at the top of the event list, the user runs command `eaddm eid/1 m/20 potato`. This
results in a `EventAddMaterialCommand` created and then executed, which triggers the change in current quantity within the
`Material` object using `Quantity#addToQuantity()`.

Step 6. As a result, the `Material` object will be updated as follows (in the JSON-serialised format):
```json
{
  "material" : "potato",
  "currentQuantity" : "20",
  "requiredQuantity" : "50"
}
```

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

### \[Proposed\] vfind feature

#### Proposed Implementation

The proposed vfind mechanism is facilitated by `VolunteerFindCommand`, `VolunteerFindCommandParser` and `SkillNameContainsKeywordsPredicate`. `VolunteerFindCommandParser` extends the interface `Parser`, and it implements the following operation:

* `VolunteerFindCommandParser#parse()` — Processes the user input's arguments.

Meanwhile, `VolunteerFindCommand` extends the abstract class `Command`, and implements the following operation:

* `VolunteerFindCommand#execute()` — Displays the filtered volunteer list.

Lastly, `SkillNameContainsKeywordsPredicate` implements the interface `Predicate`, and implements the following operation:

* `SkillNameContainsKeywordsPredicate#test` — Checks if any `skill` or `name` matches the user input.

Given below is an example usage scenario and how the vfind command behaves at each step.

Step 1. The user launches the application. The user executes `vfind n/Alex` command to find any volunteers named 'Alex' in the volunteer list. The `vfind` command calls `LogicManager#execute`, which attempts to execute the command. 

Step 2. This creates a `VolunteerFindCommandParser` object, which processes the user input's arguments, namely 'Alex'. This creates a `VolunteerFindCommand` object, with its predicate encapsulating a list of `names` and a list of `skills`.

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_



## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of volunteers and volunteering events
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage volunteers faster than a typical mouse/GUI driven app, while making volunteer management easier and more standardised than spreadsheets


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                | I want to …​                                                                          | So that I can…​                                                 |
|----------|------------------------|---------------------------------------------------------------------------------------|-----------------------------------------------------------------|
| `* * *`  | volunteer coordinator  | create new events by specifying the name of the event                                 | refer to it in the future                                       |
| `* * *`  | volunteer coordinator  | create new events by specifying the date and time of the event                        | check the date and time of an event in the future               |
| `* * *`  | volunteer coordinator  | change the name of the event                                                          | update an event with the correct name                           |
| `* * *`  | volunteer coordinator  | change the date and time of an event                                                  | update an event with the correct date and time                  |
| `* * *`  | volunteer coordinator  | check the list of events through the app                                              | view all upcoming events                                        |
| `* * *`  | volunteer coordinator  | check the date and time of an event                                                   | know when and what time an event will occur                     |
| `* * *`  | volunteer coordinator  | delete events                                                                         | remove them when they are no longer needed                      |
| `* *`    | volunteer coordinator  | create new events by specifying the roles needed                                      | check what roles are needed for an event in the future          |
| `* *`    | volunteer coordinator  | create new events by setting the location and approximate area of the event           | check where an event will be held  in the future                |
| `* *`    | volunteer coordinator  | create new events by specifying the logistics and other materials needed for an event | check what other logistics I require for an event in the future |
| `* *`    | volunteer coordinator  | create new events by providing a brief description of the event                       | refer to it in the future                                       |
| `* *`    | volunteer coordinator  | create new events by setting and allocating the budget for the event                  | check the estimated cost required for an event in the future    |
| `* *`    | volunteer coordinator  | set the end time for an event                                                         | so that I can know how long the event lasts                     |
| `* *`    | volunteer coordinator  | change the roles needed for an event                                                  | update an event with the correct roles                          |
| `* *`    | volunteer coordinator  | be able to change the location and approximate area of the event                      | update an event with the correct location                       |
| `* *`    | volunteer coordinator  | change the logistics and other materials needed for an event                          | update the event with the correct logistics required            |
| `* *`    | volunteer coordinator  | change the description of the event                                                   | update the event with the correct description                   |
| `* *`    | volunteer coordinator  | change the budget for the volunteering event                                          | update the event with the correct budget                        |
| `* *`    | volunteer coordinator  | be able to check the roles needed for an event                                        | know what roles are required for an event                       |
| `* *`    | volunteer coordinator  | be able to check the location and approximate area of the event                       | know where an event will be held                                |
| `* *`    | volunteer coordinator  | be able to check the logistics and other materials needed for an event                | know what other materials are required                          |
| `* *`    | volunteer coordinator  | check the description of an event                                                     | have a better idea of the details of the event                  |
| `* *`    | volunteer coordinator  | be able to check the budget for an event                                              | know the budget assigned for the event                          |
| `* *`    | volunteer coordinator  | check how many volunteers are in a certain event                                      | have an estimate on the current manpower for an event           |
| `* *`    | volunteer coordinator  | check which volunteers are assigned to a certain event                                | so I can easily tell who is participating in the event          |
| `* *`    | volunteer coordinator  | check how many events a volunteer has participated in                                 | keep track of their contributions                               |
| `* *`    | volunteer coordinator  | check the information and contacts of a volunteer                                     | easily obtain information from any volunteer                    |
| `* *`    | volunteer coordinator  | be able to check a volunteer’s volunteer history                                      | keep track of the number of hours they have volunteered         |
| `* *`    | volunteer coordinator  | check the items a volunteer needs to bring(if applicable) for an event                | know who is responsible for bringing specific items             |
| `* *`    | volunteer coordinator  | know, given a volunteer, the specific tasks they are assigned to within the event     | know who is responsible for a specific task                     |
| `* *`    | volunteer coordinator  | be able to allocate volunteers into events quickly                                    | save time on the volunteer allocation process                   |
| `* *`    | volunteer coordinator  | be able to remove volunteers from events quickly                                      | save time on the volunteer removal process                      |
| `* *`    | volunteer coordinator  | be able to filter through the event list                                              | quickly find the event I am interested in                       |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `iVolunteer` and the **Actor** is the `Volunteer Coordinator`, unless specified otherwise)

**Use case UCE01: Create an event**

**MSS**

1.  Volunteer Coordinator creates an event.
2.  iVolunteer shows the event created.

    Use case ends.

**Extensions**
* 1a. Invalid Command Word.
    * 1a1. iVolunteer prompts Volunteer Coordinator to provide a valid command.

  Use case resumes from step 1.
* 1b. Missing arguments for mandatory fields.
    * 1b1. System prompts Volunteer Coordinator to provide arguments for all mandatory fields.

  Use case resumes from step 1.
* 1c. Parameters are not separated by a single space.
    * 1c1. System prompts Volunteer Coordinator to separate parameters with a single space.

  Use case resumes from step 1.
* 1d. Invalid Date and Time.
    * 1d1. System prompts Volunteer Coordinator to use the correct date and time format.

  Use case resumes from step 1.
* 1e. Start date/time is after end date/time.
    * 1e1. System prompts Volunteer Coordinator to ensure that start date/time is before end date/time.

  Use case resumes from step 1.
* 1f. Invalid Budget Argument
    * 1f1. System prompts Volunteer Coordinator to use the correct budget format.

  Use case resumes from step 1.

**Use case UCE02: List all volunteering events**

**MSS**

1.  User requests to list all volunteering events.
2.  iVolunteer list out a summarized version of all volunteering events.

    Use case ends.

**Extensions**

* 1a. The input command is incorrect.

    * 1a1. System prompts the user to provide a valid command.
    
      Use case resumes from step 1.

* 1b. There are no events to list.

  Use case ends.

**Use case UCE03: Read an individual event**

**MSS**

1.  User <u>lists all volunteering events (UCE02)</u>.
2.  User requests to read an individual event.
3.  iVolunteer shows a detailed description about that particular event.

    Use case ends.

**Extensions**

* 2a. User did not input a correct command.

    * 2a1. System prompts user to input a valid command.

      Use case resumes at step 2.

* 2b. User did not input an index after the command.

    * 2b1. System prompts user to input an index.

      Use case resumes at step 2.

* 2c. User did not input a valid index after the command.

    * 2c1. System prompts user to input a valid index.

      Use case resumes at step 2.

* 2d. User did not leave a single space between the command and the index.

    * 2d1. System prompts user to separate parameters with a single space.


**Use case UCE04: Delete an event**

**MSS**

1.  User <u>lists all volunteering events (UCE02)</u>.
2.  User requests to delete a specific event in the list.
3.  iVolunteer deletes the event.

    Use case ends.

**Extensions**

* 2a. Invalid event id.
    * 2a1. iVolunteer requests for the correct command with valid event id.
    * 2a2. User enters correct command.
    Steps 2a1-2a2 are repeated until the data entered is correct.
    Use case resumes at step 3.

**Use case UCV01: Create a volunteer**

**MSS**

1.  User requests to create a volunteer.
2.  iVolunteer shows the volunteer created and appends the volunteer to the end of the volunteer list.

    Use case ends.

**Extensions**

* 1a. Invalid Command Word.
    * 1a1. iVolunteer prompts Volunteer Coordinator to provide a valid command.

    Use case resumes from step 1.
* 1b. Missing arguments for mandatory fields.
    * 1b1. System prompts Volunteer Coordinator to provide arguments for all mandatory fields.

  Use case resumes from step 1.
* 1c. Parameters are not separated by a single space.
    * 1c1. System prompts Volunteer Coordinator to separate parameters with a single space.

  Use case resumes from step 1.
* 1d. Invalid email.
    * 1d1. System prompts Volunteer Coordinator to use the correct email format.

    Use case resumes from step 1.
* 1e. Invalid phone number.
    * 1e1. System prompts Volunteer Coordinator to use a valid 8-digit phone number.

    Use case resumes from step 1.
* 1f. Name exceeds 30 characters, or name is empty.
    * 1f1. System prompts Volunteer Coordinator to use a volunteer name between 1-30 characters.

    Use case resumes from step 1.

**Use case UCV02: List all volunteers**

**MSS**

1.  User requests to list all volunteers.
2.  iVolunteer shows a list of all volunteers.

    Use case ends.

**Use case UCV03: Delete a volunteer**

**MSS**

1.  User <u>lists all volunteers (UCV02)</u>.
2.  User requests to delete a specific volunteer in the volunteer list.
3.  iVolunteer removes the volunteer from all events he/she is in.
4.  iVolunteer deletes the volunteer.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. The given index is invalid.

    * 2a1. iVolunteer shows an error message that there is no such volunteer in the given index.

      Use case resumes from step 2.

**Use case UCV04: Clear all volunteers in volunteer list**

**MSS**

1. User clears all volunteers in volunteer list.
2. The volunteer list becomes empty.

    Use case ends.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 volunteers without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Volunteer coordinator**: A person in charge of volunteer events and have many individual volunteers under them.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a volunteer

1. Deleting a volunteer while all volunteers are being shown

   1. Prerequisites: List all volunteers using the `list` command. Multiple volunteers in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No volunteer is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
