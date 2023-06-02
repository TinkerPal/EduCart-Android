package tech.hackcity.educarts.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import tech.hackcity.educarts.domain.model.AddressBook

/**
 *Created by Victor Loveday on 2/23/23
 */
@Dao
interface AddressBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAddress(address: AddressBook)

    @Delete
    suspend fun deleteAddress(address: AddressBook)

    @Query("SELECT * FROM address_book_table")
    fun readAllAddresses(): LiveData<List<AddressBook>>
}