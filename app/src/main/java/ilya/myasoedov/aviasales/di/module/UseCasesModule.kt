package ilya.myasoedov.aviasales.di.module

import dagger.Module
import dagger.Provides
import ilya.myasoedov.aviasales.features.data.repository.ICityRepository
import ilya.myasoedov.aviasales.features.domain.usecase.GetCitySuggestionsUseCase
import ilya.myasoedov.aviasales.features.domain.usecase.GetCityUseCase
import ilya.myasoedov.aviasales.features.domain.usecase.IGetCitySuggestionsUseCase
import ilya.myasoedov.aviasales.features.domain.usecase.IGetCityUseCase
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Provides
    @Singleton
    fun provideGetCityUseCase(cityRepository: ICityRepository): IGetCityUseCase =
        GetCityUseCase(cityRepository)

    @Provides
    @Singleton
    fun provideGetCitySuggestionsUseCase(cityRepository: ICityRepository): IGetCitySuggestionsUseCase =
        GetCitySuggestionsUseCase(cityRepository)
}
