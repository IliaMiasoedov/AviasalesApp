package ilya.myasoedov.aviasales.features.presentation.map

import dagger.Module
import dagger.Provides
import ilya.myasoedov.aviasales.di.Provider
import ilya.myasoedov.aviasales.di.qualifier.ScreenScope
import ilya.myasoedov.aviasales.di.qualifier.ViewModelInjection
import ilya.myasoedov.aviasales.features.domain.entity.City

@Module
class MapFragmentModule {

    @Suppress("UNCHECKED_CAST")
    @Provides
    @ScreenScope
    fun provideMapFragmentArgs(fragment: MapFragment): Pair<City, City> {
        return MapFragmentArgs.fromBundle(fragment.requireArguments()).arg as Pair<City, City>
    }

    @Provides
    @ScreenScope
    @ViewModelInjection
    fun provideMapFragmentViewModel(
        fragment: MapFragment,
        viewModelProvider: Provider<MapFragmentViewModel>
    ): MapFragmentViewModel = viewModelProvider.get(fragment, MapFragmentViewModel::class)
}
