package com.jashvantsewmangal.pokemonmvvmsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jashvantsewmangal.pokemonmvvmsample.ui.theme.PokemonMVVMSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                Home()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonMVVMSampleTheme(darkTheme = false) {
        Home()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultDarkPreview() {
    PokemonMVVMSampleTheme(darkTheme = true) {
        Home()
    }
}