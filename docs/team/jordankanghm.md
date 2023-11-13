---
layout: default.md
title: "Jordan Kang's Project Portfolio Page"
---

### Project: iVolunteer

iVolunteer is an application for volunteer coordinators to manage volunteers, events, and their interactions. The user interacts with it using a CLI, with a GUI created with JavaFX. It is written in Java with about 14 kLoC.

Given below are my contributions to the project.

* **New Feature**: Create Event Feature
  * Provided the **foundational code** for creating volunteering events in our application. This is important as the main goal of our application is to allow volunteer coordinators to be able to store their events, along with the critical details associated with them. The following shows the aspects involved when implementing this feature.
    * **Parsing of parameter arguments**, such as ensuring that the budget for an event is non-negative and in 2 decimal places to replicate real-life scenarios.
    * **Creating new classes** to represent the fields of an event, namely `EventName`, `Role`, `DateTime` `Location`, `Material` and `Budget`.
    * Implementing **checking for valid values** for the new classes created.
    * Ensuring that the newly created `Event` can be **added to the Model seamlessly**.
    * Ensuring that Event data can be **written into and retrieved from storage files** successfully.
    * **Writing test cases** to ensure the feature works correctly.

  * As the original code we were provided with dealt with `Person` objects, I had to create and implement the logic for `Event` objects entirely as the main purpose of our app pertained to being able to **create volunteering events**. The experience was extremely **time-consuming** and took me a few days to implement as the existing code for `Person` was quite significant. However, it was also **informative** as it really allowed me to understand on a deeper level how the original code works and allowed me to implement the logic for `Event` more easily.

  * Our group also had a **deadline** to meet, which asserted some pressure on me as I was responsible for the **fundamental aspect of our application**, and I also had to ensure I had a Minimum Viable Product early so that my group members have time to implement their features as well. Although the result was expectedly buggy, I am grateful for the experience as it taught me to **work under pressure** and **think from different perspectives** when implementating new fields so that my group members can have an easier time implementing their features as well.

* **New Feature**: Event Add Volunteer Feature
  * Implemented the functionality of **assigning volunteers to events**. This is important as it helps volunteer coordinators **easily keep track** of which volunteers are participating in which event, easing the volunteer management process. The following shows the aspects involved when implementing this feature.
    * **Creating a new field** for `Event`: `assignedVolunteers` which is a `Set<Name>` which stores the names of volunteers assigned to the event. I also created a `assignedEvents` field for `Volunteer` which works the same way.
    * **Parsing of parameter arguments**, such as ensuring that the `Event` and `Volunteer` indexes provided are valid.
    * **Implementing the logic** for adding a volunteer to an event, such as checking if the volunteer is already assigned to the event and checking if any of the volunteer's assigned events clashes with the current event.
    * **Updating the `Model`** with the updated `Event` and `Volunteer`.
    * **Writing test cases** to ensure the feature works correctly.

  * During the implementation of this feature, there were some **integration errors** and some commands were not working as expected as my implementation caused `Event` and `Volunteer` to become mutable. Thankfully, my group member helped in the debugging process and resolved the issue swiftly.

* **New Feature**: Event List Volunteer Feature
  * Implemented the functionality of **showing the volunteers assigned to an event**. This is important as it **increases the convenience** for volunteer coordinators as they can view the volunteers participating in an event all in a single panel. The following shows the aspects involved when implementing this feature.
    * **Parsing of parameter arguments**, such as ensuring that the event index provided is valid.
    * **Implementing the logic** for listing the assigned volunteers, such as mapping the names in `assignedVolunteers` to the actual volunteer objects.
    * **Integrating the resultant list with JavaFX** to display the volunteers in a single ListView panel.
    * **Writing test cases** to ensure the feature works correctly.

    * When implementing this feature, I deliberated whether I should display the volunteers in a panel next to the event display panel, or a pop-up window. In the end, I went with the former as although a pop-up window would be more **user-friendly**, it would affect the **integration with other commands**. Although the deliberating process was significantly time-consuming, it gave me more insights and aided my **forward-thinking capabilities**. Additionally, it also taught me to be **adaptable** when things don't go as planned and changes must be made on the fly.

* **New Feature**: Event Remove Volunteer Feature
  * Implemented the functionality of **removing volunteers from events**. This is important as it helps volunteer coordinators **easily keep track** of which volunteers are no longer participating in which event, easing the volunteer management process. The following shows the aspects involved when implementing this feature.
    * **Parsing of parameter arguments**, such as ensuring that the `Event` and `Volunteer` indexes provided are valid.
    * **Implementing the logic** for removing a volunteer from an event, such as checking if the volunteer is assigned to the event before removing.
    * **Updating the `Model`** with the updated `Event` and `Volunteer`.
    * **Writing test cases** to ensure the feature works correctly.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=jordankanghm&breakdown=true)

* **Enhancements to existing features**:
  * Initially, the code we were given was to be used as an address book with the main class being `Person`. At the beginning of the project, I refactored all aspects of the code to match `Volunteer`, which was the main target of our application. This process included the following steps.
    * **Changing the command word for each command**, e.g. The command word for adding a new `Person` was changed from `add` to `vcreate`.
    * **Refactoring all file names**, e.g. from `AddCommand` to `VolunteerCreateCommand`.
    * **Updating the `Model`** to accept `Volunteer` instead of `Person`.
    * **Changing the writing and reading of data** from `Person` to `Volunteer`.

* **Project management**:
  * Helped set up the issue tracker and milestone management on GitHub.

* **Documentation**:
    * User Guide:
      * Added the Quick Start portion of the UG (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/47/files)
      * Added documentation for event create feature(`ecreate`). (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/81/files)
      * Added documentation for event add volunteer, event list volunteer and event remove volunteer feature(`eaddv`, `elistv`, `eremovev`). (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/111/files)
    * Developer Guide:
      * Added use cases for event create feature. (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/53/files)
      * Added implementation details for event create feature, inclusive of UML class and sequence diagrams. (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/115/files)
      * Added implementation details for event add volunteer, event list volunteer and event remove volunteer features, inclusive of UML class, object and sequence diagrams. (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/219/files)
      * Added planned enhancements for the event create feature. (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/233/files)
      * Added instructions for manual testing for event create, event add volunteer, event list volunteer and event remove volunteer features. (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/232/files)

* **Community**:
  * Provided feedback on the PRs of team members (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/52, https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/67, https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/69, https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/82, https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/91, https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/229)
  * Responded to feedback given on own PRs (https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/53)
