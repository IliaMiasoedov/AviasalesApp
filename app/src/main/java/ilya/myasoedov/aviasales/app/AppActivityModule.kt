package ilya.myasoedov.aviasales.app

import dagger.Module
import dagger.Provides
import ilya.myasoedov.aviasales.di.Provider
import ilya.myasoedov.aviasales.di.qualifier.ViewModelInjection

@Module
class AppActivityModule {

    @Provides
    @ViewModelInjection
    fun provideAppActivityViewModel(
        activity: AppActivity,
        viewModelProvider: Provider<AppActivityViewModel>
    ): AppActivityViewModel = viewModelProvider.get(activity, AppActivityViewModel::class)
}
