package tech.hackcity.educarts.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by Victor Loveday on 2/23/23
 */
@Entity(tableName = "address_book_table")
data class AddressBook(
    @PrimaryKey(autoGenerate = true)
    val id: Int
)
