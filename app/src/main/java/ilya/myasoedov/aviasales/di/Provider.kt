package ilya.myasoedov.aviasales.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import kotlin.reflect.KClass

class Provider<VM : ViewModel> @Inject constructor(
    private val lazyViewModel: dagger.Lazy<VM>
) {
    @Suppress("unchecked_cast")
    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = lazyViewModel.get() as T
    }

    fun <A : FragmentActivity> get(activity: A, viewModelClass: KClass<VM>) =
        ViewModelProvider(activity.viewModelStore, viewModelFactory).get(viewModelClass.java)

    fun <F : Fragment> get(fragment: F, viewModelClass: KClass<VM>) =
        ViewModelProvider(fragment.viewModelStore, viewModelFactory).get(viewModelClass.java)
}
