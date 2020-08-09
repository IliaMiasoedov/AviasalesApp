package ilya.myasoedov.aviasales.di.module

import dagger.Module
import dagger.Provides
import ilya.myasoedov.aviasales.providers.ConverterFactoryProvider
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideConverterFactoryProvider(): ConverterFactoryProvider = ConverterFactoryProvider()
}
