package tech.hackcity.educarts.uitls

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tech.hackcity.educarts.domain.model.location.State

/**
 *Created by Victor Loveday on 9/21/23
 */
class DatabaseConverters {

    @TypeConverter
    fun fromJson(json: String): List<State> {
        val type = object : TypeToken<List<State>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(states: List<State>): String {
        return Gson().toJson(states)
    }
}