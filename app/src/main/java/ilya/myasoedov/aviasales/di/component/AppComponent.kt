package ilya.myasoedov.aviasales.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ilya.myasoedov.aviasales.app.App
import ilya.myasoedov.aviasales.di.module.ActivityInjectionsModule
import ilya.myasoedov.aviasales.di.module.AppModule
import ilya.myasoedov.aviasales.di.module.FragmentInjectionsModule
import ilya.myasoedov.aviasales.di.module.NetworkModule
import ilya.myasoedov.aviasales.di.module.RepositoriesModule
import ilya.myasoedov.aviasales.di.module.UseCasesModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityInjectionsModule::class,
        FragmentInjectionsModule::class,
        AppModule::class,
        NetworkModule::class,
        RepositoriesModule::class,
        UseCasesModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
