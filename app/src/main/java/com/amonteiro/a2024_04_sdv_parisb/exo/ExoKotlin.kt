package com.amonteiro.a2024_04_sdv_parisb.exo

fun main() {

    var res = scanNumber("Donnez un nombre : ")
    println("res =$res")

    println("fin")


}

fun scanText(question: String): String {
    print(question)

    return readlnOrNull() ?: "-"
}

fun scanNumber(question: String) = scanText(question).toInt()

fun boulangerie(nbC: Int = 0, nbS: Int = 0, nbB: Int = 0) = nbC * PRIX_CROISSANT + nbS * PRIX_SANDWITCH + nbB * PRIX_BAGUETTE

fun myPrint(text: String) = println("#$text#")

fun pair(c: Int) = c % 2 == 0

fun min(a: Int, b: Int, c: Int) = if (a < b && a < c) a
else if (b < a && b < c) b
else c

