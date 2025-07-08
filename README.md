# GTS_TZ Android Application

This is the native Android client for the GTS_TZ Content Management & Moderation Platform. Built with modern Android technologies, it provides a feature-rich, role-based user experience for interacting with the backend API.

## Key Features

-   **Modern UI:** Built entirely with Jetpack Compose, providing a declarative, reactive, and beautiful user interface.
-   **Role-Based UI:** The user interface, including bottom navigation and available actions, dynamically adapts based on the logged-in user's role (Admin, Author, or Moderator).
-   **Secure Token Management:** User authentication tokens (JWT) are securely stored on-device using Android's `EncryptedSharedPreferences`.
-   **Full Author Workflow:**
    -   View, create, edit, and delete articles.
    -   Submit articles for moderation.
    -   View the status of each article (`Draft`, `On Moderation`, `Rejected`, `Published`).
-   **Admin Dashboard:**
    -   View a list of all users in the system.
    -   Create new users with specific roles (`Author`, `Moderator`).
-   **Moderator Workflow:**
    -   View a queue of articles awaiting moderation.
    -   Review, approve, or reject articles with comments.
-   **User Experience:** Includes features like pull-to-refresh for lists and clear loading/error states.

## Architecture & Tech Stack

This project follows modern Android architecture principles, emphasizing separation of concerns, testability, and maintainability.

-   **Language:** 100% [Kotlin](https://kotlinlang.org/)
-   **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) for the entire UI layer.
-   **Architecture:**
    -   **MVVM (Model-View-ViewModel):** For clear separation between UI and business logic.
    -   **Clean Architecture Principles:**
        -   **UI Layer:** Composable screens and routes.
        -   **Domain Layer:** Use Cases that encapsulate single business operations.
        -   **Data Layer:** Repositories that abstract data sources.
-   **Dependency Injection:** [Koin](https://insert-koin.io/) for managing dependencies across the application.
-   **Navigation:** [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation) for navigating between screens.
-   **Asynchronous Programming:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/flow.html) for managing background tasks and reactive data streams.
-   **Networking:**
    -   [Retrofit](https://square.github.io/retrofit/) for type-safe REST API communication.
    -   [OkHttp](https://square.github.io/okhttp/) for its powerful interceptors (used for logging and adding auth tokens).
    -   [Gson](https://github.com/google/gson) for JSON serialization/deserialization.
-   **Security:** [Jetpack Security (EncryptedSharedPreferences)](https://developer.android.com/topic/security/data) for securely storing the user's session token.

---

## Getting Started

### Prerequisites

-   [Android Studio](https://developer.android.com/studio) (Iguana 2023.2.1 or newer recommended)
-   JDK 11 or higher
-   A running instance of the [GTS_TZ Backend](<link-to-your-backend-repo>).

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd <repository-folder>
```

### 2. Configure the Backend URL

This is the most important configuration step. The app needs to know where to find your backend server.

1.  Open the project in Android Studio.
2.  Navigate to the file: `app/src/main/java/kz/petproject/gts_tz/Constants.kt`
3.  Update the `BASE_URL` constant to point to your backend's address.
    -   **For a physical device on the same network:** Use your computer's local IP address (e.g., `http://192.168.1.10:5000/`).
    -   **For the Android Emulator:** Use the special alias `http://10.0.2.2:5000/`.
    -   **For a deployed backend:** Use the production URL (e.g., `https://your-backend.sevalla.app/`).

    ```kotlin
    object Constants {
        const val BASE_URL = "https://gtstzbackend-3bvxr.sevalla.app/" // Make sure it ends with a slash!
    }
    ```

### 3. Build and Run

1.  Wait for Android Studio to sync the Gradle project.
2.  Select a target device (emulator or physical device).
3.  Click the **Run 'app'** button (▶️) in the toolbar.

The app will install and launch. You can log in using the admin credentials (`admin` / `admin1234`) or any other user you create via the admin panel.
