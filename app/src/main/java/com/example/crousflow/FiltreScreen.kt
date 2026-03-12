package com.example.crousflow

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FiltreScreen(navController: NavController) {

    // Lire l'état global — les filtres déjà sélectionnés restent visibles
    var proximite by remember { mutableStateOf(FiltreState.proximite.value) }
    var moinsDattente by remember { mutableStateOf(FiltreState.moinsDattente.value) }
    var favoris by remember { mutableStateOf(FiltreState.favoris.value) }
    var vegetarien by remember { mutableStateOf(FiltreState.vegetarien.value) }
    var sansGluten by remember { mutableStateOf(FiltreState.sansGluten.value) }
    var halal by remember { mutableStateOf(FiltreState.halal.value) }
    var faible by remember { mutableStateOf(FiltreState.faible.value) }
    var modere by remember { mutableStateOf(FiltreState.modere.value) }
    var tous by remember { mutableStateOf(FiltreState.tous.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ── HEADER ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {
            Text(
                "←",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { navController.popBackStack() }
            )
            Text(
                "Filtres",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextSombre,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // ── TRIER PAR ──
            Text("TRIER PAR", fontSize = 11.sp, fontWeight = FontWeight.Bold,
                color = TextGris, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                ChipFiltre("📍 Proximité", proximite) { proximite = !proximite }
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltre("🔵 Moins d'attente", moinsDattente) { moinsDattente = !moinsDattente }
            }
            Spacer(modifier = Modifier.height(8.dp))
            ChipFiltre("⭐ Favoris", favoris) { favoris = !favoris }

            Spacer(modifier = Modifier.height(20.dp))

            // ── RÉGIME ALIMENTAIRE ──
            Text("RÉGIME ALIMENTAIRE", fontSize = 11.sp, fontWeight = FontWeight.Bold,
                color = TextGris, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                ChipFiltre("🌿 Végétarien", vegetarien) { vegetarien = !vegetarien }
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltre("Sans gluten", sansGluten) { sansGluten = !sansGluten }
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltre("🅷 Halal", halal) { halal = !halal }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── AFFLUENCE MAX ──
            Text("Affluence Max", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextSombre)
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                ChipFiltreColor("🟢 Faible (< 5 min)", faible, VertAffluence) {
                    faible = !faible
                    if (faible) { modere = false; tous = false }
                }
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltreColor("🟠 Modéré (< 15 min)", modere, OrangeAffluence) {
                    modere = !modere
                    if (modere) { faible = false; tous = false }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            ChipFiltreColor("🔴 Tous", tous, RougeAffluence) {
                tous = !tous
                if (tous) { faible = false; modere = false }
            }
        }

        // ── BOUTONS BAS ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    // Sauvegarder dans l'état global
                    FiltreState.proximite.value = proximite
                    FiltreState.moinsDattente.value = moinsDattente
                    FiltreState.favoris.value = favoris
                    FiltreState.vegetarien.value = vegetarien
                    FiltreState.sansGluten.value = sansGluten
                    FiltreState.halal.value = halal
                    FiltreState.faible.value = faible
                    FiltreState.modere.value = modere
                    FiltreState.tous.value = tous
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BleuPrimaire),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Appliquer les filtres", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    // Réinitialiser tout
                    FiltreState.reinitialiser()
                    proximite = false; moinsDattente = false; favoris = false
                    vegetarien = false; sansGluten = false; halal = false
                    faible = false; modere = false; tous = true
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Réinitialiser", fontSize = 16.sp, color = TextSombre)
            }
        }
    }
}

@Composable
fun ChipFiltreColor(label: String, selected: Boolean, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) color else Color(0xFFBDBDBD),
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                if (selected) color.copy(alpha = 0.1f) else Color.White,
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(label, fontSize = 13.sp, color = TextSombre)
    }
}