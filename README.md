# GitHub Link

The source code can be found [here](https://github.com/nimaintz/taskManager_project_MDAD).

---

# Team Members

- Liță Naomi 1231EA
- Chiorean Rebeca 1231EA

---

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

![image](https://github.com/user-attachments/assets/5e68546a-cc0a-42b3-96e4-158b17515d09)
![image](https://github.com/user-attachments/assets/71088f58-ae5c-4e7a-8835-7236bacf58fd)
![image](https://github.com/user-attachments/assets/be38b964-8da7-4408-998c-a58ead7e051c)
![image](https://github.com/user-attachments/assets/69d50497-571c-4ec2-8702-6d8e46c34d14)


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

# Implementation of concepts

1. Foreground Services 
   > Used for the music notification. The notification is started in a foreground service.
   ```
   private fun startMusic() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            startForeground(1, createNotification(isPlaying = true))
            Log.d("MusicService", "Music started")
        }

    }
   ```
2. Background Services
   > To be added

3. Intents

4. Activities

5. Broadcast Recivers

6. Shared Preferences

7. Database

8. Notifications

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


