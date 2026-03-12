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
    val ru = tousLesRU.find { it.id == ruName } ?: tousLesRU[0]

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

        // ── CONTENU SCROLLABLE ──
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            // ── PHOTO + GRAPHE CÔTE À CÔTE ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)  // ← plus grand
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Photo placeholder grande
                Image(
                    painter = painterResource(id = ru.imageRes),
                    contentDescription = ru.nom,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(160.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Colonne droite : nom + graphe
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween  // ← espace bien réparti
                ) {
                    Text(
                        ru.nom,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSombre
                    )
                    Text(
                        "Affluence en temps réel",
                        fontSize = 11.sp,
                        color = TextGris
                    )
                    JaugesAffluence()  // ← graphe en bas de la colonne
                }
            }

            // ── INFOS ──
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("🍴 Cuisine Française", fontSize = 13.sp, color = TextGris)
                Spacer(modifier = Modifier.height(4.dp))
                Text("📍 LOCALISATION", fontSize = 13.sp, color = TextGris)

                Spacer(modifier = Modifier.height(12.dp))

                // ── CARTE GRANDE ──
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color(0xFFE8EAF6), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🗺 ${ru.nom}", color = BleuPrimaire, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "4 Rte de la Jonelière, 44300 Nantes",
                    fontSize = 12.sp,
                    color = TextGris
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "🚶 ${ru.distance} à pied",
                        fontSize = 13.sp,
                        color = VertAffluence,
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        modifier = Modifier
                            .border(1.dp, BleuPrimaire, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .clickable { }
                    ) {
                        Text("Itinéraire →", fontSize = 13.sp, color = BleuPrimaire)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color(0xFFE0E0E0))
                Spacer(modifier = Modifier.height(12.dp))

                // ── MENU DU JOUR ──
                Text(
                    "🍽 MENU DU JOUR",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextGris,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                // ── ONGLETS ──
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

                // ── LISTE PLATS ──
                val plats = menuTrebuchet[ongletSelectionne] ?: emptyList()
                plats.forEach { plat ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(plat.nom, fontSize = 14.sp, color = TextSombre)
                            if (plat.isVegetarien) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(
                                            Color(0xFFE8F5E9),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        "🌿 Végétarien",
                                        fontSize = 10.sp,
                                        color = VertAffluence
                                    )
                                }
                            }
                        }
                        Text(
                            plat.prix,
                            fontSize = 14.sp,
                            color = BleuPrimaire,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Divider(color = Color(0xFFF0F0F0))
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // ── BOUTON PRÉCOMMANDER FIXE EN BAS ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = { },
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
    val hauteurs = listOf(30, 50, 70, 90, 40)  // ← plus hautes
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
                        .width(26.dp)   // ← plus larges
                        .height(hauteurs[index].dp)
                        .background(
                            couleurs[index],
                            RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                        )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(heure, fontSize = 8.sp, color = TextGris)
            }
        }
    }
}