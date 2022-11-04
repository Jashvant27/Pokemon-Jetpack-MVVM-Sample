package com.jashvantsewmangal.pokemonmvvmsample.model

sealed class PokemonUiState {
    object Empty: PokemonUiState()
    object Loading: PokemonUiState()
    class Loaded(val pokemons: List<Pokemon>) : PokemonUiState()
    class Error(val message: String) : PokemonUiState()
}

