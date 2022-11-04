package com.jashvantsewmangal.pokemonmvvmsample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jashvantsewmangal.pokemonmvvmsample.R
import com.jashvantsewmangal.pokemonmvvmsample.model.Pokemon
import com.jashvantsewmangal.pokemonmvvmsample.model.PokemonUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<PokemonUiState>(PokemonUiState.Empty)
    val uiState: StateFlow<PokemonUiState> = _uiState

    init {
        fetchPokemon()
    }

    private fun fetchPokemon() {
        _uiState.value = PokemonUiState.Loading

        viewModelScope.launch {
            try {
                //Ignore the delay, need to use it to show that loading event actually triggers
                delay(3000)
                //region Pokémon variables
                val ariados = Pokemon(
                    name = "Ariados",
                    type = setOf("Bug", "Poison"),
                    dexNumber = 168,
                    imageResource = R.drawable.shiny_ariados,
                    shortDescription = "Long Leg Pokémon"
                )
                val cursola = Pokemon(
                    name = "Cursola",
                    type = setOf("Ghost"),
                    dexNumber = 864,
                    imageResource = R.drawable.shiny_cursola,
                    shortDescription = "Coral Pokémon"
                )
                val donphan = Pokemon(
                    name = "Donphan",
                    type = setOf("Ground"),
                    dexNumber = 232,
                    imageResource = R.drawable.shiny_donphan,
                    shortDescription = "Armor Pokémon"
                )
                val dubwool = Pokemon(
                    name = "Dubwool",
                    type = setOf("Normal"),
                    dexNumber = 832,
                    imageResource = R.drawable.shiny_dubwool,
                    shortDescription = "Sheep Pokémon"
                )
                val entei = Pokemon(
                    name = "Entei",
                    type = setOf("Fire"),
                    dexNumber = 244,
                    imageResource = R.drawable.shiny_entei,
                    shortDescription = "Volcano Pokémon"
                )
                val leafeon = Pokemon(
                    name = "Leafeon",
                    type = setOf("Grass"),
                    dexNumber = 470,
                    imageResource = R.drawable.shiny_flower_crown_leafeon,
                    shortDescription = "Verdant Pokémon"
                )
                val hBraviary = Pokemon(
                    name = "Braviary (Hisuian Form)",
                    type = setOf("Psychic", "Flying"),
                    dexNumber = 628,
                    imageResource = R.drawable.shiny_h_braviary,
                    shortDescription = "Battle Cry Pokémon"
                )
                val hTyphlosion = Pokemon(
                    name = "Typhlosion (Hisuian Form)",
                    type = setOf("Fire", "Ghost"),
                    dexNumber = 168,
                    imageResource = R.drawable.shiny_hisuian_typhlosion,
                    shortDescription = "Ghost Flame Pokémon"
                )
                val kingdra = Pokemon(
                    name = "Kingdra",
                    type = setOf("Water", "Dragon"),
                    dexNumber = 230,
                    imageResource = R.drawable.shiny_kingdra,
                    shortDescription = "Dragon Pokémon"
                )
                val lucario = Pokemon(
                    name = "Lucario",
                    type = setOf("Steel", "Fighting"),
                    dexNumber = 448,
                    imageResource = R.drawable.shiny_lucario,
                    shortDescription = "Aura Pokémon"
                )
                val milotic = Pokemon(
                    name = "Milotic",
                    type = setOf("Water"),
                    dexNumber = 350,
                    imageResource = R.drawable.shiny_milotic,
                    shortDescription = "Tender Pokémon"
                )
                val salamence = Pokemon(
                    name = "Salamence",
                    type = setOf("Dragon", "Flying"),
                    dexNumber = 373,
                    imageResource = R.drawable.shiny_salamence,
                    shortDescription = "Dragon Pokémon"
                )
                val steelix = Pokemon(
                    name = "Steelix",
                    type = setOf("Steel", "Ground"),
                    dexNumber = 208,
                    imageResource = R.drawable.shiny_steelix,
                    shortDescription = "Iron Snake Pokémon"
                )
                //endregion
                _uiState.value = PokemonUiState.Loaded(
                    pokemons = listOf(
                        ariados,
                        cursola,
                        donphan,
                        dubwool,
                        entei,
                        leafeon,
                        hBraviary,
                        hTyphlosion,
                        kingdra,
                        lucario,
                        milotic,
                        salamence,
                        steelix
                    )
                )
            }
            catch (ex: java.lang.Exception) {
                _uiState.value = PokemonUiState.Error(ex.message.toString())
            }
        }
    }

    fun featuredPokemon(pokemons: List<Pokemon>): Pokemon {
        return pokemons.random()
    }
}