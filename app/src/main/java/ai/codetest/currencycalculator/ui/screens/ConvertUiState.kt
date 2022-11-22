package ai.codetest.currencycalculator.ui.screens

import ai.codetest.currencycalculator.biz.models.Currency

data class ConvertUiState(
    val rates: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
    val hasError: Boolean = false
)