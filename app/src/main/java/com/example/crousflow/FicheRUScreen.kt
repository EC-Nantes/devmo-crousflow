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

data class MenuItem(
    val nom: String,
    val prix: String,
    val isVegetarien: Boolean = false
)

val menuTrebuchet = mapOf(
    "Entrées" to listOf(
        MenuItem("Salade verte", "0.50€", true),
        MenuItem("Soupe du jour", "0.50€", true),
        MenuItem("Taboulé", "0.50€", true)
    ),
    "Plats" to listOf(
        MenuItem("Poulet rôti + frites", "3.30€"),
        MenuItem("Gratin de courgettes", "2.80€", true),
        MenuItem("Lasagnes bolognaise", "3.10€")
    ),
    "Desserts" to listOf(
        MenuItem("Yaourt nature", "0.50€", true),
        MenuItem("Brownie chocolat", "0.90€"),
        MenuItem("Fruit de saison", "0.50€", true)
    )
)

@Composable
fun FicheRUScreen(navController: NavController, ruName: String) {

    var ongletSelectionne by remember { mutableStateOf("Entrées") }
    val onglets = listOf("Entrées", "Plats", "Desserts")

    // Données selon le RU sélectionné
    val ru = listeRU.find { it.id == ruName } ?: listeRU[0]

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
                "Fiche RU",
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
        ) {

            // ── INFO RU ──
            Column(modifier = Modifier.padding(16.dp)) {

                Row(verticalAlignment = Alignment.Top) {
                    // Photo placeholder
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            ru.nom,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextSombre
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Jauges affluence
                        Text("Affluence en temps réel", fontSize = 11.sp, color = TextGris)
                        Spacer(modifier = Modifier.height(4.dp))
                        JaugesAffluence()
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text("🍴 Cuisine Française", fontSize = 13.sp, color = TextGris)
                Spacer(modifier = Modifier.height(4.dp))
                Text("📍 LOCALISATION", fontSize = 13.sp, color = TextGris)

                Spacer(modifier = Modifier.height(12.dp))

                // Carte mini
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color(0xFFE8EAF6), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🗺 ${ru.nom}", color = BleuPrimaire, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text("4 Rte de la Jonelière, 44300 Nantes", fontSize = 12.sp, color = TextGris)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🚶 ${ru.distance} à pied", fontSize = 13.sp, color = VertAffluence)
                    Box(
                        modifier = Modifier
                            .border(1.dp, BleuPrimaire, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .clickable { }
                    ) {
                        Text("Itinéraire →", fontSize = 13.sp, color = BleuPrimaire)
                    }
                }
            }

            Divider(color = Color(0xFFE0E0E0))

            // ── MENU DU JOUR ──
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    "🍽 MENU DU JOUR",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextGris,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Onglets
                Row {
                    onglets.forEach { onglet ->
                        val isSelected = onglet == ongletSelectionne
                        Box(
                            modifier = Modifier
                                .clickable { ongletSelectionne = onglet }
                                .background(
                                    if (isSelected) BleuPrimaire else Color.White,
                                    RoundedCornerShape(8.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) BleuPrimaire else Color(0xFFE0E0E0),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                onglet,
                                fontSize = 13.sp,
                                color = if (isSelected) Color.White else TextSombre,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Liste plats de l'onglet sélectionné
                val plats = menuTrebuchet[ongletSelectionne] ?: emptyList()
                plats.forEach { plat ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(plat.nom, fontSize = 14.sp, color = TextSombre)
                            if (plat.isVegetarien) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFE8F5E9), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("🌿 Végétarien", fontSize = 10.sp, color = VertAffluence)
                                }
                            }
                        }
                        Text(plat.prix, fontSize = 14.sp, color = BleuPrimaire, fontWeight = FontWeight.Bold)
                    }
                    Divider(color = Color(0xFFF0F0F0))
                }
            }
        }

        // ── BOUTON PRÉCOMMANDER ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("filtre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BleuPrimaire),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("⚡ Précommander ici", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun JaugesAffluence() {
    val heures = listOf("11h", "12h", "12h30", "13h", "14h")
    val hauteurs = listOf(30, 50, 70, 90, 40)
    val couleurs = listOf(
        VertAffluence, VertAffluence, OrangeAffluence, RougeAffluence, VertAffluence
    )

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        heures.forEachIndexed { index, heure ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width(18.dp)
                        .height(hauteurs[index].dp)
                        .background(couleurs[index], RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                )
                Text(heure, fontSize = 8.sp, color = TextGris)
            }
        }
    }
}