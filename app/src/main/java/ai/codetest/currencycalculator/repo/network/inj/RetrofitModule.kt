package ai.codetest.currencycalculator.repo.network.inj

import ai.codetest.currencycalculator.repo.network.BankClient
import ai.codetest.currencycalculator.repo.network.MainBankClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideBankClient(): BankClient =
       MainBankClient()
}