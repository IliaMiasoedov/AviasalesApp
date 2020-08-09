package ilya.myasoedov.aviasales.features.presentation.input

import dagger.Module
import dagger.Provides
import ilya.myasoedov.aviasales.di.Provider
import ilya.myasoedov.aviasales.di.qualifier.ScreenScope
import ilya.myasoedov.aviasales.di.qualifier.ViewModelInjection

@Module
class InputFragmentModule {

    @Provides
    @ScreenScope
    @ViewModelInjection
    fun provideInputFragmentViewModel(
        fragment: InputFragment,
        viewModelProvider: Provider<InputFragmentViewModel>
    ): InputFragmentViewModel = viewModelProvider.get(fragment, InputFragmentViewModel::class)
}
