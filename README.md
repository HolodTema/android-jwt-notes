# JWT Notes

**JWT Notes** is Android note‑taking app built with **JWT authentication**. It allows users to securely register, log in, and manage personal notes. The app is fully written in **Kotlin** and follows **Clean Architecture** principles.

Actually this application is just my pet project.

## Features

- **User Authentication** – Register and log in using **JWT tokens**.
- **Note Management** – Create, read, update, and delete notes.
- **Token Expiry Handling** – Automatic logout when the JWT token expires.
- **Modern UI** – Built with **Jetpack Compose** and **Material 3**.
- **Clean Architecture** – Separation of concerns with `domain`, `data`, and `app` layers.

## Tech Stack

| Layer          | Libraries                                                                |
|----------------|--------------------------------------------------------------------------|
| **UI**         | Jetpack Compose, Material 3, ConstraintLayout, Navigation Compose        |
| **DI**         | Hilt (Dagger Hilt)                                                       |
| **Networking** | Retrofit, OkHttp (Logging Interceptor), Gson Converter                   |
| **Async**      | Kotlin Coroutines + Flow                                                 |
| **Local Storage** | DataStore Preferences                                                  |
| **Architecture** | Clean Architecture (Domain‑Data‑Presentation), MVVM                     |
| **Build**      | Gradle Kotlin DSL, Version Catalog (libs.versions.toml)                  |

## Architecture

The project follows **Clean Architecture** with three distinct modules:

- **`domain`** – Contains use cases, repository interfaces, and business models.
- **`data`** – Implements repositories, handles API calls (Retrofit), and stores the JWT token with DataStore.
- **`app`** – The presentation layer: Jetpack Compose UI, ViewModels, Hilt DI, and navigation.

