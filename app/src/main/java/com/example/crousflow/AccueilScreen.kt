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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

val BleuPrimaire = Color(0xFF1A73E8)
val VertAffluence = Color(0xFF2E7D32)
val VertAffluenceBg = Color(0xFFE8F5E9)
val OrangeAffluence = Color(0xFFE65100)
val OrangeAffluenceBg = Color(0xFFFFF3E0)
val RougeAffluence = Color(0xFFC62828)
val RougeAffluenceBg = Color(0xFFFFEBEE)
val TextSombre = Color(0xFF1C1C1C)
val TextGris = Color(0xFF757575)



@Composable
fun AccueilScreen(navController: NavController) {

    // Lire l'état global des filtres
    val chipProximite by FiltreState.chipProximite
    val chipAffluence by FiltreState.chipAffluence
    val chipRegime by FiltreState.chipRegime

    // Liste filtrée réactive
    val ruAffiches = remember(
        chipProximite, chipAffluence, chipRegime,
        FiltreState.proximite.value, FiltreState.moinsDattente.value,
        FiltreState.vegetarien.value, FiltreState.faible.value, FiltreState.modere.value
    ) { filtrerRU() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ── HEADER ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("📍 Campus Centrale Nantes", fontSize = 12.sp, color = TextGris)
                    Text(
                        "Bonjour Lucas 👋",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSombre
                    )
                }
                Row {
                    Text("🛒", fontSize = 22.sp, modifier = Modifier.padding(end = 12.dp))
                    Text("🔔", fontSize = 22.sp)
                }
            }
        }

        // ── BARRE RECHERCHE ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color(0xFFF0F4FF), RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Text("🔍 Rechercher un restaurant...", color = TextGris)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── FILTRES RAPIDES ──
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Filtrer par :", color = TextGris, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                ChipFiltre("📍 Proximité", chipProximite) {
                    FiltreState.chipProximite.value = !FiltreState.chipProximite.value
                }
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltre("🔵 Affluence", chipAffluence) {
                    FiltreState.chipAffluence.value = !FiltreState.chipAffluence.value
                }
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltre("🌿 Régime", chipRegime) {
                    FiltreState.chipRegime.value = !FiltreState.chipRegime.value
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .clickable { navController.navigate("filtre") }
                        .border(1.dp, BleuPrimaire, RoundedCornerShape(20.dp))
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("+ Filtre personnalisé...", fontSize = 13.sp, color = BleuPrimaire)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── COMPTEUR ──
        Text(
            "${ruAffiches.size} RESTAURANTS DISPONIBLE",
            color = BleuPrimaire,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        // ── LISTE RU SCROLLABLE ──
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            ruAffiches.forEach { ru ->
                CarteRU(ru = ru, onClick = {
                    navController.navigate("ficheRU/${ru.id}")
                })
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // ── BOTTOM NAV ──
        BottomNav()
    }
}

@Composable
fun ChipFiltre(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) BleuPrimaire else Color(0xFFBDBDBD),
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                if (selected) Color(0xFFE8F0FE) else Color.White,
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(label, fontSize = 13.sp, color = TextSombre)
    }
}

@Composable
fun CarteRU(ru: RestaurantRU, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = ru.imageRes),
                contentDescription = ru.nom,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(ru.nom, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextSombre)
                Spacer(modifier = Modifier.height(4.dp))
                Text("🚶 ${ru.distance} · ⏰ Ferme à ${ru.fermeture}", fontSize = 12.sp, color = TextGris)
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .background(ru.couleurAffluenceBg, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        "● ${ru.affluence}. ${ru.tempsAttente}",
                        fontSize = 12.sp,
                        color = ru.couleurAffluence,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { onClick() },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = BleuPrimaire),
                        border = BorderStroke(1.dp, BleuPrimaire),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Voir le menu", fontSize = 12.sp, color = BleuPrimaire)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {  },
                        colors = ButtonDefaults.buttonColors(containerColor = BleuPrimaire),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Précommander", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNav() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem("🏠", "Accueil", true)
        BottomNavItem("🗺️", "Carte", false)
        BottomNavItem("🛒", "Commande", false)
        BottomNavItem("👤", "Profil", false)
    }
}

@Composable
fun BottomNavItem(icon: String, label: String, selected: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, fontSize = 20.sp)
        Text(label, fontSize = 10.sp, color = if (selected) BleuPrimaire else TextGris)
    }
}