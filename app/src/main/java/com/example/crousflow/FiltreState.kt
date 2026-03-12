package com.example.crousflow

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

object FiltreState {
    // Filtres rapides (chips accueil)
    var chipProximite = mutableStateOf(false)
    var chipAffluence = mutableStateOf(false)
    var chipRegime = mutableStateOf(false)

    // Filtres page Filtre
    var proximite = mutableStateOf(false)
    var moinsDattente = mutableStateOf(false)
    var favoris = mutableStateOf(false)
    var vegetarien = mutableStateOf(false)
    var sansGluten = mutableStateOf(false)
    var halal = mutableStateOf(false)
    var faible = mutableStateOf(false)
    var modere = mutableStateOf(false)
    var tous = mutableStateOf(true)

    fun reinitialiser() {
        chipProximite.value = false
        chipAffluence.value = false
        chipRegime.value = false
        proximite.value = false
        moinsDattente.value = false
        favoris.value = false
        vegetarien.value = false
        sansGluten.value = false
        halal.value = false
        faible.value = false
        modere.value = false
        tous.value = true
    }

    fun aucunFiltreActif(): Boolean {
        return !chipProximite.value && !chipAffluence.value && !chipRegime.value
                && !proximite.value && !moinsDattente.value && !vegetarien.value
                && !sansGluten.value && !halal.value && !faible.value && !modere.value
    }
}