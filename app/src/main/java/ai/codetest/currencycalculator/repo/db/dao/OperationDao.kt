package ai.codetest.currencycalculator.repo.db.dao

import ai.codetest.currencycalculator.repo.db.models.DbOperation
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class OperationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOperation(operation: DbOperation)

    @Transaction
    @Query(value = "SELECT * FROM operation LIMIT 10")
    abstract fun getOperations(): Flow<List<DbOperation>>
}