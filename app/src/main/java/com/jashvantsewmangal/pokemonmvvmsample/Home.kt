package com.jashvantsewmangal.pokemonmvvmsample

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jashvantsewmangal.pokemonmvvmsample.model.Pokemon
import com.jashvantsewmangal.pokemonmvvmsample.model.PokemonUiState.*
import com.jashvantsewmangal.pokemonmvvmsample.ui.theme.PokemonMVVMSampleTheme
import com.jashvantsewmangal.pokemonmvvmsample.viewmodel.HomeViewModel
import java.util.*


@Composable
fun Home() {
    val viewModel: HomeViewModel = viewModel()

    PokemonMVVMSampleTheme {
        Scaffold(topBar = { AppBar() }) { innerPadding ->
            when (val state = viewModel.uiState.collectAsState().value) {
                is Empty -> EmptyScreen(contentpadding = innerPadding)
                is Loading -> LoadingScreen(contentpadding = innerPadding)
                is Loaded -> PokemonScreen(contentpadding = innerPadding, pokemons = state.pokemons, viewModel.featuredPokemon(state.pokemons))
                is Error -> ErrorScreen(contentpadding = innerPadding, errorMessage = state.message)
            }
        }
    }
}

//region Event-screens
@Composable
private fun EmptyScreen(contentpadding: PaddingValues) {
    LazyColumn(contentPadding = contentpadding) {
        item {
            Header(stringResource(R.string.featured))
        }
        item {
            Text(
                text = "No Pokémon, you've gotta catch 'em all",
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Header(stringResource(R.string.myPokemon))
        }
        item {
            Text(
                text = "No Pokémon, you've gotta catch 'em all",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun ErrorScreen(contentpadding: PaddingValues, errorMessage: String) {
    LazyColumn(contentPadding = contentpadding) {
        item {
            Header("ERROR")
        }
        item {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Header("ERROR")
        }
        item {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun PokemonScreen(contentpadding: PaddingValues, pokemons: List<Pokemon>, featuedPokemon: Pokemon) {
    LazyColumn(contentPadding = contentpadding) {
        item {
            Header(stringResource(R.string.featured))
        }
        item {
            FeaturedPokemon(
                pokemon = featuedPokemon,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Header(stringResource(R.string.myPokemon))
        }
        items(pokemons) { pokemon ->
            PostPokemon(pokemon = pokemon)
            Divider(startIndent = 72.dp)
        }
    }

}

@Composable
private fun LoadingScreen(contentpadding: PaddingValues) {
    LazyColumn(
        contentPadding = contentpadding,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Header(stringResource(R.string.featured))
        }
        item {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 30.dp))
        }
        item {
            Header(stringResource(R.string.myPokemon))
        }
        item {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 50.dp))
        }
    }

}
//endregion

//region Default UI-elements
@Composable
private fun AppBar() {
    TopAppBar(navigationIcon = {
        Icon(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }, title = {
        Text(text = stringResource(R.string.app_name))
    }, backgroundColor = MaterialTheme.colors.primarySurface
    )
}

@Composable
fun Header(
    text: String, modifier: Modifier = Modifier
) {
    Surface(color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier.semantics { heading() }) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}
//endregion

@Composable
fun FeaturedPokemon(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* onClick */ }
        ) {
            Image(
                painter = painterResource(pokemon.imageResource),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            val padding = Modifier.padding(horizontal = 16.dp)
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.h6,
                modifier = padding
            )
            Text(
                text = pokemon.shortDescription,
                style = MaterialTheme.typography.body2,
                modifier = padding
            )
            PostMetadata(pokemon, padding)
            Spacer(Modifier.height(16.dp))
        }
    }
}


@Composable
private fun PostMetadata(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    val divider = "  •  "
    val tagDivider = " "
    val text = buildAnnotatedString {
        append(pokemon.dexNumber.toString())
        append(divider)
        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
        )
        pokemon.type.forEachIndexed { index, tag ->
            if (index != 0) {
                append(tagDivider)
            }
            withStyle(tagStyle) {
                append(" ${tag.uppercase(Locale.getDefault())} ")
            }
        }
    }
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostPokemon(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .clickable { /* todo */ }
            .padding(vertical = 0.dp),
        icon = {
            Image(
                painter = painterResource(pokemon.imageResource),
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .height(75.dp)
                    .width(75.dp)
            )
        },
        text = {
            Text(text = pokemon.name)
        },
        overlineText = {
            PostMetadata(pokemon)
        },
        secondaryText = {
            Text(text = pokemon.shortDescription)
        }
    )
}

//region Previews
@Preview("Post Pokémon")
@Composable
private fun PostItemPreview() {
    val hTyphlosion = Pokemon(
        name = "Typhlosion (Hisuian Form)",
        type = setOf("Fire", "Ghost"),
        dexNumber = 168,
        imageResource = R.drawable.shiny_hisuian_typhlosion,
        shortDescription = "Ghost Flame Pokémon"
    )
    PokemonMVVMSampleTheme {
        Surface {
            PostPokemon(pokemon = hTyphlosion)
        }
    }
}

@Preview("Featured Post")
@Composable
private fun FeaturedPostPreview() {
    val dubwool = Pokemon(
        name = "Dubwool",
        type = setOf("Normal"),
        dexNumber = 832,
        imageResource = R.drawable.shiny_dubwool,
        shortDescription = "Sheep Pokémon"
    )
    PokemonMVVMSampleTheme {
        FeaturedPokemon(pokemon = dubwool)
    }
}

@Preview("Featured Post • Dark")
@Composable
private fun FeaturedPostDarkPreview() {
    val dubwool = Pokemon(
        name = "Dubwool",
        type = setOf("Normal"),
        dexNumber = 832,
        imageResource = R.drawable.shiny_dubwool,
        shortDescription = "Sheep Pokémon"
    )
    PokemonMVVMSampleTheme(darkTheme = true) {
        FeaturedPokemon(pokemon = dubwool)
    }
}

@Preview("Home")
@Composable
private fun HomePreview() {
    Home()
}
//endregion