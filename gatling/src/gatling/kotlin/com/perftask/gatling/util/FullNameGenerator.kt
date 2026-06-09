package com.perftask.gatling.util

import kotlin.random.Random

object FullNameGenerator {

    private val maleLastNames = listOf(
        "Закроев", "Петров", "Иванов", "Сидоров", "Кузнецов", "Смирнов", "Попов"
    )
    private val femaleLastNames = listOf(
        "Лазорева", "Петрова", "Иванова", "Сидорова", "Кузнецова", "Смирнова", "Попова"
    )
    private val maleFirstNames = listOf(
        "Василий", "Иван", "Пётр", "Алексей", "Дмитрий", "Сергей", "Николай"
    )
    private val femaleFirstNames = listOf(
        "Мария", "Анна", "Елена", "Ольга", "Наталья", "Татьяна", "Ирина"
    )
    private val malePatronymics = listOf(
        "Геннадьевич", "Иванович", "Петрович", "Сергеевич", "Николаевич", "Алексеевич"
    )
    private val femalePatronymics = listOf(
        "Геннадьевна", "Ивановна", "Петровна", "Сергеевна", "Николаевна", "Алексеевна"
    )

    fun generate(): String {
        val isMale = Random.nextBoolean()
        return if (isMale) {
            "${maleLastNames.random()} ${maleFirstNames.random()} ${malePatronymics.random()}"
        } else {
            "${femaleLastNames.random()} ${femaleFirstNames.random()} ${femalePatronymics.random()}"
        }
    }
}
