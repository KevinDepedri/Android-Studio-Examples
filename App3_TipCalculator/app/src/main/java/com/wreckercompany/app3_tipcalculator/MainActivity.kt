package com.wreckercompany.app3_tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
                Surface(modifier = Modifier.fillMaxHeight()) {
                    TipCalculator()
                }
            }
        }
    }
}


@Composable
fun TipCalculator(modifier: Modifier = Modifier){
    var inputAmount by remember { mutableStateOf("") }
    val amount = inputAmount.toFloatOrNull() ?: 0f

    var inputTipRate by remember { mutableStateOf("") }
    val tipRate = inputTipRate.toFloatOrNull() ?: 0f

    var inputRoundUp by remember { mutableStateOf(false) }

    val tip = calculateTip(amount, tipRate, inputRoundUp)

    val focusManager = LocalFocusManager.current

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
        ) {
        Text(
            text = stringResource(id = R.string.calculate_tip),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        EditNumberField(
            label = R.string.bill_amount,
            KeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            KeyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down)}
            ),
            value = inputAmount,
            onValueChange = { inputAmount = it}
        )

        Spacer(Modifier.height(16.dp))

        EditNumberField(
            label = R.string.how_was_the_service,
            KeyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            KeyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            value = inputTipRate,
            onValueChange = { inputTipRate = it}
        )

        Spacer(Modifier.height(16.dp))

        RoundTheTipRow(RoundUp = inputRoundUp, onRoundUpChanged = { inputRoundUp = it })

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.tip_amount, tip),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    KeyboardOptions: KeyboardOptions,
    KeyboardActions: KeyboardActions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = value,
        label = { Text(stringResource(label)) },
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions,
        keyboardActions = KeyboardActions
    )
}

@Composable
fun RoundTheTipRow(
    RoundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.round_up_tip))
        Switch(
            checked = RoundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            colors = SwitchDefaults.colors(uncheckedThumbColor = Color.Gray)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    App3_TipCalculatorTheme {
        TipCalculator()
    }
}

private fun calculateTip(amount: Float = 0f, tipPercentage: Float = 15f, roundTip: Boolean = false): String {
    var tip: Float = (amount * tipPercentage) / 100
    if (roundTip){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}