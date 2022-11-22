package ai.codetest.currencycalculator.repo.db

import ai.codetest.currencycalculator.repo.db.dao.OperationDao
import ai.codetest.currencycalculator.repo.db.models.DbOperation
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val FILE_NAME: String = "main.db"

@Database(
    entities = [DbOperation::class],
    version = 1
)
abstract class Db : RoomDatabase() {
    abstract val operationDao: OperationDao
}

fun Db(appContext: Context): Db =
    Room
        .databaseBuilder(appContext, Db::class.java, FILE_NAME)
        .fallbackToDestructiveMigration()
        .build()