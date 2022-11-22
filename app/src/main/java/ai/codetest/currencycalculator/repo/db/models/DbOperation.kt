package ai.codetest.currencycalculator.repo.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "operation"
)
data class DbOperation(
    @PrimaryKey val id: String,
    val originCurrencyCode: String,
    val toCurrencyCode: String,
    val originAmount: Float,
    val toAmount: Float,
    val timestamp: String
)
