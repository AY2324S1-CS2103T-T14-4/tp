---
layout: default.md
title: "Ruo Xuan's Project Portfolio Page"
---
### Project: iVolunteer

iVolunteer is an application for volunteer coordinators to manage volunteers, events, and their interactions. The user interacts with it using a CLI, with a GUI created with JavaFX. It is written in Java with about 14 kLoC.

Given below are my contributions to the project:

* **New Feature**: Added the event list command ([#88](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/88)).
  * What it does: List out all events in the event display pane. For each event, only the key information (name, start date and time, end date and time, location, roles needed and materials needed) is displayed to the user so as to keep this list concise.
  * Justification: This feature makes event planning more efficient for our users as they are able to manage all events at once. The information displayed to the user is restricted to only the crucial ones as otherwise, the event list will be very long. Users might have to scroll through a lot more to find the event they want, making our app less user-friendly.
  * Highlights: The implementation of this feature was not difficult as the original code already had a list feature which I could refer to, and the event display was beautifully done up by a team member. However, implementing this feature has taught me to put myself in the shoes of our users, and think about ways to further improve the user experience even if this is a very simple feature of the app.

* **New Feature**: Added the event show command ([#96](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/96)).
  * What it does: Allows user to read more about an event by displaying all of its information in a pop-up window.
  * Justification: Since displayed event list only has the key information, there must be a way for users to see all information for the event. Hence, I have decided to display them in a pop-up window so that users can refer to both the pop-up window and the main window at the same time, which makes the event planning process more convenient and efficient.
  * Highlights: Implementation of the pop-up window UI was manageable as I could gain some inspiration from the `HelpWindow` class given in our original code. On the other hand, obtaining the information of the event to display was a lot more challenging to implement. iVolunteer uses a Facade to separate the component internals and users of the component, so I had to come up with a way for the pop-up window to have access to the event to display. It required an in-depth analysis of design alternatives, and it took me numerous trial and error to finally decide on my implementation method. 
To further improve on user experience, I have added the ability to close this pop-up window using the `ESC` key ([#130](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/130)).

* **New Feature**: Added the ability to sort all events in chronological order ([#120](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/120)).
  * What it does: The event list displayed is automatically sorted in a way such that users will always see the most upcoming event first.
  * Justification: Empathizing with our users, we recognize that when faced with numerous events, prioritizing those with impending deadlines is a common practice. With this in mind, this functionality aims to eliminate the need for users to manually compare dates across all events, offering a convenient way to focus on the next imminent task.
  * Highlights: Implementing this feature allowed me to utilize the topics I have learnt last semester (e.g. the `Comparable<T>` class).

* **New Feature**: Added the volunteer list event command to list out all events joined by a volunteer in the event display pane. This makes our product more well-rounded as it allows users to manage not only events, but volunteers as well ([#122](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/122)).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=ruo-x&breakdown=true)

* **Project management**:
    * Assigned PE dry run issues to my group members based on their parts.

* **Enhancements to existing features**:
    * Designed a new product icon and replaced the old one with it: [#142](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/142).

* **Documentation**:
    * User Guide:
        * Added documentation for event list, event show: [#79](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/79)
        * Added documentation for volunteer list event features: [#201](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/201)
    * Developer Guide:
        * Updated UiClassDiagram: [#118](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/118)
        * Added implementation details for show individual event feature, inclusive of UML class diagrams: [#231](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/231)
        * Added use cases for event show and event list feature: [82](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/82)
        * Added use case for volunter list event feature: [#236](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/236)

* **Team-based tasks**
    * Added FAQ, Known issues, Command summary section of the UserGuide: [#70](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/70)
    * Ensured User Guide has a standardized format and remained up to date: [#112](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/112), [#142](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/142), [#211](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/211)

* **Community**:
    * Reviewed the PRs of my group members and providing constructive feedback (examples: [#203](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/203), [#121](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/121), [#204](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/204))
    * Reported bugs for other teams (examples: [#1](https://github.com/ruo-x/ped/issues/1), [#4](https://github.com/ruo-x/ped/issues/4), [#5](https://github.com/ruo-x/ped/issues/5))
