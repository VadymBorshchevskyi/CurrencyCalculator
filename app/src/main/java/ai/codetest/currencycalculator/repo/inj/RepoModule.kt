package ai.codetest.currencycalculator.repo.inj

import ai.codetest.currencycalculator.biz.Repo
import ai.codetest.currencycalculator.repo.MainRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {
    @Binds
    fun bindRepo(mainRepo: MainRepo): Repo
}