package com.amonteiro.a2024_04_sdv_parisb.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a2024_04_sdv_parisb.model.PictureBean
import com.amonteiro.a2024_04_sdv_parisb.model.WeatherAPI
import com.amonteiro.a2024_04_sdv_parisb.model.WeatherBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val LONG_TEXT = """Le Lorem Ipsum est simplement du faux texte employé dans la composition
    et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard
    de l'imprimerie depuis les années 1500"""

fun main() {
    val mainViewModel = MainViewModel()
    println("Chargement des données...")
    mainViewModel.searchText.value = "Nice"
    mainViewModel.loadWeathers()

    while (mainViewModel.runInProgress) {
        Thread.sleep(500)
    }

    println("Taille de la liste : ${mainViewModel.pictureList.size}")
    println("mainViewModel.errorMessage : ${mainViewModel.errorMessage}")
    println("mainViewModel.pictureList : ${mainViewModel.pictureList}")
    println("Fin")
}

class MainViewModel : ViewModel() {
    val pictureList = mutableStateListOf<PictureBean>()
    var searchText = mutableStateOf("Nice")
    var runInProgress by mutableStateOf(false)
    var errorMessage by mutableStateOf("")


//    init {//Création d'un jeu de donnée au démarrage
//        loadFakeData()
//    }

    fun loadWeathers() {
        //Pour etre courant qu'une tache est en cours
        runInProgress = true
        errorMessage = ""
        //Tache asynchrone

        viewModelScope.launch(Dispatchers.Default) {
            try {
                //Requêtes
                val weathers: List<WeatherBean> = WeatherAPI.loadWeatherAround(searchText.value)
                //J'adapte le résultat en PictureBean qui est la classe de mon composant graphique
                val res = weathers.map {
                    PictureBean(
                        it.id,
                        it.weather.getOrNull(0)?.icon ?: "",
                        it.name,
                        "Il fait ${it.main.temp} à ${it.name} avec un vent de ${it.wind.speed}m/s"
                    )
                }
                pictureList.clear() //je vide la liste
                pictureList.addAll(res)

                //Ma tache est finie
                runInProgress = false
            }
            catch (e: Exception) {
                e.printStackTrace()
                runInProgress = false
                errorMessage = e.message ?: "Une erreur est survenue"
            }
        }

    }

    fun loadFakeDataAsync() {
        runInProgress = true
        viewModelScope.launch(Dispatchers.Default) {
            delay(2000)
            loadFakeData()
            runInProgress = false
        }
    }

    fun loadFakeData() {
        pictureList.clear()
        pictureList.addAll(
            arrayListOf(
                PictureBean(1, "https://picsum.photos/200", "ABCD", LONG_TEXT),
                PictureBean(2, "https://picsum.photos/201", "BCDE", LONG_TEXT),
                PictureBean(3, "https://picsum.photos/202", "CDEF", LONG_TEXT),
                PictureBean(4, "https://picsum.photos/203", "EFGH", LONG_TEXT)
            ).shuffled()
        ) //shuffled() pour avoir un ordre différent à chaque appel
    }
}