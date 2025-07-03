package com.example.actividad_1

import androidx.compose.animation.core.tween
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.actividad_1.mainActivity.AttendantSecond
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.actividad_1.ui.theme.Actividad_1Theme
import com.example.actividad_1.view.Navigate
import com.example.project_1.view.ActivityOne.AttendantLast
import com.example.project_1.view.ActivityOne.FlightStudent // Asegúrate que este import es correcto
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Actividad_1Theme {
                Navigate()
            }
        }
    }
}

@Composable
fun HorizontalPages(navController: NavHostController) {
    val pageCount = 3
    val pagerState = rememberPagerState(pageCount = { pageCount })

    LaunchedEffect(Unit) {

    }

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> FlightStudent()
            1 -> AttendantSecond()
            2 -> AttendantLast(navController)
            else -> Text(text = "Página no encontrada")
        }
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 150.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(pageCount) { i ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(12.dp)
                    .background(
                        color = if (pagerState.currentPage == i) Color.Black else Color.LightGray.copy(alpha = 0.5f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
    }
}



/*
@Composable
fun PrimeraVentana(){
    Box(
        modifier = Modifier.fillMaxSize().background( Color( 0xff00d7f9 ) )
    ){
        Text(text = "Hola desde ventana_1", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun SegundaVentana(){
    Box(
        modifier = Modifier.fillMaxSize().background( Color( 0xffff5733  ) )
    ){
        Text(text = "Hola desde ventana_2", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun TerceraVentana(){
    Box(
        modifier = Modifier.fillMaxSize().background( Color( 0xff9cff33) )
    ){
        Text(text = "Hola desde ventana_3", modifier = Modifier.align(Alignment.Center))
    }
}
*/