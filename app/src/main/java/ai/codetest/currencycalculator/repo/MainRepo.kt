package ai.codetest.currencycalculator.repo

import ai.codetest.currencycalculator.biz.InternetException
import ai.codetest.currencycalculator.biz.models.Rates
import ai.codetest.currencycalculator.biz.Repo
import ai.codetest.currencycalculator.biz.models.Operation
import ai.codetest.currencycalculator.repo.db.Db
import ai.codetest.currencycalculator.repo.network.BankClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepo @Inject constructor(
    private val bankClient: BankClient,
    private val db: Db
) : Repo {

    override suspend fun getRates(date: String): List<Rates> {
        val apiRates = try {
            bankClient.getRates(date)
        } catch (e: UnknownHostException) {
            throw InternetException()
        }
        return apiRates.body()?.map { RepoConverters.apiRatesToRates(it) } ?: emptyList()
    }

    override suspend fun saveOperation(operation: Operation) {
        db.operationDao.insertOperation(RepoConverters.operationToDbOperation(operation))
    }

    override fun getOperations(): Flow<List<Operation>> {
        return db.operationDao.getOperations().map { dbOperations -> RepoConverters.dbOperationToOperations(dbOperations) }
    }
}