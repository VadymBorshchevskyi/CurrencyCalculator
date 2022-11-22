package ai.codetest.currencycalculator.biz.models

import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

data class Operation(
    val id: String = UUID.randomUUID().toString(),
    val originCurrencyCode: String,
    val toCurrencyCode: String,
    val originAmount: Float,
    val toAmount: Float,
    val timestamp: String
) {
    val result: String
    get() {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        val originAmountFormatter = df.format(originAmount)
        val toAmountFormatter = df.format(toAmount)
        return "$originAmountFormatter $originCurrencyCode => $toAmountFormatter $toCurrencyCode"
    }
}
