package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.*
import com.example.ui.MainViewModel
import com.example.ui.Screen
import com.example.ui.theme.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// --- Custom Brutalist Modifiers ---

fun Modifier.hardShadow(
    shadowColor: Color = Color.Black,
    offsetX: Dp = 6.dp,
    offsetY: Dp = 6.dp,
    borderWidth: Dp = 2.dp
) = this.drawBehind {
    // Draw solid shadow box offset
    drawRect(
        color = shadowColor,
        topLeft = Offset(offsetX.toPx(), offsetY.toPx()),
        size = this.size
    )
}.border(width = borderWidth, color = Color.Black, shape = RectangleShape)

// --- Bottom Navigation Composable ---
@Composable
fun RidgeHallBottomNavBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Black,
        modifier = modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val items = listOf(
                NavigationItem("HUB", Screen.Hub, Icons.Default.Home),
                NavigationItem("VENDORS", Screen.Vendors, Icons.Default.ShoppingCart),
                NavigationItem("DECIDE", Screen.Decide, Icons.Default.Star),
                NavigationItem("JOURNAL", Screen.Factory, Icons.Default.List),
                NavigationItem("REWARDS", Screen.Rewards, Icons.Default.Favorite)
            )

            items.forEach { item ->
                val isActive = when (item.screen) {
                    Screen.Hub -> currentScreen is Screen.Hub
                    Screen.Vendors -> currentScreen is Screen.Vendors
                    Screen.Decide -> currentScreen is Screen.Decide
                    Screen.Factory -> currentScreen is Screen.Factory
                    Screen.Rewards -> currentScreen is Screen.Rewards
                    else -> false
                }

                if (isActive) {
                    // Active button has brutalist Heritage Red stamp style
                    Box(
                        modifier = Modifier
                            .hardShadow(
                                shadowColor = Color.Black,
                                offsetX = 3.dp,
                                offsetY = 3.dp,
                                borderWidth = 1.5.dp
                            )
                            .background(HeritageRed)
                            .clickable { onNavigate(item.screen) }
                            .padding(horizontal = 14.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = StarkWhite,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = item.label,
                                style = IndustrialTypography.labelMd,
                                color = StarkWhite,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(top = 3.dp)
                            )
                        }
                    }
                } else {
                    // Inactive buttons have clean foundry brass look
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RectangleShape)
                            .clickable { onNavigate(item.screen) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = FoundryCream,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = item.label,
                            style = IndustrialTypography.labelMd,
                            color = FoundryCream,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(top = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

data class NavigationItem(val label: String, val screen: Screen, val icon: ImageVector)

// --- Generic Header Bar --
@Composable
fun RidgeHallHeader(
    onMenuClick: () -> Unit,
    profilePoints: Int,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Black,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(64.dp)
            .border(width = 2.dp, color = Color.Black),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onMenuClick() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Sidebar",
                    tint = RidgeGold,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "RIDGE HALL AMBLER",
                    style = IndustrialTypography.labelXl,
                    color = RidgeGold,
                    letterSpacing = 1.sp
                )
            }

            // Loyalty counter quick check
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RectangleShape)
                    .clickable { onProfileClick() }
                    .background(Color.DarkGray)
                    .border(1.dp, RidgeGold)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Rewards Point Hub",
                    tint = RidgeGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "$profilePoints PTS",
                    style = IndustrialTypography.labelMd,
                    color = RidgeGold,
                    fontSize = 11.sp
                )
            }
        }
    }
}

