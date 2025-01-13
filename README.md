# Task Manager App

Task Manager is a simple Android application that helps you organize and manage your tasks effectively. It also includes a Pomodoro timer to improve focus and productivity. You can also play music while on the app.

---

## Features

### Task Management
- Add, edit, and delete tasks.
- Categorize tasks and view them in a clean list using a RecyclerView.
- Mark tasks as complete using a checkbox.
- Listen to music(it closes automatically when you recive a call and can be closed from the notification pannel)

### Pomodoro Timer
- Focus on tasks using a built-in Pomodoro timer.
- Choose session durations of 25, 50, or 75 minutes.
- Start/Stop the timer with a simple button click.
- It also send a notification every pomodoro session.

### Settings
- Switch between light and dark themes.
- Adjust text size using a seek bar for better readability.

---

## Screenshots



---

## Project Structure

```
TaskManagerApp/
|-- app/
|   |-- src/
|   |   |-- main/
|   |   |   |-- java/com/example/taskmanager/  // Application Logic
|   |   |   |-- res/
|   |   |       |-- layout/                   // XML Layout Files
|   |   |       |-- drawable/                 // App Icons & Graphics
|   |   |       |-- values/                   // Strings, Themes, etc.
```

---

## XML Layouts

### `activity_main.xml`
Defines the main screen layout with buttons for navigation and a RecyclerView for displaying tasks.

### `fragment_new_task_menu.xml`
Contains the UI for creating or editing tasks.

### `pomodoro_main.xml`
Hosts the Pomodoro timer UI with session duration options and a timer display.

### `settings_main.xml`
Provides a settings screen to toggle themes and adjust text size.

### `task_item_cell.xml`
Defines the layout for individual task items in the RecyclerView.

---

## How to Build and Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/TaskManagerApp.git
   ```

2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Build and run the app on an emulator or physical device.

---

## Dependencies

- RecyclerView for task lists.
- CardView for task item cells.
- Material Components for modern UI design.

Add these dependencies to your `build.gradle` file:
```gradle
implementation 'androidx.recyclerview:recyclerview:1.2.1'
implementation 'androidx.cardview:cardview:1.0.0'
implementation 'com.google.android.material:material:1.8.0'
```

---

## Contributing

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature-name
   ```
3. Make your changes and commit them:
   ```bash
   git commit -m "Description of changes"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---


