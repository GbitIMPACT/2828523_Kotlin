package com.example.project_1.view.ActivityOne

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.actividad_1.R

@Composable
fun FirstScreen( navController: NavHostController ){

}
@Composable
fun FlightStudent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "bibi",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "\u00A9",
                fontSize = 24.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 6.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.primera),
            contentDescription = "Student illustration",
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "For students who want to become flight attendants",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                modifier = Modifier.padding(24.dp),
                text = "Communicate with flight attendants, meet, find out useful information that will help you fulfill your dream",
                fontSize = 15.sp,
                color = Color.Gray,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}