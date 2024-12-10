package com.example.rickandmorty_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.rickandmorty_api.Screens.CharacterDetailScreen
import com.example.rickandmorty_api.Screens.CharacterListScreen
import com.example.rickandmorty_api.Screens.EpisodeListScreen
import com.example.rickandmorty_api.Screens.HomeScreen
import com.example.rickandmorty_api.Screens.LocationListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            false
        }
        setContent {
            RickAndMortyApp()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(


                title = {
                    Box(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .background(Color(0xFF1e2838)),

                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Rick and Morty",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White

                        )
                    }
                },
                navigationIcon = {
                    if (navController.currentBackStackEntry?.destination?.route != "home") {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF1e2838)),
                modifier = Modifier.height(56.dp),
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(navController)
            }
            composable("characterList") {
                CharacterListScreen(navController = navController)
            }
            composable("episodeList") {
                EpisodeListScreen()
            }
            composable("locationList") {
                LocationListScreen()
            }
            composable("characterDetail/{id}") { backStackEntry ->
                val characterId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
                CharacterDetailScreen(characterId)
            }
        }
    }
}
