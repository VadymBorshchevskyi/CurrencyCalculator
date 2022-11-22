package ai.codetest.currencycalculator.repo.db.inj

import ai.codetest.currencycalculator.repo.db.Db
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext appContext: Context): Db = Db(appContext)
}