# Feature & Scalability Roadmap

Recommendations for improving the robustness and scalability of the project.

## 1. Feature Enhancements
- [ ] **Data Persistence Encryption**: Use SQLCipher with Room to encrypt the local database.
- [ ] **Advanced In-App Updates**: Implement full update flows (Immediate and Flexible) for both Android and iOS.
- [ ] **Rich Media Support**: Integrate Video playback using a KMP-ready player (like `KMPVideoPlayer`).
- [ ] **Biometric Auth**: Add biometric authentication (Fingerprint/FaceID) for secure login.

## 2. Robustness & Stability
- [ ] **Unit Testing**: 
    - Aim for 80%+ coverage in `commonMain` for Use Cases and ViewModels.
    - Use `Turbine` for testing `StateFlow` emissions.
- [ ] **E2E UI Testing**: Implement Maestro or Appium for cross-platform UI testing.
- [ ] **Centralized Error Handling**: Implement a global `ErrorMapper` that converts Exception types (Ktor, Room, etc.) into user-friendly UI messages.
- [ ] **Network Connectivity Interceptor**: Implement a Ktor interceptor that automatically retries failed requests or checks for internet before firing.

## 3. Scalability
- [ ] **Multi-Module Source Sets**: As the project grows, split the `shared` module into smaller modules:
    - `:shared:domain`
    - `:shared:data`
    - `:shared:ui-kit` (Common UI components)
- [ ] **Design System**: Move hardcoded colors and spacing into a formal `Theme` object to allow for easier white-labeling.
- [ ] **Dynamic Base URLs**: Allow the app to switch environments (Dev/Prod) at runtime via a hidden "Developer Settings" menu, overriding BuildKonfig.
- [ ] **CI/CD Pipeline**: Set up GitHub Actions to run tests, build APKs, and export iOS frameworks on every PR.
