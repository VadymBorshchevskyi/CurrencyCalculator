package ai.codetest.currencycalculator.ui.viewmodels

import ai.codetest.currencycalculator.biz.models.Currency
import ai.codetest.currencycalculator.biz.InternetException
import ai.codetest.currencycalculator.biz.models.Rates
import ai.codetest.currencycalculator.biz.Repo
import ai.codetest.currencycalculator.biz.models.Operation
import ai.codetest.currencycalculator.ui.screens.ConvertUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {

    private val _selectedDay = MutableStateFlow(getCurrentDateTime())
    val selectedDay = _selectedDay.asStateFlow()
    private val _uiState = MutableStateFlow(ConvertUiState())
    val uiState = _uiState.asStateFlow()
    private val _amount = MutableStateFlow("")
    val amount = _amount.asStateFlow()
    var rates: List<Rates> = emptyList()

    init {
        getRates()
    }

    private fun getCurrentDateTime(): Instant {
        val currentMoment = Instant.now()
        return currentMoment.atZone(ZoneId.systemDefault()).toInstant()
    }

    fun selectDate(date: Long) {
        viewModelScope.launch {
            val userSelectedDate = Instant.ofEpochMilli(date)
            val localDateTime = userSelectedDate.atZone(ZoneId.systemDefault()).toInstant()
            _selectedDay.emit(localDateTime)
            getRates()
        }
    }

    fun getRates() {
        viewModelScope.launch {
            val formatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd")
            val dateFormatter = selectedDay.first().atZone(ZoneId.systemDefault()).format(formatter)
            try {
                _uiState.emit(ConvertUiState(isLoading = true))
                rates = repo.getRates(dateFormatter)
                _uiState.emit(ConvertUiState(
                    rates.map { rate -> Currency(
                        code = rate.rateCode,
                        name = rate.rateName) }
                ))
            } catch (e: InternetException) {
                _uiState.emit(ConvertUiState(hasError = true))
            }
        }
    }

    fun convert(originRateCode: String, toRateCode: String, amountString: String)  {
        viewModelScope.launch {
            val originRate = rates.find { it.rateCode == originRateCode } ?: return@launch
            val toRate = rates.find { it.rateCode == toRateCode } ?: return@launch
            val amount = amountString.toFloatOrNull() ?: return@launch
            val baseAmount = originRate.rate * amount
            val result = baseAmount/toRate.rate
            val formatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val dateFormatter = getCurrentDateTime().atZone(ZoneId.systemDefault()).format(formatter)
            _amount.emit(result.toString())
            repo.saveOperation(Operation(
                originCurrencyCode = originRate.rateCode,
                originAmount = amount,
                toCurrencyCode = toRate.rateCode,
                toAmount = result,
                timestamp = dateFormatter
            ))
        }

    }
}