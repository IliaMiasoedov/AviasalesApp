package ilya.myasoedov.aviasales.util.extensions

fun List<String>.containsIgnoreCase(string: String): Boolean {
    return if (isNotEmpty()) {
        for (s in this) {
            if (s.equals(string.trim(), ignoreCase = true)) {
                return true
            }
        }
        false
    } else {
        false
    }
}
