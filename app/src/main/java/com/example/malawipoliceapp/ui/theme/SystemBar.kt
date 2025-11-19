package com.example.malawipoliceapp.ui.theme



import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.statusBarsPadding(): Modifier {
    return this.windowInsetsPadding(WindowInsets.systemBars)
}