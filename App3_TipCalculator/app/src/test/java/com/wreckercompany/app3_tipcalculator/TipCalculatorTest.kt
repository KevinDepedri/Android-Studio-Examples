package com.wreckercompany.app3_tipcalculator

import org.junit.Test
import java.text.NumberFormat
import org.junit.Assert.assertEquals

class TipCalculatorTest {

    @Test
    fun calculate_20_percent_tip_no_roundup(){
        val amount = 100f
        val tipPercent = 20f
        val roundUp = false
        val actualTip = calculateTip(amount, tipPercent, roundUp)
        val expectedTip = NumberFormat.getCurrencyInstance().format(20)
        assertEquals(expectedTip, actualTip)
    }

}