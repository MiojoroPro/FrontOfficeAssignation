# FrontOfficeAssignation

## Description

**FrontOfficeAssignation** est une application web Spring Boot qui sert d'interface front-office pour la consultation et la gestion des réservations hôtelières. Elle se connecte à une API backend pour récupérer les réservations et permet à l'utilisateur de les visualiser et de les filtrer par plage de dates.

## Fonctionnalités

- **Affichage de la liste des réservations** : présente toutes les réservations dans un tableau avec les informations suivantes :
  - Identifiant de la réservation
  - Identifiant du client
  - Nombre de passagers
  - Date et heure de la réservation
  - Nom de l'hôtel
- **Filtrage par plage de dates** : possibilité de filtrer les réservations selon une date de début et/ou une date de fin
- **Réinitialisation du filtre** : bouton permettant d'afficher à nouveau toutes les réservations
- **Gestion des cas vides** : affichage d'un message adapté si aucune réservation n'est trouvée

## Architecture

L'application suit une architecture MVC à trois couches :

```
Navigateur → HomeController → ReservationService → API Backend (port 8383)
                     ↓
             Vue Thymeleaf (reservation.html)
```

| Composant | Rôle |
|-----------|------|
| `HomeController` | Gère les requêtes HTTP GET sur `/`, transmet les données à la vue |
| `ReservationService` | Appelle l'API backend, filtre les réservations par date |
| `Reservation` | Modèle de données représentant une réservation |
| `ApiResponse` | Encapsule la réponse générique de l'API backend |
| `reservation.html` | Template Thymeleaf pour l'affichage de l'interface utilisateur |

## Stack technique

| Technologie | Version |
|-------------|---------|
| Java | 17 |
| Spring Boot | 3.2.0 |
| Thymeleaf | (inclus dans Spring Boot) |
| Maven | 3+ |

## Prérequis

- Java 17 ou supérieur
- Maven 3 ou supérieur
- L'API backend doit être disponible sur `http://localhost:8383/reservation/api/reservations`

## Configuration

Le fichier `src/main/resources/application.properties` contient les propriétés suivantes :

```properties
server.port=8081
spring.application.name=assignation
api.reservations.url=http://localhost:8383/reservation/api/reservations
```

Le port de l'application et l'URL de l'API peuvent être modifiés dans ce fichier.

## Lancer l'application

```bash
# Compiler et lancer l'application
mvn spring-boot:run
```

L'application est ensuite accessible sur [http://localhost:8081](http://localhost:8081).

## Construire le projet

```bash
mvn clean install
```
