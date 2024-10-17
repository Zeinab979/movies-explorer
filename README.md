# movies-explorerOverview
The Movie App is an Android application designed to showcase a list of popular movies, allow users to browse movie categories, and view detailed information about each movie. It follows the Clean Architecture pattern and is built using modern Android technologies like Kotlin, Jetpack Compose, and Hilt for dependency injection.

# Features
Popular Movies: Browse through a list of popular movies.
Category List: Horizontally scrollable list of movie categories.
Movie Details: Get detailed information about individual movies (title, overview, ratings, etc.).
Search: Find movies using a search function with debounce for improved performance.
Error Handling: Gracefully manage network errors and API issues with a custom Resource<T> class.

# Tech Stack
Language: Kotlin
UI: Jetpack Compose
Architecture: Clean Architecture with MVVM
Dependency Injection: Hilt
Networking: Retrofit + Coroutines for asynchronous operations
API: The Movie Database (TMDb) API

# Architecture
This project follows Clean Architecture principles, separating the app into distinct layers:

Presentation Layer: Manages UI using Jetpack Compose.
Domain Layer: Contains business logic with Use Cases.
Data Layer: Manages data fetching from the TMDb API.

# API Key Setup
To fetch movie data from TMDb, you need an API key:
Sign up at TMDb.
Get your API key from the API section.
Add the key to your project in local.properties.
