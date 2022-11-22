package ai.codetest.currencycalculator.repo.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun MainBankClient(): BankClient {

    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://bank.gov.ua/NBUStatService/v1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(BankClient::class.java)
}