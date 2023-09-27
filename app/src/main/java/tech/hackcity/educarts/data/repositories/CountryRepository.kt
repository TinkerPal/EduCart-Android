package tech.hackcity.educarts.data.repositories

import androidx.lifecycle.LiveData
import tech.hackcity.educarts.domain.dao.CountryDao
import tech.hackcity.educarts.domain.model.location.Country

/**
 *Created by Victor Loveday on 9/25/23
 */
class CountryRepository(private val countryDao: CountryDao) {

    val readAllCountries: LiveData<List<Country>> = countryDao.fetchAllCountries()

    suspend fun saveCountry(country: List<Country>) {
        countryDao.saveCountry(country)
    }
}