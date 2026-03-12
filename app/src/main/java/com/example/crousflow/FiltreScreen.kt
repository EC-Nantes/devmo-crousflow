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
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun FiltreScreen(navController: NavController) {

    // États des boutons sélectionnés
    var proximite by rememberSaveable { mutableStateOf(false) }
    var moinsDattente by rememberSaveable { mutableStateOf(false) }
    var favoris by rememberSaveable { mutableStateOf(false) }

    var vegetarien by rememberSaveable { mutableStateOf(false) }
    var sansGluten by rememberSaveable { mutableStateOf(false) }
    var halal by rememberSaveable { mutableStateOf(false) }

    var faible by rememberSaveable { mutableStateOf(false) }
    var modere by rememberSaveable { mutableStateOf(false) }
    var tous by rememberSaveable { mutableStateOf(true) }

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
            Text(
                "TRIER PAR",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextGris,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                ChipFiltre("📍 Proximité", proximite) { proximite = !proximite }
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltre("🔵 Moins d'attente", moinsDattente) { moinsDattente = !moinsDattente }
            }
            Spacer(modifier = Modifier.height(8.dp))
            ChipFiltre("⭐ Favoris", favoris) { favoris = !favoris }

            Spacer(modifier = Modifier.height(20.dp))

            // ── RÉGIME ALIMENTAIRE ──
            Text(
                "RÉGIME ALIMENTAIRE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextGris,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                ChipFiltre("🌿 Végétarien", vegetarien) { vegetarien = !vegetarien }
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltre("Sans gluten", sansGluten) { sansGluten = !sansGluten }
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltre("🅷 Halal", halal) { halal = !halal }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── AFFLUENCE MAX ──
            Text(
                "Affluence Max",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextSombre
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
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
            // Bouton Appliquer
            Button(
                onClick = {
                    // Construire la liste des filtres sélectionnés
                    val filtresChoisis = mutableListOf<String>()
                    if (proximite) filtresChoisis.add("Proximité")
                    if (moinsDattente) filtresChoisis.add("Moins d'attente")
                    if (vegetarien) filtresChoisis.add("Végétarien")
                    if (sansGluten) filtresChoisis.add("Sans gluten")
                    if (halal) filtresChoisis.add("Halal")
                    if (faible) filtresChoisis.add("Faible")
                    if (modere) filtresChoisis.add("Bondé")

                    // Envoyer les filtres à l'écran Accueil
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("filtres_appliques", filtresChoisis)

                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BleuPrimaire),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Appliquer les filtres", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bouton Réinitialiser
            OutlinedButton(
                onClick = {
                    proximite = false; moinsDattente = false; favoris = false
                    vegetarien = false; sansGluten = false; halal = false
                    faible = false; modere = false; tous = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
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