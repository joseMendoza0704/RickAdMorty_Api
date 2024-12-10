package com.example.rickandmorty_api.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty_api.ViewModel.ApiResult
import com.example.rickandmorty_api.ViewModel.CharacterDetailViewModel

@Composable
fun CharacterDetailScreen(characterId: Int) {
    val viewModel: CharacterDetailViewModel = viewModel()


    val characterDetail by viewModel.characterDetail.collectAsState(ApiResult.Loading)

    LaunchedEffect(characterId) {
        viewModel.getCharacterDetail(characterId.toString())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

            .background(Color(0xFF1e2838)),
        contentAlignment = Alignment.Center
    ) {
        when (val result = characterDetail) {
            is ApiResult.Loading -> {
                CircularProgressIndicator()
            }
            is ApiResult.Success -> {
                val character = result.data
                character?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF38761D)),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(it.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.White,
                            )
                            Text(
                                text = "Species: ${it.species}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.White,

                                )
                            Text(
                                text = "Status: ${it.status}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.White,
                            )
                            Text(
                                text = "Gender: ${it.gender}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.White,
                            )
                        }
                    }
                }
            }
            is ApiResult.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${result.message}", color = Color.Red)
                }
            }
        }
    }
}
