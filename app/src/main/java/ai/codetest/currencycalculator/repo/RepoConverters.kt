package ai.codetest.currencycalculator.repo

import ai.codetest.currencycalculator.biz.models.Operation
import ai.codetest.currencycalculator.biz.models.Rates
import ai.codetest.currencycalculator.repo.db.models.DbOperation
import ai.codetest.currencycalculator.repo.network.modules.ApiRates

object RepoConverters {
    fun apiRatesToRates(apiRates: ApiRates): Rates {
        return Rates(
            rate = apiRates.rate,
            rateCode = apiRates.cc,
            rateName = apiRates.txt,
            timestamp = apiRates.exchangedate
        )
    }

    fun dbOperationToOperations(dbOperations: List<DbOperation>): List<Operation> =
        dbOperations.map { dbOperation ->
            Operation(
                id = dbOperation.id,
                originAmount = dbOperation.originAmount,
                originCurrencyCode = dbOperation.originCurrencyCode,
                toCurrencyCode = dbOperation.toCurrencyCode,
                toAmount = dbOperation.toAmount,
                timestamp = dbOperation.timestamp
            )
        }

    fun operationToDbOperation(operation: Operation): DbOperation =
        DbOperation(
            id = operation.id,
            originAmount = operation.originAmount,
            originCurrencyCode = operation.originCurrencyCode,
            toCurrencyCode = operation.toCurrencyCode,
            toAmount = operation.toAmount,
            timestamp = operation.timestamp
        )
}