package ilya.myasoedov.aviasales.app

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ilya.myasoedov.aviasales.R
import ilya.myasoedov.aviasales.di.qualifier.ViewModelInjection
import javax.inject.Inject

class AppActivity : DaggerAppCompatActivity() {

    @Inject
    @ViewModelInjection
    lateinit var viewModel: AppActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }
}
