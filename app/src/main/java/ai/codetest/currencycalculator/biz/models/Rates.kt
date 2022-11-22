package ai.codetest.currencycalculator.biz.models

data class Rates(
    val rateName: String,
    val rate: Float,
    val rateCode: String,
    val timestamp: String
)
