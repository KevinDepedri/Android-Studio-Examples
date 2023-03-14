package com.wreckercompany.app3_tipcalculator

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.wreckercompany.app3_tipcalculator.ui.theme.App3_TipCalculatorTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip_no_roundup(){
        composeTestRule.setContent {
            App3_TipCalculatorTheme{
                TipCalculator()
            }
        }

        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("100")
        composeTestRule.onNodeWithText("Tip (%)")
            .performTextInput("20")

        val expectedTip = NumberFormat.getCurrencyInstance().format(20)
        composeTestRule.onNodeWithText("Tip Amount: $expectedTip").assertExists(
            "Assertion failed: no node with this text was found")
    }
}