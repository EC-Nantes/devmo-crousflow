# CrousFlow 🍽️

Application Android dédiée à la restauration universitaire au Crous de Nantes.
Conçue dans le cadre du cours **DEVMO — UI/UX Design et Développement Mobile**
à l'École Centrale Nantes.

## Présentation

CrousFlow permet aux étudiants de :
- Visualiser l'**affluence en temps réel** de chaque restaurant universitaire
- **Filtrer** les restaurants par proximité, affluence ou régime alimentaire
- Consulter la **fiche détaillée** d'un RU (menu du jour, diagramme d'affluence, itinéraire)

## Écrans développés

| Écran | Tâche | Description |
|-------|-------|-------------|
| Accueil | T1 | Liste des 13 RU avec badges d'affluence et filtres rapides |
| Filtres avancés | T1.1 | Filtrage par proximité, régime alimentaire, affluence maximale |
| Fiche RU | T1.2 | Détail d'un restaurant : photo, menu du jour, diagramme d'affluence |

## Stack technique

- **Langage** : Kotlin
- **UI** : Jetpack Compose
- **Navigation** : navigation-compose 2.7.7
- **État global** : FiltreState (object Kotlin singleton + mutableStateOf)

## Structure du projet
```
com.example.crousflow/
├── MainActivity.kt          # NavHost + graphe de navigation
├── AccueilScreen.kt         # Écran liste des RU
├── FiltreScreen.kt          # Écran filtres avancés
├── FicheRUScreen.kt         # Écran détail RU
├── FiltreState.kt           # État global partagé (singleton)
└── RestaurantsData.kt       # Données + logique de filtrage
```

## Lancer le projet

1. Cloner le dépôt
```bash
git clone https://github.com/EC-Nantes/devmo-crousflow.git
```
2. Ouvrir dans **Android Studio**
3. Lancer sur émulateur ou appareil physique (API 24+)

## Liens

- 🎨 [Prototype Balsamiq](https://balsamiq.cloud/scvklv6/pjei1o7)
- 📹 [Vidéo de démonstration](https://drive.google.com/drive/folders/1i-b6-RVPHLVGQ1ZKUrKGqanbT5TuZH5d)

## Auteur

Bouzidi Safa — École Centrale Nantes, 2026
Encadré par Vincent Tourre & Myriam Servières
