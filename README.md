# Journal App Overview

**Project Information**
- **Project Name:** Journal App
**Participants**: Ayush Bhardwaj (2021A7PS2634G, f20212634@goa.bits-pilani.ac.in) - Naman Agarwal (2021A7PS2668G, f20212668@goa.bits-pilani.ac.in)

## App Functionality and Known Issue

The Journal App is designed to store tasks with date and time stamps, providing users the capability to share and delete tasks. However, a current bug exists:

- When clicking the floating action **Add** entry button, a new entry is created in the database. Unexpectedly, clicking the Back button does not discard the entry, resulting in an empty entry displayed in the EntryList recycler view. The user anticipates entry creation only upon clicking the **Save** button.

## Completed Tasks and Steps

### Task 1: Implementing Nav Graph Actions

- The `entryListFragment` serves as the start destination.
- `entryDetailsFragment` and `infoFragment` stem from the home destination.
- The "add new entry" button facilitates entry addition.

### Task 2: Modifying Database

- Added **INSERT, QUERY, and UPDATE** queries for corresponding functionalities.
- The **DELETE** button erases Journal entries.

### Task 3: Delete Button on Menu Bar

- Implemented the Delete Button on the Menu bar.
- A final warning is issued to the user before entry deletion.

### Task 4: Share Button on Menu Bar

- The Share Button employs an implicit intent, allowing users to share relevant messages through applications on their phones.

### Task 5: Implementing the 'Info' Button

- The 'Info' button initiates a new fragment via intent, displaying app information.

### Task 6: Accessibility

- Utilized **TalkBack** service for testing, offering a user-friendly experience for blind or visually impaired users.
- Ran **Accessibility Scanner** to identify and address issues, such as adjusting text contrast.

## Testing and Stress-Testing

- Adopted a test-driven approach, writing and running test cases during each development step.
- Conducted instrumented/UI tests regularly, addressing edge cases and ensuring app stability.
- Employed the monkey tool for stress testing, running it successfully for 2500, 5000, and 10000 iterations without any app crashes.

## Time Investment

- **Writing Code, Testing, and Solving Accessibility Issues:** 14 hours
- **Documentation:** 1 hour
- **Total Time:** 15 hours

## Assignment Difficulty

On a scale of 1 to 10, the assignment's difficulty is rated as **9**. The challenging aspects included database management and navigation handling using the nav graph, providing valuable insights into crucial functionalities in App Development.