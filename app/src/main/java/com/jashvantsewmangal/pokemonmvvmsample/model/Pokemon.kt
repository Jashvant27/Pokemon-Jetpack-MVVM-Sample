package com.jashvantsewmangal.pokemonmvvmsample.model

data class Pokemon(
    val name: String,
    val type: Set<String>,
    val dexNumber: Int,
    val imageResource: Int,
    val shortDescription: String
)