package ilya.myasoedov.aviasales.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ilya.myasoedov.aviasales.app.AppActivity
import ilya.myasoedov.aviasales.app.AppActivityModule

@Module
abstract class ActivityInjectionsModule {

    @ContributesAndroidInjector(modules = [AppActivityModule::class])
    abstract fun appActivityInjector(): AppActivity
}
