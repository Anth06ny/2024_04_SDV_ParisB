package com.amonteiro.a2024_04_sdv_parisb.model

import com.amonteiro.a2024_04_sdv_parisb.model.WeatherAPI.client
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.InputStreamReader


//Utilisation
fun main() {

    val weather = WeatherAPI.loadWeather("Nice")
    println("Il fait ${weather.main.temp} à ${weather.name} avec un vent de ${weather.wind.speed}m/s")

}

object WeatherAPI {
    val client = OkHttpClient()
    val gson = Gson()


    const val URL_API = "https://api.openweathermap.org/data/2.5/weather?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&q="

    fun loadWeather(cityName: String): WeatherBean {

        //Requete
        val json: String = sendGet(URL_API + cityName)
        return gson.fromJson(json, WeatherBean::class.java)
    }

    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().use { //it:Response
            //use permet de fermer la réponse qu'il y ait ou non une exception
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}\n${it.body.string()}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }

    fun loadWeatherOpti(cityName: String) = sendGetOpti(URL_API + cityName).use {
        gson.fromJson(InputStreamReader(it.body.byteStream()), WeatherBean::class.java)
    }


    fun sendGetOpti(url: String): Response {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        val response = client.newCall(request).execute() //it:Response
        //use permet de fermer la réponse qu'il y ait ou non une exception
        //Analyse du code retour
        if (!response.isSuccessful) {
            throw Exception("Réponse du serveur incorrect :${response.code}\n${response.body.string()}")
        }
        //Résultat de la requête
        return response
    }
}

/* -------------------------------- */
// Beans
/* -------------------------------- */

data class WeatherBean(var name: String, var wind: WindBean, var main: TempBean)
data class WindBean(var speed: Double)
data class TempBean(var temp: Double)

