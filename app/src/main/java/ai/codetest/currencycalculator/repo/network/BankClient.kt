package ai.codetest.currencycalculator.repo.network

import ai.codetest.currencycalculator.repo.network.modules.ApiRates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BankClient {
    @GET("statdirectory/exchange?json")
    suspend fun getRates(@Query("date") date: String): Response<List<ApiRates>>
}