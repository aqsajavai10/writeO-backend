# WriteO Backend

WriteO is the backend service for the **WriteO Platform**, designed and implemented entirely using **Java Spring Boot**. It provides all the core business logic, data persistence, and REST APIs required by the frontend and other consumers.

## Features

This backend supports a wide range of functionality for managing creative writing content and user interactions. The major features include:

* **Chapters** – Create, read, update, and delete chapters within novels.
* **Characteristics** – Define and manage characteristics of characters.
* **CharacterMentions** – Track and query where characters are mentioned.
* **CharacterRelations** – Store and retrieve relations between characters.
* **Characters** – Full CRUD support for characters.
* **Mentions** – General mentions tracking and analytics.
* **ModelFeatures** – Manage internal model-related features.
* **Notes** – Add and manage notes for novels, characters, and chapters.
* **Novels** – Create, edit, and manage novels and their associated metadata.
* **UserAccount** – Manage user accounts.
* **UserAuth** – Handle authentication and token management (Keycloak integration possible).
* **UserStats** – Store and retrieve user statistics.
* **Volumes** – CRUD for novel volumes.
* **WriteONews** – Manage platform-related news and updates.

## Architecture

The backend follows a clean **layered architecture**, making the codebase scalable and maintainable:

* **config/** – Global application configurations (security, CORS, beans).
* **dao/** – Data Access Objects (Repositories) for database interactions.
* **exceps/** – Custom exception handling classes.
* **service/** – Business logic layer handling core operations.
* **utils/** – Utility classes and helper functions.
* **web/** – REST controllers exposing APIs.

This separation of concerns ensures that:

* Controllers handle HTTP requests only.
* Services contain business rules and logic.
* DAOs focus purely on persistence.
* Utilities and configs are reusable across modules.

## Database

WriteO uses a relational database MySQL with JPA/Hibernate for ORM. Entities are designed with proper relationships (One-to-Many, Many-to-Many) to support complex data models.

## Authentication & Authorization

User authentication and authorization can be implemented via **Keycloak** or Spring Security with JWT. This allows secure access control over endpoints, supporting multiple user roles (admin, writer, reader).




## Future Enhancements

* GraphQL API support for more flexible queries.
* Caching layer using Redis for faster response times.
* Asynchronous event handling (Spring Events / Kafka).
* Docker + Kubernetes deployment scripts for cloud deployment.


---

This backend is the core engine powering **WriteO**, ensuring a robust foundation for managing creative writing workflows.
