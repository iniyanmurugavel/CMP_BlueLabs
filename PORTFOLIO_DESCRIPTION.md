# BlueLabs CMP - Portfolio Website

A modern, cross-platform portfolio website built with Kotlin Multiplatform and Compose Multiplatform, showcasing projects, blog posts, and professional experience with a beautiful, responsive design.

## Overview

BlueLabs CMP is a personal portfolio website that demonstrates the power of Kotlin Multiplatform by running on Web (WASM), Android, iOS, and Desktop from a single codebase. The site features a blog system with dynamic content fetching from Firebase, SEO optimization, and a clean Material 3 design that works seamlessly across all platforms.

## Key Features

- **Cross-Platform Excellence:** Single codebase targeting Web (WASM), Android, iOS, and Desktop
- **Dynamic Blog System:** Firebase-powered blog with markdown rendering and syntax highlighting
- **SEO Optimized:** Automated sitemap generation, meta tags, and search engine friendly URLs
- **Modern UI/UX:** Material 3 Adaptive Design with smooth animations and responsive layouts
- **Portfolio Showcase:** Project gallery with detailed descriptions, tech stacks, and live demos
- **Fast & Efficient:** WebAssembly for near-native web performance

## Technical Highlights

- **100% Kotlin:** Shared business logic, UI, and navigation across all platforms
- **Compose Multiplatform:** Declarative UI framework for consistent design language
- **Firebase Integration:** Real-time content management and analytics
- **Automated CI/CD:** GitHub Actions for building, testing, and deploying to Firebase Hosting
- **Sitemap Generation:** Custom Gradle task that dynamically generates sitemap.xml from Firebase blog data
- **Advanced Image Handling:** Coil 3 with Ktor for efficient image loading across platforms
- **State Management:** ViewModel and Decompose for robust navigation and state handling

## Tech Stack

- **Language:** Kotlin 2.2.20
- **UI Framework:** Compose Multiplatform 1.9.0
- **Architecture:** MVVM, Repository Pattern, Clean Architecture
- **Networking:** Ktor 3.3.0
- **Dependency Injection:** Koin 4.1.1
- **Navigation:** Jetpack Navigation Compose + Arkivanov Decompose
- **Content Rendering:** Multiplatform Markdown Renderer with syntax highlighting
- **Image Loading:** Coil 3.3.0
- **Backend:** Firebase Realtime Database
- **Hosting:** Firebase Hosting
- **Design System:** Material 3 Adaptive

## Platform Support

- **Web:** WASM (WebAssembly) - [Live Demo](https://bluelabs.in)
- **Android:** Native Android app (minSdk 24, targetSdk 36)
- **iOS:** Native iOS app (iOS 14+)
- **Desktop:** JVM Desktop app (macOS, Windows, Linux)

## Live Demo

🌐 **Website:** [https://bluelabs.in](https://bluelabs.in)

📱 **Play Store:** Coming Soon

🍎 **App Store:** Coming Soon

## Project Structure

```
BlueLabsCMP/
├── composeApp/          # Shared Compose Multiplatform code
│   ├── commonMain/      # Common code for all platforms
│   ├── androidMain/     # Android-specific code
│   ├── iosMain/         # iOS-specific code
│   ├── wasmJsMain/      # Web (WASM) specific code
│   └── desktopMain/     # Desktop-specific code
├── buildSrc/            # Custom Gradle scripts (sitemap generator)
└── iosApp/              # iOS app entry point
```

## Features in Detail

### Dynamic Blog System
- Fetch blog posts from Firebase in real-time
- Markdown rendering with code syntax highlighting
- Search and filter functionality with fuzzy search
- Share and bookmark capabilities

### SEO & Performance
- Automated sitemap.xml generation from Firebase data
- Meta tags and Open Graph support
- Optimized WASM bundle size
- Lazy loading and efficient resource management

### Responsive Design
- Material 3 Adaptive Navigation Suite
- Window size class-based layouts
- Smooth transitions and animations
- Dark mode support (coming soon)

---

*Built with ❤️ using Kotlin Multiplatform & Compose Multiplatform*