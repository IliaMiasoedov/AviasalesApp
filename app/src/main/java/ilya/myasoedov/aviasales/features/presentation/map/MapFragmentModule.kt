package ilya.myasoedov.aviasales.features.presentation.map

import dagger.Module
import dagger.Provides
import ilya.myasoedov.aviasales.di.Provider
import ilya.myasoedov.aviasales.di.qualifier.ScreenScope
import ilya.myasoedov.aviasales.di.qualifier.ViewModelInjection

@Module
class MapFragmentModule {

    @Provides
    @ScreenScope
    fun provideMapFragmentArgs(fragment: MapFragment): MapFragmentParam {
        return MapFragmentArgs.fromBundle(fragment.requireArguments()).arg
    }

    @Provides
    @ScreenScope
    @ViewModelInjection
    fun provideMapFragmentViewModel(
        fragment: MapFragment,
        viewModelProvider: Provider<MapFragmentViewModel>
    ): MapFragmentViewModel = viewModelProvider.get(fragment, MapFragmentViewModel::class)
}
