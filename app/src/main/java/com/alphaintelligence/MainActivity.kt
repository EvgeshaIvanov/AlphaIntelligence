package com.alphaintelligence

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import com.alphaintelligence.ui.theme.AlphaIntelligenceTheme
import com.presentation.CoinsScreen

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlphaIntelligenceTheme {
                Navigator(
                    screen = CoinsScreen()
                )
            }
        }
    }
}