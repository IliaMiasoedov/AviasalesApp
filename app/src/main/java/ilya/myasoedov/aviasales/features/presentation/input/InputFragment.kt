package ilya.myasoedov.aviasales.features.presentation.input

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import ilya.myasoedov.aviasales.R
import ilya.myasoedov.aviasales.app.BaseFragment
import ilya.myasoedov.aviasales.features.data.repository.IncorrectArrivalCityException
import ilya.myasoedov.aviasales.features.data.repository.IncorrectDepartureCityException
import ilya.myasoedov.aviasales.features.data.repository.TheSameCitiesException
import ilya.myasoedov.aviasales.util.extensions.safeNavigate

class InputFragment : BaseFragment<InputFragmentViewModel>() {

    private lateinit var inputButton: Button
    private lateinit var departureEditTextLayout: TextInputLayout
    private lateinit var arrivalEditTextLayout: TextInputLayout
    private lateinit var departureEditText: EditText
    private lateinit var arrivalEditText: EditText
    private lateinit var progressBar: ProgressBar

    override fun layoutRes(): Int = R.layout.fragment_input

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inputButton = view.findViewById(R.id.fragment_input_button)
        departureEditTextLayout = view.findViewById(R.id.fragment_input_from_city_layout)
        arrivalEditTextLayout = view.findViewById(R.id.fragment_input_to_city_layout)
        departureEditText = view.findViewById(R.id.fragment_input_from_city_edit_text)
        arrivalEditText = view.findViewById(R.id.fragment_input_to_city_edit_text)
        progressBar = view.findViewById(R.id.fragment_input_progress_bar)

        setListeners()
        observe()
    }

    private fun setListeners() {
        departureEditText.doAfterTextChanged {
            processOnTextChangedAction()
        }
        arrivalEditText.doAfterTextChanged {
            processOnTextChangedAction()
        }
        inputButton.setOnClickListener {
            viewModel.getCity(
                from = departureEditText.text.toString(),
                to = arrivalEditText.text.toString()
            )
        }
    }

    private fun observe() {
        viewModel.cityResultLiveData.observe(viewLifecycleOwner, Observer { result ->
            findNavController().safeNavigate(InputFragmentDirections.openMap(result))
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { error ->
            processError(error)
        })
        viewModel.progressLiveData.observe(viewLifecycleOwner, Observer { progress ->
            progressBar.visibility = if (progress) View.VISIBLE else View.GONE
            inputButton.visibility = if (progress) View.INVISIBLE else View.VISIBLE
        })
    }

    private fun processOnTextChangedAction() {
        departureEditTextLayout.isErrorEnabled = false
        arrivalEditTextLayout.isErrorEnabled = false
        inputButton.isEnabled =
            departureEditText.text.isNotEmpty() && arrivalEditText.text.isNotEmpty()
    }

    private fun processError(error: Throwable) {
        when (error) {
            is IncorrectDepartureCityException -> {
                departureEditTextLayout.error =
                    getString(R.string.input_page_wrong_departure_city)
            }
            is IncorrectArrivalCityException -> {
                arrivalEditTextLayout.error =
                    getString(R.string.input_page_wrong_arrival_city)
            }
            is TheSameCitiesException -> {
                arrivalEditTextLayout.error =
                    getString(R.string.input_page_arrival_city_should_distinct)
            }
            else -> {
                view?.let {
                    Snackbar.make(it, error.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}
