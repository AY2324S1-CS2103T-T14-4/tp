---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# iVolunteer User Guide

iVolunteer is your dedicated application for volunteer coordination, designed with **volunteer coordinators** in mind. If you're looking to enhance your volunteer coordination tasks, iVolunteer is the tool that empowers you to **manage volunteers and volunteering events efficiently**. iVolunteer streamlines the process for **swift and effective coordination**, by combining the efficiency of a **Command Line Interface (CLI)** with the advantages of a **Graphical User Interface (GUI)**.

**Command Line Interface (CLI):** a text-based interface that is used to operate software and operating systems while allowing the user to respond to visual prompts by typing single commands into the interface and receiving a reply in the same way.

**Graphical User Interface (GUI):** a form of user interface that allows users to interact with electronic devices through graphical icons and audio indicators such as primary notation.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) or above installed in your Computer.

1. Download the latest `iVolunteer.jar` from [here](https://github.com/AY2324S1-CS2103T-T14-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for iVolunteer.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar iVolunteer.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>

   Some example commands you can try:

   * `elist` 
     * Lists all events

   * `ecreate n/food donation r/10 chef r/20 packer sd/23/9/2023 1500 l/hougang dsc/help food distribution m/50 potatoes b/50.00`
     * Creates an event with name `food donation`, roles needed `10 chef` and `20 packer`, event date `23rd September 2023, 3pm`, location `hougang`, description `help food distribution`, materials needed `50 potatoes` and budget `$50`

   * `edelete 3`
     * Deletes the 3rd event in the current event list

   * `vlist`
     * Lists all volunteers.

   * `vcreate n/John Doe p/98765432 e/johnd@example.com`
     * Adds a volunteer named `John Doe` to the list of volunteers.

   * `vdelete 3`
     * Deletes the 3rd volunteer in the current volunteer list.

   * `vclear`
     * Deletes all volunteers.

   * `exit`
     * Exits the app.
     

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `vfind n/NAME`, `NAME` is a parameter which can be used as `vfind n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [s/SKILL]` can be used as `n/John Doe s/friendly` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[s/SKILL]…​` can be used as ` ` (i.e. 0 times), `s/friendly`, `s/friendly s/caring` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `vlist`, `elist`, `exit` and `vclear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help: `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Creating a new volunteer's profile: `vcreate`

Volunteer coordinators can create new volunteer profiles, and add the volunteer into the volunteer list.

Format: `vcreate n/NAME p/PHONE_NUMBER e/EMAIL [s/SKILLS]…`

Parameters:
* n/ - Volunteer name
* p/ - Phone number of the volunteer
* e/ - Email address of the volunteer
* s/ - Skills a volunteer may have

<box type="tip" seamless>

**Tip:** A volunteer can have any number of skills (including 0).
</box>

Restrictions:
* Volunteers cannot have duplicate names (Volunteer names are unique to each volunteer).
* The maximum number of characters of a volunteer name is 30.
* The email must be in a valid format.
* The phone number must be a valid 8-digit Singapore phone number.

Examples:
* `vcreate n/John p/91234567 e/john123@gmail.com` 
  * creates a volunteer named `John` with a phone number of `91234567` and an email address of `john123@gmail.com`, with no specific skills. The volunteer profile will be appended to the bottom of the volunteer list.
* `vcreate n/Mary p/92345678 e/mary123@gmail.com s/Cooking s/Carrying heavy goods` 
  * creates a volunteer named `Mary` with a phone number of `92345678` and an email address of `mary123@gmail.com`, with two skills: `Cooking` and `Carrying heavy goods`. The volunteer profile will be appended to the bottom of the volunteer list.

### Listing all volunteers: `vlist`

Shows a list of all volunteers in the volunteer list.

Format: `vlist`

### Locating volunteers by name and skill: `vfind`

Finds volunteers whose names contain any of the given keywords.

Format: `vfind [n/NAME]…​ [s/SKILL]…​`

Parameters:
* n/ - Volunteer name
* s/ - Skills of volunteer

<box type="tip" seamless>

**Tip:** Both the volunteer name and corresponding skills can be searched.
</box>

Restrictions:
* At least one of the optional fields must be provided.
* The search is case-insensitive. e.g `n/hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `s/chef n/Hans` and `n/Hans s/chef` are valid inputs.
* Allows partial matching of keywords e.g. `n/Han` will match `Hans`.
* Volunteers matching **at least one** NAME keyword will be returned (i.e. `OR` search).
  e.g. `n/Hans n/Bo` will return `Hans Gruber`, `Bo Yang`.
* Volunteers matching **both** SKILL keywords will be returned (i.e. `AND` search).
  e.g. `s/chef s/boxer` will return volunteers that have skills `chef` and `boxer`.

Examples:
* `vfind n/John` 
  * returns `john` and `John Doe`
* `vfind n/alex n/david` 
  * returns `Alex Yeoh`, `David Li`<br>


  ![result for 'find alex david'](images/findAlexDavidResult.png)


* `vfind s/chef` returns volunteers who are chefs

### Editing a volunteer profile: `vedit`

Edits an existing volunteer in the volunteer list.

Format: `vedit VOLUNTEER_ID [n/NAME] [p/PHONE] [e/EMAIL] [s/SKILL]…​`

Parameters:
* n/ - Volunteer name
* p/ - Volunteer phone number
* e/ - Volunteer email
* s/ - Volunteer skill

Restrictions:
* Edits the volunteer at the specified `VOLUNTEER_ID`. 
* `VOLUNTEER_ID` refers to the index number shown in the displayed volunteer list. 
* `VOLUNTEER_ID` **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing skills, the e**x**isting skills of the volunteer will be removed i.e adding of skills is not cumulative.

<box type="tip" seamless>

**Tip:** You can remove all the volunteer’s skills by typing `s/` without
specifying any skills after it.
</box>

Examples:
* `vedit 1 p/91234567 e/johndoe@example.com`
  * Edits the phone number and email address of the 1st volunteer to be `91234567` and `johndoe@example.com` respectively.
* `vedit 2 n/Betsy Crower s/`
  * Edits the name of the 2nd volunteer to be `Betsy Crower` and clears all existing skills.


### Deleting a volunteer profile: `vdelete`

Volunteer coordinators can delete volunteers and remove them from the volunteer list if they no longer wish to volunteer anymore.

Format: `vdelete VOLUNTEER_ID`

Restrictions:

* Deletes the volunteer at the specified `VOLUNTEER_ID`.
* The `VOLUNTEER_ID` refers to the index number shown in the displayed volunteer list.
* `VOLUNTEER_ID` **must be a positive integer** 1, 2, 3, …
* `VOLUNTEER_ID` must represent a valid volunteer number in the displayed volunteer list. If there are 30 volunteers in the volunteer list currently displayed to the user, the acceptable values will be from 1-30.

Examples:
* `vlist`, followed by `vdelete 6` will remove the 6th volunteer displayed in the volunteer list.
* `vfind n/Betsy` followed by `vdelete 1` deletes the 1st volunteer in the results of the `vfind` command.

### Clearing all volunteer entries: `vclear`

Clears all volunteers from the volunteer list.

Format: `vclear`

### Creating an event: `ecreate`

Volunteer coordinators can create new events.

Format: `ecreate n/EVENT_NAME r/ROLES_NEEDED… sd/START_DATETIME [ed/END_DATETIME] l/LOCATION dsc/DESCRIPTION [m/MATERIALS_AND_LOGISTICS_NEEDED]... [b/BUDGET]`

Parameters:
* n/ - Event name
* r/ - Roles needed for the event and its quantity
* sd/ - Start date and time of the event
* ed/ - End date and time of the event
* l/ - Location of the event
* dsc/ - Description of the event
* m/ - Materials needed for the event and its quantity
* b/ - Budget for the event

Restrictions:
* All parameters must be separated by a single space.
* All arguments cannot be blank.
* Events cannot have duplicate names (Event names are unique to each event).
* The date and time formats must be exactly `DD/MM/YYYY TTTT`.
* If the end date and time is specified, it must be the _same time_ or _after_ the start date and time of the event.
* The location and description arguments cannot contain the `[` and `]` symbols.
* The material argument must be an integer, followed by a space, and then the name of material required.
* The role argument must be an integer, followed by a space, and then the name of role required.
* The budget argument must be a number in 2 decimal places.

<box type="tip" seamless>

**Tip:** If the end date and time is not specified, iVolunteer will automatically set the end date and time to **exactly 3 hours** after the start date and time.
</box>

Examples:
* `ecreate n/clean beach r/10 cleaner sd/30/11/2023 1200 ed/30/11/2023 1800 l/east coast park dsc/help clean east coast park m/10 pairs of gloves m/10 trash bags b/50.00` 
  * Creates an event with name `clean beach`, roles needed `10 cleaner`, event date from `30th November 2023, 12pm` to `30th November 2023, 6pm`, location `east coast park`, description `help clean east coast park`, materials needed `10 pairs of gloves` and `10 trash bags` and budget `$50.00`

### Listing all events: `elist`
Volunteer coordinators can see all the events they are organising. For each event, only the most important information will be shown: name, start date and time, end date and time, location, roles needed and materials needed.

Format: `elist`

<box type="tip" seamless>

**Tip:** Events are sorted automatically in chronological order!
</box>

### Reading an individual event: `eshow`
Volunteer coordinators can read up more about an individual event, to familiarize themselves with its requirements while planning for it.

Format: `eshow EVENT_ID`

Restrictions:
* First part must be `eshow`
* Second part must be an integer that represents a valid `EVENT_ID`: If the list of events displayed is 10 events long, the acceptable values will be from 1-10.
* First and second parts must be separated by a single space.

Examples:
* `eshow 7` 
  * result in a pop-up window appearing, listing all details of the event at id `7`. This includes its name, start date and time, end date and time, location, roles needed, logistics needed (if any), budget (if any), and a description.

### Deleting an event: `edelete`

Deletes the event from the event list.

Format: `edelete EVENT_ID`

Restrictions:
* Deletes the event at the specified `id`.
* `id` refers to the index number shown in the displayed event list.
* `id` **must be a positive integer** 1, 2, 3, …​

Examples:
* `elist` followed by `edelete 2` deletes the 2nd event in the event list.
* `efind Beach cleaning` followed by `edelete 1` deletes the 1st event in the results of the `find` command (tentative feature)

### Clearing all event entries: `eclear`

Clears all entries from the event list.

Format: `eclear`

### Adding and tracking quantity of materials into an event: `eaddm`

Volunteer coordinators can track the current quantity of materials in an event and add materials into an event, in order to
track the progress of the logistics gathered for the event.

Format: `eaddm eid/EVENT_ID m/MATERIAL`

Parameters:
* eid/ - Event id
* m/ - The current quantity of a material to add to the event

Restrictions:
* The event id must be positive and must correspond to exactly one of the ids of the events currently listed.
* The event id **must be a positive integer** 1, 2, 3, …
* The material name specified must be present within the event.

Examples:
* `ecreate n/clean beach m/10 trash bags ...` (refer to [ecreate](#creating-an-event-ecreate) above for full command) creates an 
  event `clean beach` that requires `20 trash bags`. By performing `efind clean beach`, then `eaddm eid/1 10 trash bags`,
  the event `clean beach` will now contain `10 / 20 trash bags`.

### Adding a volunteer into an event: `eaddv`

Adds a volunteer to an event.

Format: `eaddv vid/VOLUNTEER_ID eid/EVENT_ID`

Parameters:
* vid/ - Volunteer id
* eid/ - Event id

Restrictions:
* The event id must be positive and must correspond to exactly one of the ids of the events currently listed.
* The volunteer id must be positive and must correspond to exactly one of the ids of the volunteers currently listed.
* The volunteer must not already be added to the event.

Examples:
* `eaddv vid/1 eid/1` 
  * adds the volunteer with id 1 to the event with id 1.

### Listing all volunteers in an event: `elistv`

Shows a list of all volunteers in an event.

Format: `elistv EVENT_ID`

Restrictions:
* The event id must be positive and must correspond to exactly one of the ids of the events currently listed.

Examples:
* `elistv 1` 
  * lists the volunteers added to the event with id 1.

### Removing a volunteer from an event: `eremovev`

Removes a volunteer from an event.

Format: `eremovev vid/VOLUNTEER_ID eid/EVENT_ID`

Parameters:
* vid/ - Volunteer id
* eid/ - Event id

Restrictions:
* The event id must be positive and must correspond to exactly one of the ids of the events currently listed.
* The volunteer id must be positive and must correspond to exactly one of the ids of the volunteers currently listed.
* The volunteer should already be added to the event.

Examples:
* `eremovev vid/1 eid/1` 
  * removes the volunteer with id 1 from the event with id 1.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

iVolunteer data are saved in the hard disk automatically after any command executed. There is no need to save manually.

### Editing the data file

iVolunteer data are saved automatically as two JSON files, `[JAR file location]/data/volunteerStorage.json` (volunteer storage) and `[JAR file location]/data/eventStorage.json` (event storage and _event-volunteer interactions_). Advanced users are welcome to update data directly by editing both data files.

_**Note:** Event-volunteer interactions through `eaddv`, `elistv`, `eremovev` coming in v1.3!_

<box type="warning" seamless>

**Caution:**
If your changes to any data file makes its format invalid, iVolunteer will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: As a Windows 11 user, how do I open a command terminal?<br>
**A**: On your Windows 11 computer, do the following:
1. Select the Start Menu (the Windows icon) in the taskbar, or press the Windows key.
2. Type `cmd` in the search bar.
3. Select Command Prompt from the list.

**Q**: As a Mac user, how do I open a command terminal?<br>
**A**: On your Mac, do one of the following:
1. Click the Launchpad icon in the Dock, type Terminal in the search field, then click Terminal.
2. In the Finder, open the `/Applications/Utilities` folder, then double-click Terminal.

--------------------------------------------------------------------------------------------------------------------

## Known issues

coming soon

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                                        | Format, Examples                                                                                                                                                                                                                                                                                                                        |
|-----------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Display help window**                       | `help`                                                                                                                                                                                                                                                                                                                                  |
| **Create a new event**                        | `ecreate n/EVENT_NAME r/ROLES_NEEDED… sd/START_DATETIME [ed/END_DATETIME] l/LOCATION dsc/DESCRIPTION [m/MATERIALS_AND_LOGISTICS_NEEDED]... [b/BUDGET]` <br> e.g., `ecreate n/clean beach r/cleaner sd/30/11/2023 1200 ed/30/11/2023 1800 l/east coast park dsc/help clean east coast park m/10 pairs of gloves m/10 trash bags b/50.00` |
| **List all events**                           | `elist`                                                                                                                                                                                                                                                                                                                                 |
| **Read an individual event**                  | `eshow EVENT_ID` <br> e.g., `eshow 8`                                                                                                                                                                                                                                                                                                   |
| **Delete an event**                           | `edelete EVENT_ID` <br> e.g., `edelete 3`                                                                                                                                                                                                                                                                                               |
| **Clear all events**                          | `eclear`                                                                                                                                                                                                                                                                                                                                |
| **Create a new volunteer profile**            | `vcreate vn/VOLUNTEER_NAME hp/PHONE_NUMBER e/EMAIL [s/SKILLS]...`<br> e.g.,`vcreate vn/John Lim hp/81234567 e/john123@gmail.com s/Cooking`                                                                                                                                                                                              |
| **List all volunteer profiles**               | `vlist`                                                                                                                                                                                                                                                                                                                                 |
| **Edit a volunteer profile**                  | `vedit VOLUNTEER_ID [n/NAME] [p/PHONE] [e/EMAIL] [s/SKILL]…​` <br> e.g., `vedit 1 p/91234567 e/johndoe@example.com`                                                                                                                                                                                                                     |
| **Delete a volunteer profile**                | `vdelete VOLUNTEER_ID` <br> e.g., `vdelete 4`                                                                                                                                                                                                                                                                                           |
| **Clear all volunteer profiles**              | `vclear`                                                                                                                                                                                                                                                                                                                                |
| **Add a volunteer to an event**               | `eaddv vid/VOLUNTEER_ID eid/EVENT_ID`<br> e.g., `eaddv vid/1 eid/3`                                                                                                                                                                                                                                                                     |
| **Check for volunteers assigned to an event** | `elistv EVENT_ID` <br> e.g. `elistv 8`                                                                                                                                                                                                                                                                                                  |
| **Remove a volunteer from an event**          | `eremovev vid/VOLUNTEER_ID eid/EVENT_ID`<br> e.g., `eremovev vid/3 eid/4`                                                                                                                                                                                                                                                               |
| **Exits iVolunteer**                          | `exit`                                                                                                                                                                                                                                                                                                                                  |
