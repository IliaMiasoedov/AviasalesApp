package ilya.myasoedov.aviasales.features.presentation.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ilya.myasoedov.aviasales.features.domain.entity.City
import ilya.myasoedov.aviasales.features.domain.usecase.IGetCitySuggestionsUseCase
import ilya.myasoedov.aviasales.features.domain.usecase.IGetCityUseCase
import ilya.myasoedov.aviasales.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

class InputFragmentViewModel @Inject constructor(
    private val getCityUseCase: IGetCityUseCase,
    private val getCitySuggestionsUseCase: IGetCitySuggestionsUseCase
) : ViewModel() {

    private val progress = MutableLiveData<Boolean>()
    private val error = SingleLiveEvent<Throwable>()
    private val cityResult = SingleLiveEvent<Pair<City, City>>()
    private val arrivalCitySuggestions = MutableLiveData<List<String>>()
    private val departureCitySuggestions = MutableLiveData<List<String>>()

    val progressLiveData: LiveData<Boolean>
        get() = progress
    val errorLiveData: LiveData<Throwable>
        get() = error
    val cityResultLiveData: LiveData<Pair<City, City>>
        get() = cityResult
    val arrivalCitySuggestionsLiveData: LiveData<List<String>>
        get() = arrivalCitySuggestions
    val departureCitySuggestionsLiveData: LiveData<List<String>>
        get() = departureCitySuggestions

    fun getCity(from: String, to: String) {
        viewModelScope.launch {
            progress.value = true
            getCityUseCase.getCity(from = from, to = to).fold(
                onSuccess = { cityResult.value = it },
                onFailure = { error.value = it })
            progress.value = false
        }
    }

    fun getArrivalCitySuggestions(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                getCitySuggestionsUseCase.getCitySuggestions(query).fold(
                    onSuccess = { arrivalCitySuggestions.value = it },
                    onFailure = { arrivalCitySuggestions.value = emptyList() }
                )
            }
        } else {
            arrivalCitySuggestions.value = emptyList()
        }
    }

    fun getDepartureCitySuggestions(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                getCitySuggestionsUseCase.getCitySuggestions(query).fold(
                    onSuccess = { departureCitySuggestions.value = it },
                    onFailure = { departureCitySuggestions.value = emptyList() }
                )
            }
        } else {
            departureCitySuggestions.value = emptyList()
        }
    }
}
