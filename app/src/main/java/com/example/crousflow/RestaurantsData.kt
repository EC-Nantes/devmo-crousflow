package com.example.crousflow

import androidx.compose.ui.graphics.Color



data class RestaurantRU(
    val id: String,
    val nom: String,
    val distance: String,
    val fermeture: String,
    val affluence: String,
    val tempsAttente: String,
    val couleurAffluence: Color,
    val couleurAffluenceBg: Color,
    val imageRes: Int
)

val tousLesRU = listOf(
    RestaurantRU("trebuchet", "RU Trébuchet", "3 min", "14H00",
        "Disponible", "~5 min", VertAffluence, VertAffluenceBg,
        R.drawable.censive),
    RestaurantRU("cafet", "Cafet' Centrale", "5 min", "14H00",
        "Modéré", "~15 min", OrangeAffluence, OrangeAffluenceBg,
        R.drawable.cafet_ecn),
    RestaurantRU("launay", "RU Launay", "8 min", "14H00",
        "Bondé", "~30 min", RougeAffluence, RougeAffluenceBg,
        R.drawable.launay),
    RestaurantRU("censive", "RU La Censive", "6 min", "14H00",
        "Disponible", "~5 min", VertAffluence, VertAffluenceBg,
        R.drawable.ru5),
    RestaurantRU("lombarderie", "RU Lombarderie", "12 min", "14H00",
        "Modéré", "~15 min", OrangeAffluence, OrangeAffluenceBg,
        R.drawable.ru6),
    RestaurantRU("chantrerie", "RU Chantrerie", "15 min", "14H00",
        "Disponible", "~5 min", VertAffluence, VertAffluenceBg,
        R.drawable.ru7),
    RestaurantRU("bellier", "RU Bellier", "10 min", "14H00",
        "Bondé", "~30 min", RougeAffluence, RougeAffluenceBg,
        R.drawable.ru8),
    RestaurantRU("fleuriot", "RU Fleuriot", "7 min", "14H00",
        "Disponible", "~5 min", VertAffluence, VertAffluenceBg,
        R.drawable.ru9),
    RestaurantRU("verne", "RU Jules Verne", "9 min", "14H00",
        "Modéré", "~15 min", OrangeAffluence, OrangeAffluenceBg,
        R.drawable.ru10),
    RestaurantRU("procure", "Cafet' Procure", "4 min", "14H00",
        "Disponible", "~5 min", VertAffluence, VertAffluenceBg,
        R.drawable.ru11),
    RestaurantRU("atrium", "RU Atrium", "11 min", "14H00",
        "Bondé", "~30 min", RougeAffluence, RougeAffluenceBg,
        R.drawable.ru12),
    RestaurantRU("bongars", "RU Bongars", "14 min", "14H00",
        "Disponible", "~5 min", VertAffluence, VertAffluenceBg,
        R.drawable.ru13),
    RestaurantRU("staps", "Cafet' STAPS", "20 min", "14H00",
        "Modéré", "~15 min", OrangeAffluence, OrangeAffluenceBg,
        R.drawable.ru5)
)

fun filtrerRU(): List<RestaurantRU> {
    var liste = tousLesRU

    // ── FILTRES (exclure) ──────────────────────────────────
    if (FiltreState.chipProximite.value || FiltreState.proximite.value) {
        liste = liste.filter {
            it.distance.replace(" min", "").toIntOrNull() ?: 99 <= 8
        }
    }
    if (FiltreState.chipAffluence.value || FiltreState.moinsDattente.value) {
        liste = liste.filter { it.affluence != "Bondé" }
    }
    if (FiltreState.faible.value) {
        liste = liste.filter { it.affluence == "Disponible" }
    }
    if (FiltreState.modere.value) {
        liste = liste.filter { it.affluence != "Bondé" }
    }
    if (FiltreState.vegetarien.value) {
        liste = liste.filter { it.affluence != "Bondé" } // adapter si tu ajoutes ce champ
    }

    // ── TRI (ordre d'affichage) ────────────────────────────
    liste = when {
        // Chip Proximité → trier par distance croissante
        FiltreState.chipProximite.value || FiltreState.proximite.value ->
            liste.sortedBy {
                it.distance.replace(" min", "").toIntOrNull() ?: 99
            }

        // Chip Affluence OU "Moins d'attente" → trier par temps d'attente croissant
        FiltreState.chipAffluence.value || FiltreState.moinsDattente.value ->
            liste.sortedBy { ru ->
                when (ru.affluence) {
                    "Disponible" -> 1
                    "Modéré"     -> 2
                    "Bondé"      -> 3
                    else         -> 4
                }
            }

        // Filtre Faible ou Modéré → trier aussi par attente
        FiltreState.faible.value || FiltreState.modere.value ->
            liste.sortedBy { ru ->
                when (ru.affluence) {
                    "Disponible" -> 1
                    "Modéré"     -> 2
                    "Bondé"      -> 3
                    else         -> 4
                }
            }

        // Favoris → garder ordre par défaut (ou ajouter un champ favori plus tard)
        FiltreState.favoris.value -> liste

        // Aucun filtre → ordre par défaut
        else -> liste
    }

    return liste
}
