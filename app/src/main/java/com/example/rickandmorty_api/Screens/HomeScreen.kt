package com.example.rickandmorty_api.Screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold (
        containerColor = (Color(0xFF1e2838))
    ){ innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                HomeOptionCard(
                    imageUrl = "https://sm.ign.com/ign_latam/image/t/the-top-10/the-top-100-best-tv-shows-of-all-time_em2k.jpg",
                    title = "Ver Personajes",
                    onClick = { navController.navigate("characterList") }
                )
            }

            item {
                HomeOptionCard(
                    imageUrl = "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/media/image/2019/05/rick-morty-temporada-4-llegara-noviembre.jpg?tf=3840x",
                    title = "Ver Episodios",
                    onClick = { navController.navigate("episodeList") }
                )
            }

            item {
                HomeOptionCard(
                    imageUrl = "https://static.wikia.nocookie.net/rick-y-morty-espanol/images/0/0c/T1E03_-_Parque_Anat%C3%B3mico.png/revision/latest?cb=20190827222106&path-prefix=es",
                    title = "Ver Lugares",
                    onClick = { navController.navigate("locationList") }
                )
            }
        }
    }
}

@Composable
fun HomeOptionCard(imageUrl: String, title: String, onClick: () -> Unit) {
    val scale = remember { Animatable(1f) }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        scale.animateTo(0.95f, animationSpec = tween(durationMillis = 100))
                        tryAwaitRelease()
                        scale.animateTo(1f, animationSpec = tween(durationMillis = 100))
                        onClick()
                    }
                )
            }
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF60A85F)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4 / 3f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )


            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
