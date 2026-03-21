## Résumé de l'application

FrontOfficeAssignation est une petite application **Spring Boot / Thymeleaf** qui permet d'afficher et filtrer les réservations envoyées par un backend (API REST configurée par `api.reservations.url`).  
Sur la page d'accueil (`/`), l'utilisateur peut :
- Consulter la liste des réservations (ID, client, passagers, date/heure, hôtel)
- Filtrer les réservations par plage de dates (début / fin)

## Lancement rapide
1. **Prérequis** : Java 17 et Maven.
2. **Configurer l'API** : par défaut dans `src/main/resources/application.properties`  
   ```
   api.reservations.url=http://localhost:8383/reservation/api/reservations
   server.port=8081
   ```
   Adaptez l'URL si le backend tourne ailleurs.
3. **Démarrer** :
   ```bash
   mvn spring-boot:run
   ```
4. **Accéder à l'interface** : http://localhost:8081/

## Fonctionnement
- **Controller** : `HomeController` charge les réservations et applique le filtre de dates.
- **Service** : `ReservationService` appelle l'API via `RestTemplate` et filtre côté front si une plage est fournie.
- **Vue** : `src/main/resources/templates/reservation.html` présente un formulaire de filtre et le tableau des réservations.

## Tests
Le projet ne contient pas encore de tests automatisés. Lancez simplement :
```bash
mvn test
```
pour vérifier qu'aucun test n'échoue si vous en ajoutez plus tard.
