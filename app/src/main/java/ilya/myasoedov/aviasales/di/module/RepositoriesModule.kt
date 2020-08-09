package ilya.myasoedov.aviasales.di.module

import dagger.Module
import dagger.Provides
import ilya.myasoedov.aviasales.features.data.datasources.Client
import ilya.myasoedov.aviasales.features.data.repository.CityRepository
import ilya.myasoedov.aviasales.features.data.repository.ICityRepository
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideCityRepository(client: Client): ICityRepository = CityRepository(client)
}
