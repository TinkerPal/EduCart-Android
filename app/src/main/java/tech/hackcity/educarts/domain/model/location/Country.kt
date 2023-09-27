package tech.hackcity.educarts.domain.model.location

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "country_table")
data class Country(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val id: Int,
    val currency: String,
    val phone_code: String,
    val states: List<State>
)