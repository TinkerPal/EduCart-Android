package tech.hackcity.educarts.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tech.hackcity.educarts.domain.model.location.Country
import tech.hackcity.educarts.domain.model.location.State

/**
 *Created by Victor Loveday on 9/21/23
 */

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCountry(country: List<Country>)

    @Query("SELECT * FROM country_table ORDER BY name DESC")
    fun fetchAllCountries(): LiveData<List<Country>>

//    @Query("SELECT states FROM country_table WHERE name = :country")
//    fun fetchAllStatesByCountry(country: String): LiveData<List<State>>

}