// ------ SCREEN 1: HUB / HOME SCREEN ------
@Composable
fun HubScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var hoursDialogVisible by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FoundryCream)
    ) {
        // Hero Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .border(width = 2.dp, color = Color.Black)
            ) {
                // Background Hero Image
                AsyncImage(
                    model = "https://lh3.googleusercontent.com/aida/AP1WRLs4eE4Pi_VJHANNcihHhTNWE4GuetwrDoG-XFO1RrvBskTKIrbPusIURpc7RNbpEtKA-blnZh4Zp1TZB3EI0i6fljpV1xUEUK61x_RgqkJ4lxgLIWOiwSG3o4J7-bOSdz2f2zoSJ-tfTBCZbno-xl-zvM-12Lp98EZqnDqL852obL9NJpuwTqUq6TbwbwYxAp89k-pGAyOY4jwUYUXqIfxX9yHohqIjuT4lf7IV6O_olO013IxwDjnryxU",
                    contentDescription = "Ridge Hall Ambler physical space interior",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Dark Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.3f), Color.Black.copy(alpha = 0.85f)),
                                startY = 400f
                            )
                        )
                )

                // Branding Elements Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo Banner Wordmark
                    AsyncImage(
                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuC8lPZRqr7u97U0wQnLcTJTyehFIgNMAT1HqcqBflpaA3XDG3uLR5ftCjKE8Re9og2luU9s1hGMk51FOefvsU_mIH7kjUVHbUKbhZVowyWFN-JQJZ9oNXfdW4qKbpCgkFqmS_qZ1KEpjkUX0k-4-aawL6FUttWdIFGwmteSCR2s5BYpGuKfzfZ0DFYrCmYQd1msDeEfhFLr68erreUizBZdBtSV8vkZZnbxzrxnkixq8ABVVqdPkppLy4YzhX-iuFyjdO3r8KyLrAS2",
                        contentDescription = "Ridge Hall Wordmark Logo Overlay",
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(180.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Brutalist Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { viewModel.navigateTo(Screen.Vendors) },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = HeritageRed),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .hardShadow(shadowColor = Color.Black, offsetX = 4.dp, offsetY = 4.dp),
                            contentPadding = PaddingValues(vertical = 14.dp)
                        ) {
                            Text(
                                "VIEW MENU",
                                style = IndustrialTypography.labelXl,
                                color = StarkWhite,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Button(
                            onClick = { hoursDialogVisible = true },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = StarkWhite),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                                .hardShadow(shadowColor = Color.Black, offsetX = 4.dp, offsetY = 4.dp),
                            contentPadding = PaddingValues(vertical = 14.dp)
                        ) {
                            Text(
                                "HOURS",
                                style = IndustrialTypography.labelXl,
                                color = IndustrialBlack,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }

        // Social Blurb Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RidgeGold)
                    .drawBehind {
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 6f
                        )
                    }
                    .padding(vertical = 36.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "RIDGE HALL | AMBLER, PA",
                    style = IndustrialTypography.headlineMd,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = HeritageRed, thickness = 3.dp, modifier = Modifier.width(100.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "FLAVOR-FORWARD SOCIAL SPACE FEATURING TOP REGIONAL CHEFS, CRAFT BEVERAGES, AND COMMUNITY GATHERINGS.",
                    style = IndustrialTypography.bodyLg,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "ESTD",
                        style = IndustrialTypography.labelXl,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "IN",
                        style = IndustrialTypography.labelXl,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "2025",
                        style = IndustrialTypography.headlineMd,
                        color = HeritageRed
                    )
                }
            }
        }

        // Food Factions Bento Preview
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 28.dp)
            ) {
                Text(
                    "FOOD FACTIONS PREVIEW",
                    style = IndustrialTypography.headlineLg,
                    color = IndustrialBlack,
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider(color = Color.Black, thickness = 4.dp)
                Spacer(modifier = Modifier.height(16.dp))

                // Bento Item 1: Twisted Gingers
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color.Black)
                        .border(2.dp, Color.Black)
                        .clickable { viewModel.navigateTo(Screen.VendorDetail("twisted-gingers", Screen.Hub)) }
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "NOW OPEN",
                            style = IndustrialTypography.labelMd,
                            color = StarkWhite,
                            modifier = Modifier
                                .background(HeritageRed)
                                .border(1.5.dp, StarkWhite)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                .align(Alignment.Start)
                        )

                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida/AP1WRLtk0KZ_GgwY9ituiIRlseOIpmPahbgz4s8oesowAQqVI7cI0OtNPzlZkjTusa4B3BNmAl2v-yicki9cgjw--mwORLDDhZ256eZYyp0y69BPy1wFA-3hsdLkr_fpMwHI70yFQ6sqP9tpHxJt4O_5hHZwo5iRl0LuunUQZv4fC6t_V_Oay2LxuN4gYs9hLGEKxG0IoHjZ6-Y03L7BfpdBqoCQW1brM3_hH27uE3FhXzD9L54GBqhoYX4w_kKT",
                            contentDescription = "Twisted Gingers Brewing Logo Overlay",
                            modifier = Modifier.size(110.dp)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "TWISTED GINGERS",
                                style = IndustrialTypography.labelXl,
                                color = RidgeGold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                "VIEW MENU",
                                style = IndustrialTypography.labelMd,
                                color = IndustrialBlack,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(RidgeGold)
                                    .border(2.dp, Color.Black)
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Bento Grid 2: Double Down, Lucky's Roadside, Pho Mi
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Double Down Box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(210.dp)
                            .background(Color.Black)
                            .border(2.dp, Color.Black)
                            .clickable { viewModel.navigateTo(Screen.VendorDetail("double-down", Screen.Hub)) }
                            .padding(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida/AP1WRLupa9JFIpMMXA3OfUPQ54dlzgZsaszVT0jB5XvjZn3_rhSXj7ViVulGYJrF06fYxc3ugXwCgVYel9AxIze0I-CXR7IZ4vwyVbmWZfCrAFBRkJjIWeLIV6Mb8yxOKVGA2-Bm7O5WJak5cgTB6HRjb8dDZUjSSEZ1UH7Grqr1dH3OKTMVezWNnNnkdje4d7W7hd3JxICwK2GOJ6jQxcNnuogGrTXj2ahdgYyEwZHLrLHyAAv8Fw9GCVdLfNg",
                                contentDescription = "Double Down Kitchen Logo",
                                modifier = Modifier.size(70.dp)
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "DOUBLE DOWN",
                                    style = IndustrialTypography.labelMd,
                                    color = RidgeGold,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "VIEW MENU",
                                    style = IndustrialTypography.labelMd,
                                    color = Color.Black,
                                    fontSize = 11.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(RidgeGold)
                                        .border(1.dp, Color.Black)
                                        .padding(vertical = 4.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Lucky's Roadside Box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(210.dp)
                            .background(Color.Black)
                            .border(2.dp, Color.Black)
                            .clickable { viewModel.navigateTo(Screen.VendorDetail("luckys-roadside", Screen.Hub)) }
                            .padding(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida/AP1WRLvqyHD1u9s7BXAinBM9gu9zADwAqyPWFwVBZ8PNwwbZOtKk1DIH9AGuHY64sgsGvPeHqZ1WbI4sB2roCA9WCgIlNcTFWTiuGM-iuoFfOA6JMPIJXmQIBbFj9eN5q9H87aANprt8UC9uRXYRYF6D2zbAVBkfVaVh9Dy96IakJA8PDfUdOUkinurgtOCmY23pVpYTr5U1tAJUrcWMgnPcWRty8VRFncDaUpzGkpOfFClfqB9GseG2--rCTWXV",
                                contentDescription = "Lucky's Roadside Logo",
                                modifier = Modifier.size(70.dp)
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "LUCKY'S ROADSIDE",
                                    style = IndustrialTypography.labelMd,
                                    color = RidgeGold,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "VIEW MENU",
                                    style = IndustrialTypography.labelMd,
                                    color = Color.Black,
                                    fontSize = 11.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(RidgeGold)
                                        .border(1.dp, Color.Black)
                                        .padding(vertical = 4.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Pho Mi Please Box (Landscape Block)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(HeritageRed)
                        .border(2.dp, Color.Black)
                        .clickable { viewModel.navigateTo(Screen.VendorDetail("pho-mi-please", Screen.Hub)) }
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida/AP1WRLv0VkOEdShk-97MEJADWsS6idieaBDrfAO_MRA_uAJKseWwMYpH-GShdI0ZBPtXBBYAWAi3mTefwfiPaFH1t4-3ZOSAqC28F_cxTGPuIFLYPhaUhZBkHW2mtfgtOSQsojs5bHbJYg-athtIQP5S5nJM6oh9O7Gh1TMWx9J8VLq3p5ue83ULLzVjCqRMDHQxZmgd6-p3LWmLIbo7uzixbuxxLoaah4HFBeypKFDHCcH5lDgkOMswTwDlvwaC",
                            contentDescription = "Pho Mi Please Brand Logo",
                            modifier = Modifier
                                .size(90.dp)
                                .background(StarkWhite)
                                .border(1.5.dp, Color.Black)
                                .padding(4.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "PHO MI PLEASE",
                                style = IndustrialTypography.headlineMd,
                                color = StarkWhite,
                                fontSize = 20.sp
                            )
                            Text(
                                "Vietnamese comfort in every bowl",
                                style = IndustrialTypography.bodyMd,
                                color = RidgeGold,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "VIEW MORE",
                                style = IndustrialTypography.labelMd,
                                color = IndustrialBlack,
                                modifier = Modifier
                                    .background(RidgeGold)
                                    .border(1.5.dp, Color.Black)
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Beautiful photography grid
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                val gridImages = listOf(
                    "https://lh3.googleusercontent.com/aida/AP1WRLvNgOIF-bbfaZ62vz9OxZLydA0o4pNlvm_yiOdjuRrg93VsUERbjOL1dk3QP_VCfhy-s7BcZ7wPlPUU_ql-pzdI7YV9ATHeXLFX5_aCSwiDVRj0cLVRRyGYK02Y-kMLwBhR-avEbTivJWYlsU3OEvcNzguojy2ZAMsE4P0JB8DGOw4pBYLZQ8JbV3TjHALs0v0wCgtehdZVjy1nW94DcSJ2qBCSEd4b0GF_ZhWHKW6Uk7QyFT3qDHkvAVow",
                    "https://lh3.googleusercontent.com/aida/AP1WRLuIxO7FGIFek2nZvkOpnbPe1dK8lOAoOkCJxKwqDVlACZxxe8-75BHCwDiT0dWdKznDid5oCAr8QzKYkB4fHHqhZRc-Qw3MWMHyDdBByBMCVFRlX87FNElg9uMALCrGTnZCGaXYJrIVseqWEsNoocyYiI30hFtFWsQ0s2vpYuRGfNRy2bkbkN-glMhxMSO7QE0I5wZayM7OgChLaXFHNd0xfrGkMqJDEFtUdjfNhu_QhDR2VUE_d2khsHo",
                    "https://lh3.googleusercontent.com/aida/AP1WRLu2Wra8nZgaE87ROoHAa8TOYEVXotEFCKi9NsuHcCz8TtEsa0OU3-Zfgez5Gcfvia2hXpqix6WJedw3EWbbQz4TMWs7kXR2YngBEthaSbTRW2zocLlo5Rgzo8eTsAufJFHBCjcRuseBYGKW8mgzykWGJD4Z96P7RmSQ3vcM83rKQZQ6lC5SaiCZlHSB8qPyhf6Pxyfp-AuVKf7Se8uO9tQCrZSQBO8C4ZhsvUrYg65XdQ8x4CHSMgCg5_LY",
                    "https://lh3.googleusercontent.com/aida/AP1WRLvFAfrEMsAa38K66xZ9y5wpxfM5oaJKl4q8XkHojl_vaa0zSwkoGyLRM7ZdvKocR7A6H1piXbq65KtJltNi1oS6YlJP3iSTHYzvhYjh-8nXtgfkrMDQX30lkqjuC_KZK-R3pVcxrsukXiah7alOnSiSDENdXe5tK6vYgUvtngY-YII_8jD2x156OB-KMtKlUvRFEjfy1_jqYsqhkssoveJOQOZzniugYh_59jvFJGR62cSY81nVoZxdqe4"
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    gridImages.take(2).forEach { url ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .border(2.dp, Color.Black)
                        ) {
                            AsyncImage(
                                model = url,
                                contentDescription = "Ridge Hall delicious food faction plate",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    gridImages.drop(2).take(2).forEach { url ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .border(2.dp, Color.Black)
                        ) {
                            AsyncImage(
                                model = url,
                                contentDescription = "Ridge Hall delicious food active tables",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.navigateTo(Screen.Vendors) },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = HeritageRed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .hardShadow(shadowColor = Color.Black, offsetX = 6.dp, offsetY = 6.dp),
                    contentPadding = PaddingValues(vertical = 18.dp)
                ) {
                    Text(
                        "EXPLORE ALL VENDORS",
                        style = IndustrialTypography.labelXl,
                        color = StarkWhite,
                        fontSize = 18.sp
                    )
                }
            }
        }

        // Newsletter sign up and Location footer
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .background(Color.Black)
                    .drawBehind {
                        drawLine(
                            color = HeritageRed,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 12f
                        )
                    }
                    .padding(vertical = 32.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "SUBSCRIBE FOR UPDATES",
                    style = IndustrialTypography.headlineMd,
                    color = RidgeGold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(14.dp))
                var newsletterEmail by remember { mutableStateOf("") }
                var isSubscribed by remember { mutableStateOf(false) }

                if (isSubscribed) {
                    Text(
                        "THANK YOU FOR SUBSCRIBING!",
                        style = IndustrialTypography.bodyLg,
                        color = StarkWhite,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                } else {
                    OutlinedTextField(
                        value = newsletterEmail,
                        onValueChange = { newsletterEmail = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(StarkWhite, RectangleShape),
                        placeholder = { Text("ENTER EMAIL", color = Color.Gray, style = IndustrialTypography.labelMd) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RidgeGold,
                            unfocusedBorderColor = Color.Black
                        ),
                        singleLine = true,
                        shape = RectangleShape
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            if (newsletterEmail.isNotEmpty()) {
                                isSubscribed = true
                                viewModel.addFreePoints(50) // Reward points for newsletter!
                            }
                        },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = HeritageRed),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, StarkWhite)
                    ) {
                        Text("SUBSCRIBE", style = IndustrialTypography.labelXl, color = StarkWhite)
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
                HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    "LOCATION",
                    style = IndustrialTypography.labelMd,
                    color = RidgeGold,
                    fontSize = 11.sp
                )
                Text(
                    "15 S RIDGE AVE, AMBLER, PA 19002",
                    style = IndustrialTypography.labelXl,
                    color = StarkWhite,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "CONTACT",
                    style = IndustrialTypography.labelMd,
                    color = RidgeGold,
                    fontSize = 11.sp
                )
                Text(
                    "INFO@RIDGEHALLAMBLER.COM",
                    style = IndustrialTypography.bodyLg,
                    color = StarkWhite,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    "© 2025 - RIDGE HALL AMBLER",
                    style = IndustrialTypography.caption,
                    color = Color.DarkGray
                )
            }
        }
    }

    // Operating Hours dialog
    if (hoursDialogVisible) {
        AlertDialog(
            onDismissRequest = { hoursDialogVisible = false },
            title = {
                Text(
                    "OPERATING HOURS",
                    style = IndustrialTypography.labelXl,
                    color = HeritageRed
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("MON - THU", style = IndustrialTypography.bodyMd, color = Color.Black)
                        Text("11AM - 9PM", style = IndustrialTypography.labelMd, color = Color.Black)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("FRI - SAT", style = IndustrialTypography.bodyMd, color = Color.Black)
                        Text("11AM - 10PM", style = IndustrialTypography.labelMd, color = Color.Black)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("SUN", style = IndustrialTypography.bodyMd, color = HeritageRed)
                        Text("11AM - 8PM", style = IndustrialTypography.labelMd, color = HeritageRed)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { hoursDialogVisible = false }) {
                    Text("OK", style = IndustrialTypography.labelXl, color = Color.Black)
                }
            },
            shape = RectangleShape,
            containerColor = StarkWhite,
            modifier = Modifier.border(2.dp, Color.Black)
        )
    }
}

// ------ SCREEN 2: VENDORS / DIRECTORY ------
@Composable
fun VendorsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val favorites by viewModel.favorites.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Brews", "Seafood", "Street Food", "Comfort", "★ Saved")

    val filteredVendors = remember(searchQuery, selectedCategory, favorites) {
        VendorData.allVendors.filter { vendor ->
            val matchesQuery = vendor.name.contains(searchQuery, ignoreCase = true) ||
                    vendor.tagline.contains(searchQuery, ignoreCase = true) ||
                    vendor.category.contains(searchQuery, ignoreCase = true)

            val matchesCategory = when (selectedCategory) {
                "All" -> true
                "★ Saved" -> favorites.any { it.vendorId == vendor.id }
                else -> vendor.category == selectedCategory
            }

            matchesQuery && matchesCategory
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FoundryCream)
    ) {
        // Hero title block
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "FOOD FACTIONS UNITE",
                        style = IndustrialTypography.headlineLg,
                        color = HeritageRed,
                        fontSize = 32.sp,
                        modifier = Modifier.weight(1f)
                    )

                    // Stencil style Tags
                    Row {
                        Box(
                            modifier = Modifier
                                .background(Color.Black)
                                .border(1.5.dp, Color.Black)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "10+ SPOTS",
                                style = IndustrialTypography.labelMd,
                                color = RidgeGold,
                                fontSize = 10.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .background(HeritageRed)
                                .border(width = 1.5.dp, color = Color.Black)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "OPEN NOW",
                                style = IndustrialTypography.labelMd,
                                color = StarkWhite,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Welcome to the foundry. 10+ legendary eateries forged in flavor. Whether it's street food, craft brews, or high-tier seafood, we serve it with industrial grit.",
                    style = IndustrialTypography.bodyLg,
                    modifier = Modifier
                        .drawBehind {
                            drawLine(
                                color = Color.Black,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = 12f
                            )
                        }
                        .padding(start = 12.dp)
                )
            }
        }

        // Search & Filters Sticky Panel
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .border(width = 2.dp, color = Color.Black)
                    .padding(14.dp)
            ) {
                // Search Input Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            "SEARCH VENDORS...",
                            color = Color.Gray,
                            style = IndustrialTypography.labelMd
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(StarkWhite, RectangleShape),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RidgeGold,
                        unfocusedBorderColor = Color.Black
                    ),
                    shape = RectangleShape,
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable filters horizontal row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Button(
                            onClick = { selectedCategory = cat },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) RidgeGold else StarkWhite,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(2.dp, Color.Black),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = cat.uppercase(),
                                style = IndustrialTypography.labelMd,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }

        // Empty State Helper
        if (filteredVendors.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "No faction match found",
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "NO FOOD FACTIONS MATCH YOUR FILTER.",
                        style = IndustrialTypography.bodyLg,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Try a different search query or click 'ALL' to reset.",
                        style = IndustrialTypography.bodyMd,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            searchQuery = ""
                            selectedCategory = "All"
                        },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = HeritageRed)
                    ) {
                        Text("RESET FILTERS", style = IndustrialTypography.labelXl, color = StarkWhite)
                    }
                }
            }
        }

        // Vendors list matching Screenshot 2
        items(filteredVendors) { vendor ->
            val isFavorite = favorites.any { it.vendorId == vendor.id }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .hardShadow(shadowColor = Color.Black, offsetX = 6.dp, offsetY = 6.dp)
                    .background(StarkWhite)
            ) {
                // Tag "FEATURED / NOW OPEN"
                if (vendor.isFeatured) {
                    Box(
                        modifier = Modifier
                            .background(HeritageRed)
                            .border(width = 1.5.dp, color = Color.Black)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "FEATURED / NOW OPEN",
                            style = IndustrialTypography.labelMd,
                            color = StarkWhite,
                            fontSize = 9.sp
                        )
                    }
                }

                // Heart Favorite Button top-right
                IconButton(
                    onClick = { viewModel.toggleFavorite(vendor.id, vendor.name) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.08f), RoundedCornerShape(100.dp))
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite Faction toggle",
                        tint = if (isFavorite) HeritageRed else Color.Black
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Margin under tags/favorites
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Food Image / Cover
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .border(width = 2.dp, color = Color.Black)
                        ) {
                            AsyncImage(
                                model = vendor.imageUrl,
                                contentDescription = "${vendor.name} physical plate cover",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Text Info
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "CATEGORY: ${vendor.category.uppercase()}",
                                style = IndustrialTypography.caption,
                                color = HeritageRed
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                vendor.name,
                                style = IndustrialTypography.headlineMd,
                                color = IndustrialBlack,
                                fontSize = 18.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                vendor.tagline,
                                style = IndustrialTypography.bodyMd,
                                color = SlateGrey,
                                fontSize = 13.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons Order and Details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { viewModel.navigateTo(Screen.VendorDetail(vendor.id, Screen.Vendors)) },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            modifier = Modifier
                                .weight(1f)
                                .border(1.5.dp, Color.Black),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Text(
                                "VIEW MENU",
                                style = IndustrialTypography.labelMd,
                                color = StarkWhite
                            )
                        }

                        Button(
                            onClick = { viewModel.navigateTo(Screen.VendorDetail(vendor.id, Screen.Vendors)) },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = StarkWhite),
                            modifier = Modifier
                                .weight(1f)
                                .border(1.5.dp, Color.Black),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Text(
                                "DETAILS",
                                style = IndustrialTypography.labelMd,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }

        // Secondary Events & Host Block
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                // Thick split divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.Black)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Latest from Forge Blog Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color.Black, RectangleShape),
                    colors = CardDefaults.cardColors(containerColor = StarkWhite),
                    shape = RectangleShape
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            "LATEST FROM THE FORGE",
                            style = IndustrialTypography.headlineMd,
                            color = HeritageRed
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Column {
                                Text(
                                    "March 12, 2025",
                                    style = IndustrialTypography.caption,
                                    color = Color.Gray
                                )
                                Text(
                                    "NEW VENDOR: LOBSTER ROLLS & MORE JOINING THE FACTION",
                                    style = IndustrialTypography.bodyLg,
                                    fontSize = 14.sp
                                )
                            }
                            HorizontalDivider()
                            Column {
                                Text(
                                    "March 08, 2025",
                                    style = IndustrialTypography.caption,
                                    color = Color.Gray
                                )
                                Text(
                                    "TASTING EVENT: CRAFT BEER & SLIDERS IN THE COURTYARD",
                                    style = IndustrialTypography.bodyLg,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Host Takeover Banner matching page 2 details
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(2.dp, Color.Black)
                ) {
                    AsyncImage(
                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDeEjuDb0IZTNXn1-IpqyO_dz0z55VQDKANSKKfZUfee7ZvaU98cFPINq1vD2AHcrHzlY1m8rRW1n3iEWpTXmQDxRrn0bu-VG_Aymmimc05rWhPjMwaaeNQ_ie47-6X2o5z3Ic3fl7Z9Bt6KfRODjbGml-mMb9TtGOwMOV7_ao3kUfGhE-H6VMvVI2u9HKGEfqTqoZWvCDMPfrfe0A6i3VCBTF9Jq4N-amQpz57sp_v3X2EWo4hW3XbvPFL3CYqu-_0KEaYA7vl312v",
                        contentDescription = "Corporate events crowd in Ridge Hall Tavern",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(HeritageRed.copy(alpha = 0.55f))
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            color = Color.Black,
                            modifier = Modifier
                                .border(1.5.dp, StarkWhite)
                                .hardShadow(StarkWhite, 4.dp, 4.dp, 1.dp)
                                .padding(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "HOST AT RIDGE",
                                    style = IndustrialTypography.labelXl,
                                    color = StarkWhite
                                )
                                Text(
                                    "Corporate events or social takeovers.",
                                    style = IndustrialTypography.caption,
                                    color = StarkWhite,
                                    fontSize = 11.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "INQUIRE NOW",
                                    style = IndustrialTypography.labelMd,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .background(RidgeGold)
                                        .clickable { viewModel.navigateTo(Screen.Factory) }
                                        .padding(horizontal = 14.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ------ SCREEN 3: VENDOR DETAIL ------
@Composable
fun VendorDetailScreen(
    vendorId: String,
    fromScreen: Screen,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val favorites by viewModel.favorites.collectAsState()
    val isFavorite = favorites.any { it.vendorId == vendorId }

    val vendor = remember(vendorId) {
        VendorData.getVendorById(vendorId) ?: VendorData.allVendors.first()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FoundryCream)
    ) {
        // Hero Header matching Screenshot 3
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .border(width = 2.dp, color = Color.Black)
            ) {
                AsyncImage(
                    model = vendor.imageUrl,
                    contentDescription = "${vendor.name} hero food detail",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f)),
                                startY = 150f
                            )
                        )
                )

                // Back Button
                IconButton(
                    onClick = { viewModel.navigateBack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Black.copy(alpha = 0.6f))
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go back",
                        tint = StarkWhite
                    )
                }

                // Header Contents
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = vendor.logoUrl,
                            contentDescription = "${vendor.name} brand logo",
                            modifier = Modifier
                                .size(56.dp)
                                .background(StarkWhite)
                                .border(1.dp, Color.Black)
                                .padding(2.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                "STALL #04 - RIDGE HALL",
                                style = IndustrialTypography.caption,
                                color = RidgeGold
                            )
                            Text(
                                vendor.name,
                                style = IndustrialTypography.headlineLg,
                                color = StarkWhite,
                                fontSize = 24.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        vendor.description,
                        style = IndustrialTypography.bodyMd,
                        color = FoundryCream,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Action panel (Favorite & Order)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { viewModel.toggleFavorite(vendor.id, vendor.name) },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFavorite) HeritageRed else StarkWhite,
                        contentColor = if (isFavorite) StarkWhite else Color.Black
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .hardShadow(shadowColor = Color.Black, offsetX = 3.dp, offsetY = 3.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Save faction"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        if (isFavorite) "SAVED" else "SAVE FACTION",
                        style = IndustrialTypography.labelMd
                    )
                }

                Button(
                    onClick = {
                        viewModel.addFreePoints(25) // trigger reward for simulating inquiry!
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = HeritageRed),
                    modifier = Modifier
                        .weight(1f)
                        .hardShadow(shadowColor = Color.Black, offsetX = 3.dp, offsetY = 3.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text(
                        "ORDER NOW",
                        style = IndustrialTypography.labelXl,
                        color = StarkWhite
                    )
                }
            }
        }

        // Info Card block (Operating Hours & Location matching page 3)
        item {
            Column(
                modifier = Modifier
                    .fillTextColorCard()
                    .padding(16.dp)
                    .hardShadow(shadowColor = Color.Black)
                    .background(StarkWhite)
                    .padding(16.dp)
            ) {
                Text(
                    "OPERATING HOURS",
                    style = IndustrialTypography.labelXl,
                    color = HeritageRed
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color.Black, thickness = 1.5.dp)

                vendor.operatingHours.forEach { (days, time) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(days, style = IndustrialTypography.bodyMd, color = Color.Black)
                        Text(
                            time,
                            style = IndustrialTypography.labelMd,
                            color = if (days == "SUN") HeritageRed else Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color.Black, thickness = 1.5.dp)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "LOCATION",
                    style = IndustrialTypography.labelXl,
                    color = HeritageRed
                )
                Text(
                    vendor.location,
                    style = IndustrialTypography.bodyMd,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Menu items listing inside a high-contrast container
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                    .background(Color.Black)
                    .padding(1.dp)
                    .background(StarkWhite)
                    .padding(16.dp)
            ) {
                // Header of menu
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = HeritageRed,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 9f
                            )
                        }
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "THE MENU",
                        style = IndustrialTypography.headlineMd,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Restaurant menu stencil",
                        tint = HeritageRed,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                vendor.menuCategories.forEach { category ->
                    // Category Badge
                    Box(
                        modifier = Modifier
                            .background(Color.Black)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = category.categoryName,
                            style = IndustrialTypography.labelMd,
                            color = RidgeGold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    category.items.forEach { item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = item.name,
                                    style = IndustrialTypography.labelXl,
                                    color = Color.Black,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = item.price,
                                    style = IndustrialTypography.headlineMd,
                                    color = HeritageRed,
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                item.description,
                                style = IndustrialTypography.bodyMd,
                                color = Color.Gray,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                            HorizontalDivider(color = Color.Black, thickness = 1.dp)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        // Multi-image tags section matching SOURCING, CRAFT, PAIRINGS
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val splitPics = listOf(
                    Triple(
                        "SOURCING",
                        "https://lh3.googleusercontent.com/aida/AP1WRLvJ0-Uc1F6kfO6nlp1DjXyWOCsiERExDwK1de92Q5jqBAWx02mVboUkgBylRkdAZr2YqJu1icWRr5Zl4ffUtiQ1C2XRyc_ZwdPqVEDqIu5aokDNsaY2RHmojyNtoR9S9opnFoAhx7SRjTPUABhgWTgjAmvYSNih52ywAR4EGsHYE1tS-BJUNRr5m7yT7l1mV6w5DrnDXzMFgVr2T7q5nVxqHCH_2pKnL2gkDIpqeUeuMFe9_myBdy1HP03j",
                        "A macro shot of fresh lobster preparation in an industrial setting"
                    ),
                    Triple(
                        "CRAFT",
                        "https://lh3.googleusercontent.com/aida/AP1WRLuS0I-zDhgblyT-dA9LqOG9mcy35hhYmB3z1nMoVKWfWh1Si4oZE9EdzXzVGKDY0ft_quY8MxKVUQrdPAD25e0jmvFpZJDVRC9LDwKHu6RPJqGHFprZ3wxlbnScrCBbTiM2sPWW6bCeI__MFPhCyWy6xMH8ZhMRsEINttQJA0J5X3QRjPHFl1ZwM-N_4m0w5X7ruFdYBy0E8I_ifoc7Jb6LloZDfS03CMquf6Ef9xlmdbTXl7piz5W5lkQC",
                        "Atmospheric food service in Ridge Hall Social tables"
                    ),
                    Triple(
                        "PAIRINGS",
                        "https://lh3.googleusercontent.com/aida-public/AB6AXuCFM4ZAs2E1Pwg2gTA-YiTg6F9iHuExUe-kns8u0JsfopoJGvRbHozNkqN8Jzz4nO6f-vVwRkIJaojZZQHaTu84gC8tVHwWvacX0hWSgLd7-w-O7qVdll01aEqwMHn52jMNmjjp5ZnnIukUATqxpMQup6NVqFcALzYt241G6OM-z8ImiuyOHQGENeR-sqH-LMw1UY0-VmUO4d_tEEV1_rytCGYA0qXePsx8YY4GhC5qNoExtNoDs69kBtx6CEno8G9I7gSyA9p-Jbju",
                        "Tasting beers on rustic iron bar"
                    )
                )

                splitPics.forEach { (title, url, desc) ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .border(2.dp, Color.Black)
                    ) {
                        AsyncImage(
                            model = url,
                            contentDescription = desc,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .background(Color.Black)
                                .align(Alignment.BottomStart)
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = title,
                                style = IndustrialTypography.labelXl,
                                color = RidgeGold
                            )
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.fillTextColorCard() = this.fillMaxWidth()

// ------ SCREEN 4: DECIDE / SPIN GAME SCREEN ------
@Composable
fun DecideScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val isSpinning by viewModel.isSpinning.collectAsState()
    val rotationAngle by viewModel.wheelRotation.collectAsState()
    val spinWinner by viewModel.spinWinner.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FoundryCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title Call
            Text(
                "CAN'T DECIDE?",
                style = IndustrialTypography.headlineXl,
                color = Color.Black,
                fontSize = 42.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "LET THE INDUSTRIAL ENGINE CHOOSE YOUR BITE",
                style = IndustrialTypography.labelXl,
                color = HeritageRed,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Wheel Construction matching screenshot 4
            Box(
                modifier = Modifier
                    .size(290.dp)
                    .hardShadow(shadowColor = Color.Black, offsetX = 8.dp, offsetY = 8.dp)
                    .clip(GenericShape { size, _ ->
                        addOval(androidx.compose.ui.geometry.Rect(0f, 0f, size.width, size.height))
                    })
                    .background(Color.Black)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                // Interactive Wheel Drawing
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(rotationAngle)
                        .drawBehind {
                            val innerSize = this.size
                            val radius = innerSize.width / 2

                            // Draw 10 Alternating colored wedge segments
                            // Alternating colors are Ridge Gold and Heritage Red
                            val colors = listOf(RidgeGold, HeritageRed)

                            for (i in 0 until 10) {
                                val startAngle = i * 36f
                                drawArc(
                                    color = colors[i % 2],
                                    startAngle = startAngle,
                                    sweepAngle = 36f,
                                    useCenter = true,
                                    size = innerSize
                                )
                            }

                            // Draw thick lines dividing sections
                            for (i in 0 until 10) {
                                val rad = (i * 36f * PI / 180f)
                                val endX = radius + radius * cos(rad).toFloat()
                                val endY = radius + radius * sin(rad).toFloat()
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(radius, radius),
                                    end = Offset(endX, endY),
                                    strokeWidth = 3f
                                )
                            }
                        }
                )

                // Central circular SPIN button layered on top
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(GenericShape { size, _ ->
                            addOval(androidx.compose.ui.geometry.Rect(0f, 0f, size.width, size.height))
                        })
                        .hardShadow(shadowColor = Color.Black, offsetX = 4.dp, offsetY = 4.dp)
                        .background(Color.Black)
                        .border(width = 4.dp, color = RidgeGold, shape = GenericShape { size, _ ->
                            addOval(androidx.compose.ui.geometry.Rect(0f, 0f, size.width, size.height))
                        })
                        .clickable { viewModel.startSpinWheel() },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "SPIN",
                            style = IndustrialTypography.labelXl,
                            color = RidgeGold,
                            fontSize = 20.sp
                        )
                        Text(
                            "TO DECIDE",
                            style = IndustrialTypography.labelMd,
                            color = StarkWhite,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            // Decider Pointer arrow
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Rotation cursor indicator",
                tint = HeritageRed,
                modifier = Modifier
                    .size(48.dp)
                    .rotate(-90f)
                    .offset(y = (-315).dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Result Display Card
            AnimatedVisibility(
                visible = spinWinner != null && !isSpinning,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                spinWinner?.let { winner ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(StarkWhite)
                            .border(width = 2.dp, color = Color.Black)
                            .hardShadow(Color.Black, offsetX = 6.dp, offsetY = 6.dp)
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "LUNCH IS SERVED!",
                            style = IndustrialTypography.labelMd,
                            color = HeritageRed
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            winner.name,
                            style = IndustrialTypography.headlineMd,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { viewModel.navigateTo(Screen.VendorDetail(winner.id, Screen.Decide)) },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = HeritageRed),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "VIEW MENU",
                                style = IndustrialTypography.labelXl,
                                color = StarkWhite
                            )
                        }
                    }
                }
            }
        }
    }
}

