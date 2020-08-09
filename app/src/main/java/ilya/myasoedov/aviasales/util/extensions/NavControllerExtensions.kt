package ilya.myasoedov.aviasales.util.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigate(directions: NavDirections) {
    runCatching { navigate(directions) }
}

fun NavController.safePopBackStack() {
    runCatching { popBackStack() }
}
