---
layout: default.md
title: "Zac's Project Portfolio Page"
---

### Project: iVolunteer

iVolunteer is an application for volunteer coordinators to manage volunteers, events, and their interactions. The user interacts with it using a CLI, with a GUI created with JavaFX. It is written in Java with about 14 kLoC.

Given below are my contributions to the project.

* **New Feature**: Enhanced functionality for Volunteer Create, Volunteer List, and Volunteer Delete commands.
  * What it does: adapted the AB3 code for these commands to suit iVolunteer.
  * Highlights: The original AB3 implementation had an `address` field for their contacts, whereas in iVolunteer, the `address` field was not necessary. Thus, this implementation proved challenging as a huge amount of refactoring had to be done to remove all traces of the `address` field in iVolunteer's volunteers. 

* **New Feature**: Enhanced functionality for Volunteer Find command, and added the Event Find command.
  * What it does: allows the user to filter through the volunteer list for a specific volunteer(s). Able to filter by name or/and by skill.
  * Justification: This feature improves the product significantly because a volunteer coordinator can easily find volunteers with a particular skill set or name.
  * Highlights: This enhancement was not too difficult as it was a build up from the existing AB3 find command. Following this, I also implemented the Event Find command which allows users to filter through the event list.

* **New Feature**: Added the ability to undo/redo previous commands.
    * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
    * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
    * Highlights: 
      * This enhancement affects existing commands and commands to be added in the future. This is because only certain commands are undo-able, and new undo-able commands that are added in the future will also have to adopt code from the undo feature.
      * It required an in-depth analysis of design alternatives (further elaboration on pros and cons in the Developer Guide). During implementation, I had to experiment with and determine which approach was the most suitable given the project constraints. 
        * **Alternative 1 (current choice):** Saves both the volunteers and events state.
        * **Alternative 2:** Selectively save either the volunteer or event storage.
        * **Alternative 3:** Individual command knows how to undo/redo by itself.
    * Credits: I used the high-level architecture of the undo/redo commands provided by the [SE-education AB4](https://se-education.org/addressbook-level4/DeveloperGuide.html#undo-redo-feature) as a reference to build the commands. 



* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=zacwong&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=zacwong2151&tabRepo=AY2324S1-CS2103T-T14-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
    * Help set up the issue tracker and milestone management on GitHub

* **Documentation**:
    * User Guide:
        * Added documentation for the features `efind`: [\#207](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/207) and `vfind`: [\#103](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/103)
        * Added documentation for the features `undo` and `redo`: [\#206](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/206)
    * Developer Guide:
        * Added use cases for event delete feature: [\#67](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/67)
        * Added implementation details for undo and redo features, inclusive of UML class diagrams: [#216](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/216)
        * Added a Planned Enhancements section to propose fixes to add in the future: [\#215](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/215)
        * Added a Instructions for Manual Testing section to give testers guidance on using our app: [\#218](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/218)

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#122](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/122), [\#69](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/69), [\#113](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/113), [\#82](https://github.com/AY2324S1-CS2103T-T14-4/tp/pull/82)
    * PE dry run bugs found: [(1)](https://github.com/AY2324S1-CS2103T-W11-3/tp/issues/186), [(2)](https://github.com/AY2324S1-CS2103T-W11-3/tp/issues/162)