// ------ SCREEN 5: JOURNAL / BOOKING THE FACTORY ------
@Composable
fun FactoryScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val bookings by viewModel.bookings.collectAsState()

    val name by viewModel.bookingFormName.collectAsState()
    val type by viewModel.bookingFormType.collectAsState()
    val guests by viewModel.bookingFormGuests.collectAsState()
    val intel by viewModel.bookingFormIntel.collectAsState()
    val statusFeedback by viewModel.bookingFormStatus.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FoundryCream)
    ) {
        // Hero Card Spec Intro
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .hardShadow(shadowColor = Color.Black)
                    .background(Color.Black)
            ) {
                Column {
                    Box(modifier = Modifier.height(280.dp)) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida/AP1WRLtlgPoHZgaiwoQvntfLD3XlMn10DYBWZmEi8dkRoHGfcGJS3YFUlHVDn35KMuJKxLqhd3f1kJTsytb_yrnIpuiOy4rGD2tBtrhnTD1AP6rQnW_YqWZPAuFoxDRtX-zmtvdy0HuFWKu-fp-ZWQe6Ps7SVhT-gW3cQy4Irgti10Oe5tRzpRmtxJOO8_9N06BYoCY3g8cSsgwOnPhiUIr-9Zi7830RH52K4SvVWFoQMM1myeUu5otyRe3YXpyS",
                            contentDescription = "The Factory luxury lounge interior",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                                .background(RidgeGold)
                                .border(width = 1.5.dp, color = Color.Black)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "NOW BOOKING",
                                style = IndustrialTypography.labelMd,
                                color = Color.Black
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .background(StarkWhite)
                            .padding(20.dp)
                    ) {
                        Text(
                            "THE FACTORY",
                            style = IndustrialTypography.headlineLg,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            "Forged in 1904, our premier event space blends raw industrial power with modern social elegance. From high-stakes corporate summits to legendary weddings, The Factory is the heavy-duty heart of Ambler's social scene.",
                            style = IndustrialTypography.bodyMd,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // Venue specs list matching spec details row
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text(
                    "VENUE SPECS",
                    style = IndustrialTypography.headlineLg,
                    color = Color.Black,
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                val specs = listOf(
                    Triple(
                        "CAPACITY",
                        "350 GUESTS",
                        "https://lh3.googleusercontent.com/aida/AP1WRLu9JT0KhCTRa2PjNenV35flAUe1tEHadFr6OaTjPrsJ9t0Yl0JjRtDUuR25qwJY6WzFbVSqW83I9QfvKkXu5cT3rmLf9IhID2s-F54fJY4Ov4Efe-hRiGuXNG484dCu_2UsyYhBwV-EVVEmDr_cXXax0xMagarDB5d7mQDO8-rr-fSJVIWIJ8Mufavhxj8-UOCWuUXZuHVoYDnET-hdiJE02LPMLhJ9jbfekh8b6U9_V4NKi3WTYGaS9xJS"
                    ),
                    Triple(
                        "FLOOR SPACE",
                        "5,200 SQ FT",
                        "https://lh3.googleusercontent.com/aida/AP1WRLtSTzcYv7WzLhjuwo9my1WKoV6zEdbI0exm38T0OD2kfdpYJrqAhUzPUUjWqJy51KVZgYQtVhu5imr2-QoSs6z4oxmLtLtQYzEPvKf4MUvl_Fr_BLOIBmug49ZAHvQ_uAXUSMuZz2Cq-Ed7LkhdVbDlGk2xzQbS8AqrlOSbrXppgrdkLnpdl-uEIGUmKUCtCIqSfYs2kIWH6syUv1d--6evs_3sbsgWAoyhQawBRcvb6N8prZzPi18jnFLv"
                    ),
                    Triple(
                        "BAR SERVICE",
                        "FULL SERVICE",
                        "https://lh3.googleusercontent.com/aida/AP1WRLtA9CEZil5N33kBq5u0-mzIlo4tKrwnviekmsGGJl_-JvrtKZyxSxe8va_3YvH6U9UIw2HMH2JTSWRVkUBtdQJfDbcv2mL5opOlo7YkSBHfbm09efHJwSmjGuW9RVoRL4CkU3_ZeKpiv9JlSNJsFBWRPVMANUfd1-xa6OJBCgHM9muV5oxgeO3DaJPzG2iLusfjthfd2c9CvqU0JJ3EMYFkQHdiGUUAFhZZYN4dJs75E3Nk3jq2G1aOBwGf"
                    )
                )

                specs.forEach { (title, subtitle, url) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .hardShadow(shadowColor = Color.Black),
                        shape = RectangleShape,
                        colors = CardDefaults.cardColors(containerColor = StarkWhite)
                    ) {
                        Column {
                            AsyncImage(
                                model = url,
                                contentDescription = "$title specs preview",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    title,
                                    style = IndustrialTypography.labelXl,
                                    color = Color.Black
                                )
                                Text(
                                    subtitle,
                                    style = IndustrialTypography.headlineLg,
                                    color = HeritageRed,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Virtual Tour Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .hardShadow()
                    .background(Color.Black)
                    .padding(2.dp)
                    .background(StarkWhite)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        "VIRTUAL LOUNGE TOUR",
                        style = IndustrialTypography.headlineMd,
                        color = HeritageRed
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Explore our cozy leather lounge corners and industrial-chic seating areas.",
                        style = IndustrialTypography.bodyMd
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .border(1.5.dp, Color.Black)
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida/AP1WRLtlgPoHZgaiwoQvntfLD3XlMn10DYBWZmEi8dkRoHGfcGJS3YFUlHVDn35KMuJKxLqhd3f1kJTsytb_yrnIpuiOy4rGD2tBtrhnTD1AP6rQnW_YqWZPAuFoxDRtX-zmtvdy0HuFWKu-fp-ZWQe6Ps7SVhT-gW3cQy4Irgti10Oe5tRzpRmtxJOO8_9N06BYoCY3g8cSsgwOnPhiUIr-9Zi7830RH52K4SvVWFoQMM1myeUu5otyRe3YXpyS",
                            contentDescription = "Virtual tour preview background",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .align(Alignment.Center)
                                .background(StarkWhite)
                                .border(1.5.dp, Color.Black)
                                .clickable { viewModel.addFreePoints(15) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play interactive walkthrough",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }

        // BOOKING ASSEMBLY FORM
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .hardShadow()
                    .border(2.dp, Color.Black),
                color = StarkWhite,
                shape = RectangleShape
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Box(
                        modifier = Modifier
                            .background(HeritageRed)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            "RESERVATION REQUEST",
                            style = IndustrialTypography.labelMd,
                            color = StarkWhite
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "START YOUR ASSEMBLY",
                        style = IndustrialTypography.headlineLg,
                        color = Color.Black,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Tell us about your event. Our team of coordinators will handle the heavy lifting to ensure your gathering is forged in excellence.",
                        style = IndustrialTypography.bodyMd,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Name Input
                    Text("FULL NAME", style = IndustrialTypography.labelMd)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { viewModel.bookingFormName.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CementLight),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HeritageRed,
                            unfocusedBorderColor = Color.Black
                        ),
                        shape = RectangleShape,
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Group details grid (Dropdown simulated style)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("EVENT TYPE", style = IndustrialTypography.labelMd)
                            Spacer(modifier = Modifier.height(4.dp))
                            // Simple toggle selector for dropdown simulation
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.5.dp, Color.Black)
                                    .background(CementLight)
                                    .clickable {
                                        val next = when (type) {
                                            "WEDDING" -> "CORPORATE"
                                            "CORPORATE" -> "SOCIAL"
                                            else -> "WEDDING"
                                        }
                                        viewModel.bookingFormType.value = next
                                    }
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(type, style = IndustrialTypography.labelMd, color = Color.Black)
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "dropdown toggle")
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text("APPROX GUESTS", style = IndustrialTypography.labelMd)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = guests,
                                onValueChange = { viewModel.bookingFormGuests.value = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(CementLight),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = HeritageRed,
                                    unfocusedBorderColor = Color.Black
                                ),
                                shape = RectangleShape,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Additional Intel
                    Text("ADDITIONAL INTEL", style = IndustrialTypography.labelMd)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = intel,
                        onValueChange = { viewModel.bookingFormIntel.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .background(CementLight),
                        placeholder = { Text("DESCRIBE YOUR VISION...", color = Color.Gray, style = IndustrialTypography.bodyMd) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HeritageRed,
                            unfocusedBorderColor = Color.Black
                        ),
                        shape = RectangleShape
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    statusFeedback?.let { feedback ->
                        Text(
                            feedback,
                            color = if (feedback.startsWith("SUCCESS")) Color.DarkGray else HeritageRed,
                            style = IndustrialTypography.labelMd,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }

                    Button(
                        onClick = { viewModel.submitBooking() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RectangleShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .hardShadow(shadowColor = RidgeGold, offsetX = 4.dp, offsetY = 4.dp)
                    ) {
                        Text(
                            "SUBMIT REQUISITION",
                            style = IndustrialTypography.labelXl,
                            color = RidgeGold
                        )
                    }
                }
            }
        }

        // List of submitted booking inquiries (Persistent from Room DB)
        if (bookings.isNotEmpty()) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
                    Text(
                        "SUBMITTED ASSEMBLIES",
                        style = IndustrialTypography.headlineMd,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    bookings.forEach { req ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .border(1.5.dp, Color.Black),
                            color = StarkWhite,
                            shape = RectangleShape
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        req.fullName.uppercase(),
                                        style = IndustrialTypography.labelXl,
                                        color = Color.Black
                                    )
                                    IconButton(
                                        onClick = { viewModel.deleteBooking(req.id) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Cancel request",
                                            tint = HeritageRed
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "TYPE: ${req.eventType} • GUESTS: ${req.approxGuests}",
                                    style = IndustrialTypography.labelMd,
                                    color = Color.Gray
                                )
                                if (req.additionalIntel.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        req.additionalIntel,
                                        style = IndustrialTypography.bodyMd,
                                        color = Color.DarkGray
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier
                                        .background(CementDeep)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        "STATUS: ${req.status}",
                                        style = IndustrialTypography.caption,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ------ SCREEN 6: REWARDS / STAMP CARD ------
@Composable
fun RewardsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val rewardState by viewModel.userReward.collectAsState()
    val reward = rewardState ?: UserReward()

    val completedStamps = reward.stampsCount
    val remaining = 10 - completedStamps

    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoundryCream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Loyalty Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .hardShadow(),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "YOUR METALS COLLECTED",
                    style = IndustrialTypography.labelMd,
                    color = RidgeGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "${reward.points} PTS",
                    style = IndustrialTypography.headlineXl,
                    color = RidgeGold,
                    fontSize = 56.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "CHECK-INS: ${reward.totalCheckIns} • SPINS: ${reward.totalSpins}",
                    style = IndustrialTypography.caption,
                    color = StarkWhite
                )
            }
        }

        // Stamper Block virtual StampCard
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .hardShadow(),
            shape = RectangleShape,
            color = StarkWhite
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "FOUNDRY REWARDS",
                    style = IndustrialTypography.headlineMd,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "BUY 10 MEALS, GET 1 FREE BEVERAGE & +100 PTS!",
                    style = IndustrialTypography.labelMd,
                    color = HeritageRed,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Stamp Card Grid Representation (10 fields)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (row in 0 until 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            for (col in 1..5) {
                                val stampNum = (row * 5) + col
                                val isChecked = stampNum <= completedStamps

                                Box(
                                    modifier = Modifier
                                        .size(52.dp)
                                        .border(2.dp, Color.Black)
                                        .background(if (isChecked) HeritageRed else CementLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isChecked) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "stamped",
                                            tint = StarkWhite,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    } else {
                                        Text(
                                            text = "$stampNum",
                                            style = IndustrialTypography.labelXl,
                                            color = Color.Black.copy(alpha = 0.2f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                if (completedStamps < 10) {
                    Text(
                        "$remaining MORE CHECKS TO EARN!",
                        style = IndustrialTypography.bodyLg,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        "PRIZE ASSEMBLY COMPLETE!",
                        style = IndustrialTypography.bodyLg,
                        color = HeritageRed,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Actions: Check-in Stamp and Redeem Prize
                Button(
                    onClick = {
                        viewModel.earnCheckInStamp { completed ->
                            snackbarMessage = if (completed) {
                                "CARD FILLED! CLAIM YOUR BEVERAGE!"
                            } else {
                                "STAMP RECORDED! +20 PTS"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    enabled = completedStamps < 10
                ) {
                    Text("SIMULATE CHECK-IN STAMP", style = IndustrialTypography.labelXl, color = RidgeGold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        viewModel.claimDrinkReward { success ->
                            snackbarMessage = if (success) {
                                "REDEEMED PRIZE! POINTS INJECTED!"
                            } else {
                                "STAMP CARD INCOMPLETE."
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = HeritageRed),
                    enabled = completedStamps >= 10
                ) {
                    Text("REDEEM PRIZE BEVERAGE", style = IndustrialTypography.labelXl, color = StarkWhite)
                }
            }
        }

        snackbarMessage?.let { msg ->
            Spacer(modifier = Modifier.height(14.dp))
            Surface(
                modifier = Modifier
                    .fillModifierWithMaxWidth()
                    .border(2.dp, Color.Black)
                    .clickable { snackbarMessage = null },
                color = RidgeGold,
                shape = RectangleShape
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(msg, style = IndustrialTypography.labelMd, color = Color.Black)
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close feedback banner")
                }
            }
        }
    }
}

fun Modifier.fillModifierWithMaxWidth() = this.fillMaxWidth()
