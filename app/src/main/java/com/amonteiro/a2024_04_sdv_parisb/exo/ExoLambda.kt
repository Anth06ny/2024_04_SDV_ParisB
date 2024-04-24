package com.amonteiro.a2024_04_sdv_parisb.exo

fun main() {
    var toto = MyObservable(5)

    toto.observator = {
        println(it)
    }

    toto.value = 6


}

class MyObservable(value : Int){
    var value  = value
        set(newValue) {
            field = newValue

            observator?.invoke(newValue)
        }

    var observator : ((Int)->Unit)? = null



}




data class PersonBean(var name:String, var note:Int)

fun exo3(){
    val list = arrayListOf(PersonBean ("toto", 16),
        PersonBean ("Tata", 20),
        PersonBean ("Toto", 8),
        PersonBean ("Charles", 14))

    println("Afficher la sous liste de personne ayant 10 et +")
    //println(list.filter { it.note >=10 })
    //Pour un affichage de notre choix
    println(list.filter { it.note >=10 }.joinToString("\n") { "-${it.name} : ${it.note}"})

    //TODO
    println("\n\nAfficher combien il y a de Toto dans la classe ?")
    val isToto : (PersonBean)->Boolean = { it.name.equals("toto", true)}
    println(list.count(isToto))

    println("\n\nAfficher combien de Toto ayant la moyenne (10 et +)")
    println(list.count { isToto(it)  && it.note >= 10 })

    println("\n\nAfficher combien de Toto ont plus que la moyenne de la classe")
    val average = list.map { it.note} .average()
    println(list.count { isToto(it)  && it.note >= average })

    println("\n\nAfficher la list triée par nom sans doublon")
    println(list.distinctBy { it.name }.sortedBy { it.name }.joinToString("\n") { "-${it.name} : ${it.note}"})

    println("\n\nAjouter un point a ceux n’ayant pas la moyenne (<10)")
    list.filter { it.note<10 }.forEach { it.note++ }

    println("\n\nAjouter un point à tous les Toto")
    list.filter(isToto).forEach { it.note++ }

    println("\n\nRetirer de la liste ceux ayant la note la plus petite. (Il peut y en avoir plusieurs)")
    val minNote = list.minOf { it.note }
    list.removeIf { it.note == minNote }

    println("\n\nAfficher les noms de ceux ayant la moyenne(10et+) par ordre alphabétique")
    println(list.filter { it.note>=10 }.sortedBy { it.name }.joinToString("\n") { "-${it.name}"})

    println("\n\nDupliquer la liste ainsi que tous les utilisateurs (nouvelle instance) qu'elle contient")
    val list2 = list.map { PersonBean(it.name, it.note) }
    val list3 = list.map { it.copy() }

    println("\n\nAfficher par notes croissantes les élèves ayant eu cette note comme sur l'exemple")
    println(list.groupBy { it.note }.entries.sortedBy { it.key }.joinToString("\n") {
        "${it.key} : ${it.value.joinToString { it.name }}"
    })
}


data class UserBean(var name: String, var old: Int)

fun exo2() {
    val compareUsersByName: (UserBean, UserBean) -> UserBean = { u1, u2 -> if (u1.name.lowercase() <= u2.name.lowercase()) u1 else u2 }
    val compareUsersByOld: (UserBean, UserBean) -> UserBean = { u1, u2 -> if (u1.old >= u2.old) u1 else u2 }

    val user1 = UserBean("Bob", 19)
    val user2 = UserBean("Toto", 45)
    val user3 = UserBean("Charles", 26)

    val minByName: UserBean = compareUsersByName(user1, user2)
    println(minByName)
    println(compareUsersByOld(user1, user2))

    compareUsers(user1, user2,user3, compareUsersByName )
    compareUsers(user1, user2,user3, compareUsersByOld )
    val near30 = compareUsers(user1, user2,user3){a,b->
        if(Math.abs(a.old - 30) < Math.abs(b.old - 30)) a else b
    }
    println(near30) //UserBean("Charles", 26)


}

fun compareUsers(u1 : UserBean, u2  :UserBean, u3 : UserBean, comparator : (UserBean, UserBean)->UserBean)
    = comparator(comparator(u1, u2), u3)




fun exo1() {
    //Déclaration
    val lower: (String) -> Unit = { text: String -> println(text) }
    val lower2 = { text: String -> println(text) }
    val lower3: (String) -> Unit = { text -> println(text) }
    val lower4: (String) -> Unit = {
        println(it)
    }

    val hour: (Int) -> Int = { it / 60 }
    val max: (Int, Int) -> Int = { a, b -> Math.max(a, b) }
    val reverse: (String) -> String = { it.reversed() }

    var minToMinHour: ((Int?) -> Pair<Int, Int>?)? = { if (it == null) null else Pair(it / 60, it % 60) }


    //Appel
    lower("Coucou")
    println(hour(123))
    println(max(123, 4))
    println(reverse("Coucou"))
    println(minToMinHour?.invoke(123))
    println(minToMinHour?.invoke(null))

    minToMinHour = null


}