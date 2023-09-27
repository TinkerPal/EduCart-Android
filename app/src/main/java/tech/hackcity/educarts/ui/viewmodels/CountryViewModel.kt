package tech.hackcity.educarts.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.hackcity.educarts.data.repositories.CountryRepository
import tech.hackcity.educarts.data.storage.AppDatabase
import tech.hackcity.educarts.domain.model.location.Country

/**
 *Created by Victor Loveday on 9/25/23
 */
class CountryViewModel(application: Application): AndroidViewModel(application) {

    val readAllCountries: LiveData<List<Country>>
    private val repository: CountryRepository

    init {
        val countryDao = AppDatabase.getDatabase(application).countryDao()
        repository = CountryRepository(countryDao)
        readAllCountries = repository.readAllCountries
    }

    suspend fun saveCountry(country: List<Country>) {
        viewModelScope.launch {
            repository.saveCountry(country)
        }
    }
}