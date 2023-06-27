package com.example.calculator

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TopBar() {
    TopAppBar(title = {
        Text(
            text = "Calculator",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
    })
}