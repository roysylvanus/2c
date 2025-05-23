# 🎬 Movie 2C

Movie 2C is a modern Android application built using Jetpack Compose that allows users to explore, search, and save their favorite movies. It uses the TMDB (The Movie Database) API to fetch real-time movie data and supports offline caching for a smooth experience even when offline.

---

## 📱 Features

* **Dashboard** with three tabs: Home, Favorites, and Settings.
* **Home Tab**:

  * Sections for **Now Showing**, **Upcoming**, **Top Rated**, and **Popular** movies.
  * Each section displays movie cards with title, release year, description, rating, and favorite toggle.
* **Search** functionality:

  * Tap the search icon to find movies.
  * Queries are saved for reference.
* **Favorites Tab**:

  * Displays all favorite movies marked by the user.
* **Settings Tab**:

  * Change the app theme: Auto, Light, or Dark.
* **Movie Details Screen**:

  * Displays detailed information about a selected movie, including:

    * Backdrop image
    * Title, slogan, overview
    * Rating, duration, genres
    * Producing companies
    * Recommended movies
* **Offline Support**:

  * Cached movie lists and favorites available offline.
* **Error Handling** for smooth user experience.

---

## 🧠 Architecture & Design Choices

Movie 2C is built using **MVVM** architecture with **Clean Architecture** principles. The project is modularized for better scalability and maintainability:

### Modules Overview

#### 🔹 Core

* **data**: Local database and caching logic using Room.
* **network**: Retrofit integration with TMDB API endpoints and DTOs.
* **designsystem**: Centralized UI components and theming.
* **common**: Shared models, utility classes, and constants.

#### 🔹 Feature

* **movies**: Business logic for movies including repositories, use cases, UI, and ViewModels.
* **settings**: Handles app theme settings and preferences.

#### 🔹 App

* Main entry point that connects all modules and handles app-level setup.

---

## 🛠️ Setup Instructions

### Prerequisites

* Android Studio Giraffe or later
* JDK 17 or higher
* Internet connection for initial API calls

### Getting Started

1. **Clone the repository:**

   ```bash
   git clone https://github.com/roysylvanus/2c.git
   ```

2. **Add your TMDB API Access Token:**

   Modify the `secret.properties` file at the root level and add:

   ```properties
   TMDB_ACCESS_TOKEN=add-your-api-access-token-here
   ```

3. **Build the project:**

   Open in Android Studio and sync Gradle. Then run on an emulator or physical device.

---

## 🔌 Dependencies & Plugins

Movie 2C uses a curated set of modern libraries and tools:

* **Jetpack Compose**, **Material 3**, **Navigation Compose**
* **Retrofit2**, **OkHttp3**, **Gson**
* **Room Database**
* **Hilt for Dependency Injection**
* **Coil for image loading**
* **Lottie for animations**
* **Accompanist** for swipe-to-refresh
* **Mockito**, **Robolectric**, **Turbine** for testing

*See the `build.gradle.kts` files and the dependency list for full details.*

---

## 🧪 Testing

* Unit tests with JUnit, Mockito, and Kotlin Coroutines Test
  
---

## 🔒 Security

* API tokens are stored in `secret.properties` and never hardcoded.
* The file is excluded from version control via `.gitignore`.

---

## 🌐 API Reference

Powered by [TMDB API](https://developer.themoviedb.org/docs/getting-started).
