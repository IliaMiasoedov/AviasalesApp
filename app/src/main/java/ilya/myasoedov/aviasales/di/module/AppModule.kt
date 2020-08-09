package ilya.myasoedov.aviasales.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ilya.myasoedov.aviasales.app.App
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app.applicationContext
}
