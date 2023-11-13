---
  layout: default.md
  title: "Brendan Koh's Project Portfolio Page"
---

### Project: iVolunteer

iVolunteer is an application for volunteer coordinators to manage volunteers, events, and their interactions. The user interacts with it using a CLI, with a GUI created with JavaFX. It is written in Java with about 14 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added a display pane for `Event` within the JavaFX GUI ([#86](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/86)).
  * Description: The `Event` display pane is displayed side-by-side with the `Volunteer` display pane.
  * Justification: This feature improves the product significantly by allowing volunteer coordinators to track both volunteers and events at the same time, allowing for interactions within events and volunteers to be performed.
  * Highlights: This feature allowed for the creation of features that manage event-volunteer interactions, like the [`eaddv` command](../UserGuide.md#adding-a-volunteer-into-an-event-eaddv) and [`eremovev` command](../UserGuide.md#removing-a-volunteer-from-an-event-eremovev). Although the implementation of the `Event` pane itself was easy since I was able to reference the `Person` display pane in the project we based iVolunteer on, placing the `Event` pane side-by-side with the `Volunteer` pane was a bit more difficult due to the addition of new containers to accommodate the new `Event` display pane.

* **New Feature**: Implemented an end date field in the `Event` model. I realised that most events require a *start date* and *end date* while our initial plans had only 1 date for event. After approval from the group, I worked on the implementation of an *end date* field for events ([#98](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/98)), proving the merits of our project using an **agile design approach**.

* **New Feature**: Added the ability to track the quantities of *roles* and *materials* in an event, which involves the addition of a new command, the [`eaddm` command](../UserGuide.md#adding-and-tracking-quantity-of-materials-into-an-event-eaddm).
  * What it does: allows the user to track the amount of roles or materials present in the event.
  * Justification: This feature allows volunteer coordinators to track the amount of materials and roles that have already been added to the event.
  * Highlights: Due to this feature augmenting the `Event` model as well as event-volunteer interactions, implementation of this feature was relatively complex and it took a few days for me to finish the material tracking feature ([#109](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/109)). Since the role tracking feature was similar but did not require implementing a new feature, it did not take me as long to implement this feature ([#128](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/128)).

* **New Feature**: Added the ability for users to set the maximum number of volunteers within an event ([#132](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/132)).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=brein62&breakdown=true)

* **Project management**:
  * Managed releases `v1.2.1` - `v1.4` (3 releases) on GitHub

<!-- force a page break -->
<div style="page-break-before:always"></div> 

* **Enhancements to existing features**:
  * Modified label formatting for volunteer skills, event roles, event materials in GUI: [#108](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/108)

* **Documentation**:
  * User Guide:
    * Added documentation for the volunteer create, read and delete features (`vcreate`, `vlist`, `vdelete`): [#69](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/69).
    * Added demonstration on how roles and materials are tracked in iVolunteer in UG, and for `eaddm` command: [#109](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/109), [#204](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/204).
  * Developer Guide:
    * Added use cases for volunteer create, list, delete features: [#80](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/80).
    * Added implementation details in DG for material and role tracking feature, inclusive of UML class diagram: [#114](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/114), [#214](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/214)
    * Added implementation details in DG for maximum volunteer capacity feature: [#214](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/214)
    * Added Effort section in DG: [#214](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/214)

* **Team-based tasks:**
  * I set up our project's [main repository](https://github.com/ay2324s1-cs2103t-t14-4/tp), forked from the AddressBook Level-3 project found [here](https://github.com/nus-cs2103-ay2324s1/tp). This includes:
    - Setting up our [team organisation](https://github.com/AY2324S1-CS2103T-T14-4) on GitHub
    - Forking from the AddressBook Level-3 repository
    - Setting up of Java CI within GitHub
    - Setting up the Codecov code coverage tool for our team's fork of the project
    - Update documentation settings to show iVolunteer instead of AB3 ([#49](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/49))
  * I set up the project's information in Gradle. This includes:
    - Changed the project JAR file to `ivolunteer.jar` in the project's `build.gradle` ([#110](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/110))
    - Enabled assertions in the project's `build.gradle` ([#97](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/97))
  * Did the initial categorisation and sorting of bugs received during system testing in PE-D ([issues #143 to #194](https://github.com/AY2324S1-CS2103T-T14-4/tp/issues)).
    - Categorised into *feature flaw*, *documentation bug*, and *functionality bug* roughly based on testers' feedback, as well as added severity (*low*, *medium*, *high*)
    - Removed and detected duplicate bug reports (15 of 52 duplicates)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [#83](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/83), [#88](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/88), [#121](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/121), [#122](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/122) (providing feedback based on teammate's review)
  * Reported bugs and suggestion for other teams in the cohort (examples: [1](https://github.com/AY2324S1-CS2103T-W13-1/tp/issues/139), [2](https://github.com/AY2324S1-CS2103T-W13-1/tp/issues/129))
