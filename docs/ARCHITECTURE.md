# Project Architecture Guide

This project follows a **Clean Architecture** approach combined with **MVI (Model-View-Intent)** patterns for state management in Compose Multiplatform.

## Layers

### 1. Domain Layer (`shared/commonMain`)
- **Entities**: Pure data models used across the application.
- **Repositories (Interfaces)**: Definitions for data operations.
- **Use Cases**: Encapsulate specific business logic and coordinate data flow between repositories.

### 2. Data Layer (`shared/commonMain` & Platform folders)
- **Repositories (Implementations)**: Orchestrate data from multiple sources (Remote & Local).
- **Data Sources**:
    - **Remote (Ktor)**: API service implementations.
    - **Local (Room)**: Database and DAO implementations.
- **DTOs**: Data transfer objects for network and database mapping.

### 3. Presentation Layer (`shared/commonMain`)
- **State**: Data classes representing the UI state at any point in time.
- **Events (Intent)**: Sealed classes representing user actions or system events.
- **Action (Effect)**: One-time events (like navigation or showing a snackbar).
- **ViewModels**: Manage the `StateFlow`, process `Events`, and emit `Actions`. Powered by **Koin** for dependency injection.
- **UI (Compose)**: Declarative UI components that observe `State` and trigger `Events`.

## Core Libraries

- **Dependency Injection**: [Koin](https://insert-koin.io/)
- **Networking**: [Ktor](https://ktor.io/)
- **Database**: [Room](https://developer.android.com/jetpack/androidx/releases/room) (KMP version)
- **Image Loading**: [Coil3](https://coil-kt.github.io/coil/)
- **Configuration**: [BuildKonfig](https://github.com/yshrsmz/BuildKonfig)
- **Utilities**:
    - **SDP/SSP**: For scalable dimensions across screen sizes.
    - **Haze**: For glassmorphism/blur effects.
    - **Compottie**: For Lottie animations in CMP.

## MVI Pattern Example

1.  **Event**: User clicks "Login". `LoginEvent.OnLoginClicked` is sent to the ViewModel.
2.  **ViewModel**: Receives the event, sets `isLoading = true` in the **State**, and calls the `LoginUseCase`.
3.  **Use Case**: Executes logic and returns a result.
4.  **ViewModel**: Updates the **State** with the result (e.g., `isLoggedIn = true`) or emits a "Success" **Action**.
5.  **UI**: Collects the updated state and automatically re-renders the screen.
