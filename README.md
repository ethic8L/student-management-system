# System Zarządzania Studentami

## Przegląd

System Zarządzania Studentami (Student Management System) to aplikacja umożliwiająca przechowywanie i zarządzanie danymi studentów. Użytkownik może dodawać, edytować, usuwać oraz przeglądać dane studentów. Aplikacja korzysta z bazy danych PostgreSQL (lub innej wybranej bazy) do przechowywania danych oraz Java Swing do tworzenia interfejsu graficznego (GUI).

## Funkcjonalności

- **Dodawanie studentów**: Możliwość dodania nowych studentów do systemu.
- **Edytowanie danych studentów**: Umożliwia edytowanie istniejących danych studentów.
- **Usuwanie studentów**: Możliwość usuwania studentów z bazy danych.
- **Wyświetlanie listy studentów**: Wyświetla listę wszystkich zapisanych studentów w systemie.
- **Wyszukiwanie studentów**: Możliwość wyszukiwania studentów według różnych kryteriów (np. imienia, nazwiska).

## Instrukcje dotyczące kompilacji i uruchamiania aplikacji

### Wymagania wstępne

- **Java** (JDK 11 lub wyższa) zainstalowana na komputerze.
- **Maven** do zarządzania zależnościami (lub inny system kompilacji).
- **PostgreSQL** (lub inna wybrana baza danych).

### Kroki do kompilacji

1. Sklonuj repozytorium:

    ```bash
    git clone https://github.com/ethic8L/student-management-system.git
    ```

2. Przejdź do katalogu projektu:

    ```bash
    cd student-management-system
    ```

3. Zbuduj projekt za pomocą Maven (lub innego systemu kompilacji, jeśli używasz):

    ```bash
    mvn clean install
    ```

4. Uruchom aplikację:

    ```bash
    mvn exec:java
    ```

## Instrukcje dotyczące konfiguracji bazy danych

### Tworzenie bazy danych

1. Zainstaluj i uruchom PostgreSQL (lub inną bazę danych, jeśli preferujesz).
2. Utwórz nową bazę danych, np. `student_management_system`:

    ```sql
    CREATE DATABASE student_management_system;
    ```

3. Użyj poniższego skryptu SQL, aby utworzyć tabelę `students`:

    ```sql
    CREATE TABLE students (
        id SERIAL PRIMARY KEY,
        first_name VARCHAR(100) NOT NULL,
        last_name VARCHAR(100) NOT NULL,
        birthdate DATE,
        email VARCHAR(255) UNIQUE,
        phone_number VARCHAR(20)
    );
    ```

4. Skonfiguruj połączenie z bazą danych w pliku konfiguracyjnym aplikacji (np. `application.properties`):

    ```properties
    jdbc.url=jdbc:postgresql://localhost:5432/student_management_system
    jdbc.username=your_database_username
    jdbc.password=your_database_password
    ```

5. Zainicjuj bazę danych, uruchamiając aplikację. Aplikacja automatycznie połączy się z bazą danych i utworzy odpowiednie tabele.

## Ostateczne rezultaty

Po wykonaniu powyższych kroków, aplikacja powinna być w pełni funkcjonalna. Baza danych będzie zawierała dane studentów, a interfejs użytkownika umożliwi zarządzanie nimi.

## Kontakt

Jeśli masz jakiekolwiek pytania lub uwagi, skontaktuj się z autorem projektu:
- GitHub: [ethic8L](https://github.com/ethic8L)