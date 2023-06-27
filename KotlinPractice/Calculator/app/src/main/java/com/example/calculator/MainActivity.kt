package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.MediumGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           Scaffold (topBar = { TopBar() }) {
               val viewModel = viewModel<CalculatorViewModel>()
               val state = viewModel.state
               val buttonSpacing = 8.dp
               val modifier = Modifier
                   .fillMaxSize()
                   .background(MediumGray)
                   .padding(16.dp)
               Calculator(
                   state = state,
                   modifier = modifier,
                   onAction = viewModel::onAction,
                   buttonSpacing = buttonSpacing
               )
           }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTheme {
        TopBar()
    }
}