package com.amonteiro.a2024_04_sdv_parisb.model

import android.health.connect.datatypes.units.Length
import java.util.Random

fun main() {

    var c = CarBean("", "")
    var c2 = CarBean("", "")

//    val tCelsius = ThermometerBean.getCelsiusThermometer()
//    tCelsius.value = 18
//    println("La valeur est de ${tCelsius.value}") //18
//    tCelsius.value = -45
//    println("La valeur est de ${tCelsius.value}") //-30
//
//    val tFahrenheit = ThermometerBean.getFahrenheitThermometer()
//    tFahrenheit.value = 70
//    println("La valeur est de ${tFahrenheit.value}") //70
//    tFahrenheit.value = 130
//    println("La valeur est de ${tFahrenheit.value}") //120

}

class ThermometerBean(var min:Int, var max : Int, value: Int) {

    var value = value.coerceIn(min, max)
        set(newValue) {
            field = newValue.coerceIn(min, max)
        }

    companion object {
        fun getCelsiusThermometer() = ThermometerBean(-30, 50, 0)
        fun getFahrenheitThermometer() = ThermometerBean(20, 120, 32)
    }
}

class PrintRandomIntBean(val max : Int){
    private val random = Random()

    init {
        println(random.nextInt(max))
        println(random.nextInt(max))
        println(random.nextInt(max))
    }

    constructor() : this(100) {
        println(random.nextInt(max))
    }
}

class HouseBean(var color:String, length: Int, width : Int) {
    var area = length * width

    fun print() = println("La maison $color fait $area")
}

data class CarBean(var marque: String, var model : String?){

    var color : String = ""
}