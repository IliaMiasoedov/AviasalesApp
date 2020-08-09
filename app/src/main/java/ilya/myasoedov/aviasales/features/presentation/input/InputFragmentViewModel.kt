package ilya.myasoedov.aviasales.features.presentation.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ilya.myasoedov.aviasales.features.domain.entity.City
import ilya.myasoedov.aviasales.features.domain.usecase.IGetCityUseCase
import ilya.myasoedov.aviasales.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

class InputFragmentViewModel @Inject constructor(
    private val getCityUseCase: IGetCityUseCase
) : ViewModel() {

    private val progress = MutableLiveData<Boolean>()
    private val error = SingleLiveEvent<Throwable>()
    private val cityResult = SingleLiveEvent<Pair<City, City>>()

    val progressLiveData: LiveData<Boolean>
        get() = progress
    val errorLiveData: LiveData<Throwable>
        get() = error
    val cityResultLiveData: LiveData<Pair<City, City>>
        get() = cityResult

    fun getCity(from: String, to: String) {
        viewModelScope.launch {
            progress.value = true
            getCityUseCase.getCity(from = from, to = to).fold(
                onSuccess = { cityResult.value = it },
                onFailure = { error.value = it })
            progress.value = false
        }
    }
}
