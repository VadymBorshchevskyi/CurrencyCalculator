package ai.codetest.currencycalculator.biz

import ai.codetest.currencycalculator.biz.models.Operation
import ai.codetest.currencycalculator.biz.models.Rates
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun getRates(date: String): List<Rates>
    suspend fun saveOperation(operation: Operation)
    fun getOperations(): Flow<List<Operation>>
}