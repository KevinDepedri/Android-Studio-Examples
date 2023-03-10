package com.wreckercompany.app3_tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wreckercompany.app3_tipcalculator.ui.theme.App3_TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App3_TipCalculatorTheme {
                Surface() {
                    TipCalculator()
                }
            }
        }
    }
}

@Composable
fun TipCalculator(){
    var inputAmount by remember { mutableStateOf("") }
    val amount = inputAmount.toFloatOrNull() ?: 0f
    val tip = calculateTip(amount)

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
        ) {
        Text(
            text = stringResource(id = R.string.calculate_tip),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)) // Check if redundant

        Spacer(Modifier.height(16.dp))

        EditNumberField(value = inputAmount, onValueChange = { inputAmount = it})

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.tip_amount, tip),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)) // Check if redundant
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit
){
    TextField(
        value = value,
        label = { Text(stringResource(id = R.string.cost_of_service)) },
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    App3_TipCalculatorTheme {
        TipCalculator()
    }
}

private fun calculateTip(amount: Float = 0f, tipPercentage: Float = 15f): String {
    val tip: Float = (amount * tipPercentage) / 100
    return NumberFormat.getCurrencyInstance().format(tip)
}