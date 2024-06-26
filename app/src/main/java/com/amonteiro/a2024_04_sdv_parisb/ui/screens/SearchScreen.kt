package com.amonteiro.a2024_04_sdv_parisb.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amonteiro.a2024_04_sdv_parisb.R
import com.amonteiro.a2024_04_sdv_parisb.model.PictureBean
import com.amonteiro.a2024_04_sdv_parisb.ui.MyError
import com.amonteiro.a2024_04_sdv_parisb.ui.Routes
import com.amonteiro.a2024_04_sdv_parisb.ui.theme._2024_04_SDV_ParisBTheme
import com.amonteiro.a2024_04_sdv_parisb.viewmodel.MainViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Composable
fun SearchScreen(mainViewModel: MainViewModel, navHostController: NavHostController?) {


    Column(modifier = Modifier.padding(8.dp)) {

        SearchBar(searchText = mainViewModel.searchText)

        MyError(errorMessage = mainViewModel.errorMessage)

        AnimatedVisibility(visible = mainViewModel.runInProgress, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(8.dp))


        //Permet de remplacer très facilement le RecyclerView. LazyRow existe aussi
        LazyColumn(
            modifier = Modifier.weight(10f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            val filterList = mainViewModel.pictureList//.filter { it.title.contains(mainViewModel.searchText.value, true) }

            items(filterList.size) {
                PictureRowItem(
                    data = filterList[it],
                    onPictureClick = { navHostController?.navigate(Routes.DetailScreen.withObject(filterList[it])) }
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { mainViewModel.searchText.value = "" },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Clear filter")
            }

            Button(
                onClick = { mainViewModel.loadWeathers() },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(id = R.string.load_data))
            }
        }

    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, searchText: MutableState<String>) {

    TextField(
        value = searchText.value, //Valeur affichée
        onValueChange = { searchText.value = it }, //Nouveau texte entrée
        leadingIcon = { //Image d'icone
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        label = { Text("Filtrer : ") }, //Texte d'aide qui se déplace
        //Comment le composant doit se placer
        modifier = modifier
            .fillMaxWidth() // Prend toute la largeur
            .heightIn(min = 56.dp) //Hauteur minimum
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable //Composable affichant 1 PictureBean
fun PictureRowItem(modifier: Modifier = Modifier, data: PictureBean, onPictureClick: () -> Unit) {

    var fullText by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .then(modifier)

    ) {

        GlideImage(
            model = data.url,
            //Pour aller le chercher dans string.xml
            //contentDescription = getString(R.string.picture_of_cat),
            //En dur
            contentDescription = "une photo de chat",
            // Image d'attente. Permet également de voir l'emplacement de l'image dans la Preview
            loading = placeholder(R.mipmap.ic_launcher_round),
            // Image d'échec de chargement
            failure = placeholder(R.mipmap.ic_launcher),
            contentScale = ContentScale.Fit,
            //même autres champs qu'une Image classique
            modifier = Modifier
                .heightIn(max = 100.dp) //Sans hauteur il prendra tous l'écran
                .widthIn(max = 100.dp)
                .clickable(onClick = onPictureClick)
        )


        Column(modifier = Modifier.clickable {
            fullText = !fullText
        }) {
            Text(text = data.title, fontSize = 20.sp)
            Spacer(Modifier.size(8.dp))
            Text(
                text = if (fullText) data.longText else (data.longText.take(20) + "..."),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES,
    locale = "FR"
)
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    _2024_04_SDV_ParisBTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val mainViewModel = MainViewModel()
            mainViewModel.loadFakeData()
            mainViewModel.searchText.value = "BC"
            mainViewModel.errorMessage = "Une erreur"
            mainViewModel.runInProgress = true

            SearchScreen(mainViewModel, null)
        }
    }
}