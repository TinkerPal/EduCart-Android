package tech.hackcity.educarts.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by Victor Loveday on 2/23/23
 */
@Entity(tableName = "address_book_table")
data class AddressBook(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val homeAddress: String,
    val apartmentNumber: String?,
    val city: String,
    val country: String,
    val activeAddress: Boolean
)
