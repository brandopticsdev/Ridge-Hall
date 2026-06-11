package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.data.AppDatabase
import com.example.data.AppRepository
import com.example.ui.MainViewModel
import com.example.ui.MainViewModelFactory
import com.example.ui.Screen
import com.example.ui.screens.*
import com.example.ui.theme.HeritageRed
import com.example.ui.theme.IndustrialTypography
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RidgeGold
import com.example.ui.theme.StarkWhite

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Room persistence setup
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = AppRepository(database.appDao())
        
        // Constructor injection of Repository into ViewModel
        val viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository)
        )[MainViewModel::class.java]

        setContent {
            MyApplicationTheme {
                val currentScreen by viewModel.currentScreen.collectAsState()
                val userReward by viewModel.userReward.collectAsState()
                val points = userReward?.points ?: 150

                var showSidebarDialog by remember { mutableStateOf(false) }

                // Safe system back-press routing for the dynamic backstack
                BackHandler(enabled = currentScreen != Screen.Hub) {
                    val wasPopped = viewModel.navigateBack()
                    if (!wasPopped) {
                        finish()
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        RidgeHallHeader(
                            onMenuClick = { showSidebarDialog = true },
                            profilePoints = points,
                            onProfileClick = { viewModel.navigateTo(Screen.Rewards) }
                        )
                    },
                    bottomBar = {
                        RidgeHallBottomNavBar(
                            currentScreen = currentScreen,
                            onNavigate = { viewModel.navigateTo(it) }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE0D0A5)) // Foundry background
                    ) {
                        when (val screen = currentScreen) {
                            Screen.Hub -> HubScreen(
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                            Screen.Vendors -> VendorsScreen(
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                            is Screen.VendorDetail -> VendorDetailScreen(
                                vendorId = screen.vendorId,
                                fromScreen = screen.fromScreen,
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                            Screen.Decide -> DecideScreen(
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                            Screen.Factory -> FactoryScreen(
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                            Screen.Rewards -> RewardsScreen(
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        // Custom Brutalist Sidebar Drawer dialog
                        if (showSidebarDialog) {
                            SidebarMenuDialog(
                                onDismiss = { showSidebarDialog = false },
                                onNavigateTo = { targetScreen ->
                                    showSidebarDialog = false
                                    viewModel.navigateTo(targetScreen)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SidebarMenuDialog(
    onDismiss: () -> Unit,
    onNavigateTo: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.CenterStart
    ) {
        // Dialogue panel with custom sliding bounds starting from left
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .background(Color.White)
                .drawBehind {
                    this.drawLine(
                        color = Color.Black,
                        start = Offset(this.size.width, 0f),
                        end = Offset(this.size.width, this.size.height),
                        strokeWidth = 8f
                    )
                }
                .clickable(enabled = false) { }
                .padding(24.dp)
                .statusBarsPadding()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "FOUNDRY DIRECTIVES",
                            style = IndustrialTypography.labelXl,
                            color = HeritageRed
                        )
                        IconButton(onClick = { onDismiss() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close directives panel",
                                tint = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color.Black, thickness = 2.dp)
                    Spacer(modifier = Modifier.height(28.dp))

                    val menuItems = listOf(
                        Pair("MAIN FORGE", Screen.Hub),
                        Pair("FOOD FACTIONS", Screen.Vendors),
                        Pair("CHOICE ENGINER", Screen.Decide),
                        Pair("RESERVE THE FACTORY", Screen.Factory),
                        Pair("rewards center", Screen.Rewards)
                    )

                    menuItems.forEach { (label, screen) ->
                        Text(
                            text = label.uppercase(),
                            style = IndustrialTypography.headlineMd,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateTo(screen) }
                                .padding(vertical = 12.dp)
                        )
                        HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)
                    }
                }

                // Historic 1904 trivia card in sidebar
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, Color.Black, RectangleShape),
                    colors = CardDefaults.cardColors(containerColor = RidgeGold.copy(alpha = 0.3f)),
                    shape = RectangleShape
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "trivia info",
                                tint = HeritageRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "ESTABLISHED 1904",
                                style = IndustrialTypography.labelMd,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Ridge Hall was built as a heavy iron casting factory supporting Pennsylvania railways, before being reforged as a social food hall.",
                            style = IndustrialTypography.caption,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}
