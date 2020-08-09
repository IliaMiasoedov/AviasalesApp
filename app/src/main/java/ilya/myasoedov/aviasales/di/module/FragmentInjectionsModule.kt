package ilya.myasoedov.aviasales.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ilya.myasoedov.aviasales.di.qualifier.ScreenScope
import ilya.myasoedov.aviasales.features.presentation.input.InputFragment
import ilya.myasoedov.aviasales.features.presentation.input.InputFragmentModule
import ilya.myasoedov.aviasales.features.presentation.map.MapFragment
import ilya.myasoedov.aviasales.features.presentation.map.MapFragmentModule

@Module
abstract class FragmentInjectionsModule {

    @ScreenScope
    @ContributesAndroidInjector(modules = [InputFragmentModule::class])
    abstract fun inputFragmentInjector(): InputFragment

    @ScreenScope
    @ContributesAndroidInjector(modules = [MapFragmentModule::class])
    abstract fun mapFragmentInjector(): MapFragment
}
