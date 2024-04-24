package com.amonteiro.a2024_04_sdv_parisb.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amonteiro.a2024_04_sdv_parisb.ui.theme._2024_04_SDV_ParisBTheme

@Composable
fun SearchScreen() {
    Column {
        println("SearchScreen()")
        Text(text = "Text1",fontSize = 20.sp)
        Spacer(Modifier.size(8.dp))
        Text(text = "Text2",fontSize = 14.sp)
        PictureRowItem("Hello1", Color.Blue)
        PictureRowItem("Hello2", Color.Green)
        PictureRowItem("Hello3", Color.Yellow)
    }
}

@Composable
fun PictureRowItem(text : String, color:Color){
    Text(text = text,
        color = color,
        fontSize = 20.sp)
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    _2024_04_SDV_ParisBTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            SearchScreen()
        }
    }
}