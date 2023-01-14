package com.abhishek.androidlearn

import kotlin.random.Random

data class Info(
    var id: Int,
    var progress: Int
) {
    companion object {
        fun randomList() = ArrayList<Info>().apply {
            repeat(100) {
                val rand = Random.nextInt(0, 100)
                add(Info(it, rand))
            }
        }
    }
}