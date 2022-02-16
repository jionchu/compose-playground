package com.joseph.composeplayground.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.joseph.composeplayground.model.Movie
import com.joseph.composeplayground.ui.home.dto.HomeAction
import com.joseph.composeplayground.ui.theme.Suit

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(
                text = "Compose Playground",
                fontSize = 16.sp,
                fontFamily = Suit,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            Text(
                text = "Jetpack Compose + MVI",
                fontSize = 10.sp,
                fontFamily = Suit,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
            Text(
                text = "Upcoming Movies",
                fontSize = 16.sp,
                fontFamily = Suit,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 26.dp)
            )
            UpcomingMovieList(
                list = uiState.value.upComingMovieList.movies,
                navController = navController
            )
            Text(
                text = "Popular Movies",
                fontSize = 16.sp,
                fontFamily = Suit,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 26.dp)
            )
            PopularMovieList(
                list = uiState.value.popularMovieList.movies,
                navController = navController
            )
        }
    }
}

@Composable
fun UpcomingMovieList(list: List<Movie>, navController: NavController) {
    LazyRow(
        modifier = Modifier
            .padding(top = 12.dp)
            .height(260.dp), // 데이터 없을 때도 영역 높이 고정
        contentPadding = PaddingValues(start = 16.dp) // clipToPadding = false
    ) {
        items(list.size) {
            UpcomingMovieItem(movie = list[it], navController = navController)
        }
    }
}

@Composable
fun UpcomingMovieItem(movie: Movie, navController: NavController) {
    Surface(
        modifier = Modifier
            .size(180.dp, 260.dp)
            .padding(end = 14.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable { navController.navigate("detail_screen/${movie.id}") },
        color = MaterialTheme.colors.onSurface,
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            ),
            contentDescription = movie.title,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier.padding(end = 10.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = movie.title ?: "",
                fontSize = 16.sp,
                fontFamily = Suit,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movie.originalTitle ?: "",
                fontSize = 12.sp,
                fontFamily = Suit
            )
        }
    }
}

@Composable
fun PopularMovieList(list: List<Movie>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        items(list.size) {
            PopularMovieItem(movie = list[it], navController = navController)
        }
    }
}

@Composable
fun PopularMovieItem(movie: Movie, navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(5.dp))
            .aspectRatio(1.36f)    // 가로 길이에 따라 높이 적용
            .clickable { navController.navigate("detail_screen/${movie.id}") },
        color = MaterialTheme.colors.onSurface,
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500${movie.backdropPath ?: movie.posterPath}"
            ),
            contentDescription = movie.title,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier.padding(end = 10.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = movie.title ?: "",
                fontSize = 16.sp,
                fontFamily = Suit,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movie.originalTitle ?: "",
                fontSize = 12.sp,
                fontFamily = Suit
            )
        }
    }
}